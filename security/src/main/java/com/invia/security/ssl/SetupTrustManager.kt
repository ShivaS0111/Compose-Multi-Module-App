package com.invia.security.ssl

import java.security.PublicKey
import java.security.cert.CertificateException
import java.security.cert.CertificateParsingException
import java.security.cert.X509Certificate
import java.util.Arrays
import java.util.Hashtable
import javax.net.ssl.X509TrustManager
import javax.security.auth.x500.X500Principal


/**
 * The class does the basic ssl-validation over the servers public certificate
 * and exposes its attributes out. The class exposes abstract methods which may
 * be used to validate the
 *
 * @author ivy3890
 */
class SetupTrustManager() : X509TrustManager {
    /**
     * The method is not expected to be called as this is used for server
     * authentication only.
     */
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        throw CertificateException("authentication a client is not supported.")
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        val serverCertificate: X509Certificate = chain.get(0)
        var attributes: CertificateAttributes? = null
        try {
            attributes = extractCertificateAttributes(serverCertificate)
            val valid: Boolean = validateChain(chain)
            //attributes.setValid(valid)
            //PGConnector.appendToDelegateLog(attributes.toString(), PGConnector.DEBUG_LOG_LEVEL)
            certificateAttributes[SSLUtil.getString(serverCertificate)] = attributes
        } catch (ce: CertificateException) {

//            ConnectionManager.isSSLValid = false
//            attributes.setValid(false)
//            PGConnector.appendToDelegateLog(
//                "Exception in checkServerTrusted ", ce
//            )

            throw ce
        }
    }

    /**
     * Checks if the certificate is with in the valid dates, signed by the [.getAcceptedIssuers].
     *
     * @param serverCertificate
     * @throws CertificateException
     */
    private fun validateChain(chain: Array<X509Certificate>): Boolean {
        var serverCertIsFromValidCA: Boolean = false
        for (cert: X509Certificate in chain) {
            try {
                /**
                 * date validation
                 */
                cert.checkValidity()
            } catch (e: Exception) {
//                PGConnector.appendToDelegateLog(
//                    "Exception in SetUpTrustManager ", e
//                )
                //                e.printStackTrace();
                return false
            }
            for (setupCertificate: X509Certificate in acceptedIssuers) {
                val setupCAPublicCertificate: PublicKey = setupCertificate.publicKey
                try {
                    cert.verify(setupCAPublicCertificate)
                    serverCertIsFromValidCA = true
                    break
                } catch (e: Exception) {/*
                     * ignore exception as the server certificate may be valid by
                     * any one in the array
                     */
                }
            }
        }
        return serverCertIsFromValidCA
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return setupCAs
    }

    private val setupCAs: Array<X509Certificate>
        /**
         * The setup specific valid issuers public certificate should be returned.
         *
         * @return
         */
        get() {
            val list: ArrayList<X509Certificate?> = ArrayList(
                commonSetupCAs
            ) //common certificates
            if (isTesting()) { //add all dev certificates
                list.addAll(devSetupCAs)
            }
            list.addAll(prodSetupCAs) // prod certificate should be available for prod and dev environments (to support live environment selection in dev builds)
            return list.filterNotNull().toTypedArray()
        }

    val devSetupCAs: List<X509Certificate?>
        /**
         * The setup specific valid issuers dev environment
         * public certificate should be returned.
         *
         * @return
         */
        get() = listOf( //old CA certificate
            CertificateConstants.devCAPublicCertificate,  //new CA certificate
            CertificateConstants.newDevCAPublicCertificate,  //Premium brand CA certificate
            CertificateConstants.premLocalCertificate,  //New RmgRsa Certificates
            //bwinparty test ca certificate

            CertificateConstants.rmgRsaBwinPartyTestCACertificate,  //rmgrsa test ca certificate
            CertificateConstants.rmgRsaTestCACertificate
        )

    val prodSetupCAs: MutableList<X509Certificate?>
        /**
         * The setup specific valid issuers prod environment
         * public certificate should be returned.
         *
         * @return
         */
        get() {
            return Arrays.asList( //old CA certificate
                CertificateConstants.productionCAPublicCertificate,  //new CA certificate
                CertificateConstants.newProductionCAPublicCertificate,  //Premium brand CA certificate
                CertificateConstants.premProdCertificate,  //commmon rmgrsa prod certificates
                //bwinparty ca certificate

                CertificateConstants.rmgRsaBwinPartyProdCACertificate,  //rmgrsa prod certificate
                CertificateConstants.rmgRsaProdCertificate
            )
        }


    val commonSetupCAs: MutableList<X509Certificate?>
        /**
         * The setup specific valid issuers common for prod and dev environment
         * public certificate should be returned.
         *
         * @return
         */
        get() {
            return Arrays.asList( //old root certificate
                CertificateConstants.rootCAPublicCertificate,  //new root certificates
                CertificateConstants.newRootCAPublicCertificate,  //new intermediate certificates
                CertificateConstants.newIntermediateCAPublicCertificate,  //premium brand root certificate
                CertificateConstants.premRootCertificate,  //commmon rmgrsa certificates
                //bwinparty common ca certificate

                CertificateConstants.rmgRsaPartyGamingCommonRootCACertificate,  //common ca certificate
                CertificateConstants.rmgRsaCommonRootCACertificate
            )
        }

    /**
     * extracts server public certificate parameters like
     *
     *  *
     * certification authority:
     *  *
     * server common name
     *  *
     * alternate server alternate dns name
     *  *
     * server alternate ip address
     *
     * @param serverCertificate
     * @return
     * @throws CertificateParsingException
     */
    @Throws(CertificateParsingException::class)
    protected fun extractCertificateAttributes(serverCertificate: X509Certificate): CertificateAttributes {
        /**
         * 2. In order to establish a secure connection with a game server, the
         * client must:<br></br>
         * - check the certificate, represented by the game server, is valid
         * (e.g. it's not expired);<br></br>
         * - check the certificate, represented by the game server, is issued by
         * some of the trusted CAs (e.g. hard-coded in the client);<br></br>
         */
        val attributes: CertificateAttributes = CertificateAttributes()
        val serverX500Principal: X500Principal = serverCertificate.subjectX500Principal
        val serverPrincipalHelper: X500PrincipalHelper = X500PrincipalHelper(serverX500Principal)
        val serverCommonName: String = serverPrincipalHelper.cN.toString()

        val caPrincipal: X500Principal = serverCertificate.issuerX500Principal
        val caPrincipalHelper: X500PrincipalHelper = X500PrincipalHelper(caPrincipal)
        val certificationAuthorityCommonName: String = caPrincipalHelper.cN.toString()

        val serverAlternativeNames: Collection<List<*>>? = serverCertificate.subjectAlternativeNames
        val serverAlternateIA5DNSName: MutableSet<String> = HashSet()
        val serverAlternateIA5IPAddress: MutableSet<String> = HashSet()
        if (serverAlternativeNames != null) {
            for (alternativeName: List<*> in serverAlternativeNames) {
                val generalName: Int = alternativeName.get(0) as Int
                if (generalName == DNS_NAME) {
                    serverAlternateIA5DNSName.add(alternativeName.get(1) as String)
                } else if (generalName == IP_ADDRESS) {
                    serverAlternateIA5IPAddress.add(alternativeName.get(1) as String)
                }
            }
        }
        attributes.caCommonName = certificationAuthorityCommonName
        attributes.serverAlternateIA5DNSName = serverAlternateIA5DNSName
        attributes.serverAlternateIA5IPAddress = serverAlternateIA5IPAddress
        attributes.serverCertificate = serverCertificate
        attributes.serverCommonName = serverCommonName
        return attributes
    }

    companion object {
        /**
         * @see {@link X509Certificate.getSubjectAlternativeNames
         */
        private val DNS_NAME: Int = 2
        private val IP_ADDRESS: Int = 7

        /**
         * String -> X509Certificate in String form. The Class exists in both javax and java so for inter-operability. we
         * are not sure if equals method was implemented on this object X509Certificate impl;
         */
        private val certificateAttributes: MutableMap<String, CertificateAttributes?> = Hashtable()

        val certificateattributes: Map<String, CertificateAttributes?>
            get() {
                return certificateAttributes
            }
    }

    private fun isTesting() = true
}
