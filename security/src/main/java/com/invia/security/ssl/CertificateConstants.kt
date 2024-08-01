package com.invia.security.ssl

import java.io.ByteArrayInputStream
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

object CertificateConstants : Certificates() {
    var productionCAPublicCertificate: X509Certificate? = null
        private set
    var newProductionCAPublicCertificate: X509Certificate? = null
        private set
    var devCAPublicCertificate: X509Certificate? = null
        private set
    var newDevCAPublicCertificate: X509Certificate? = null
        private set
    var rootCAPublicCertificate: X509Certificate? = null
        private set

    var premRootCertificate: X509Certificate? = null
        private set
    var premProdCertificate: X509Certificate? = null
        private set
    var premLocalCertificate: X509Certificate? = null
        private set

    var newRootCAPublicCertificate: X509Certificate? = null
        private set
    var newIntermediateCAPublicCertificate: X509Certificate? = null
        private set

    var rmgRsaPartyGamingCommonRootCACertificate: X509Certificate? = null
        private set
    var rmgRsaCommonRootCACertificate: X509Certificate? = null
        private set
    var rmgRsaBwinPartyTestCACertificate: X509Certificate? = null
        private set
    var rmgRsaTestCACertificate: X509Certificate? = null
        private set
    var rmgRsaBwinPartyProdCACertificate: X509Certificate? = null
        private set
    var rmgRsaProdCertificate: X509Certificate? = null
        private set

    lateinit var pgCASubjectName: ByteArray
        private set
    lateinit var pgTestCASubjectName: ByteArray
        private set

    private lateinit var rootCASubjectName: ByteArray

    var pgCACommonName: String? = null
    var new_pgCACommonName: String? = null
    var pgTestCACommonNam: String? = null
    var new_pgTestCACommonNam: String? = null
    var rootCACommonNam: String? = null

    var premRootCommonNam: String? = null
    var premProdCommonNam: String? = null
    var premLocalCommonNam: String? = null

    var newRootCACommonName: String? = null
    var newIntermediateCACommonName: String? = null

    var rmgRsaPartyGamingCommonRootCACertificateName: String? = null
    var rmgRsaCommonRootCACertificateName: String? = null

    var rmgRsaBwinPartyTestCACertificateName: String? = null
    var rmgRsaTestCACertificateName: String? = null

    var rmgRsaBwinPartyProdCACertificateName: String? = null
    var rmgRsaProdCertificateName: String? = null

    init {
        loadCertificates()
    }

    private fun loadCertificates() {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val pgCertificateInputStream = ByteArrayInputStream(getBytes(PG_CERTIFICATE))
            val pgTestCertificateInputStream = ByteArrayInputStream(getBytes(PGTESTCA_CERTIFICATE))
            val New_pgCertificateInputStream =
                ByteArrayInputStream(getBytes(NewCertificateConstants.NEW_PG_CERTIFICATE))
            val New_pgTestCertificateInputStream =
                ByteArrayInputStream(getBytes(NewCertificateConstants.NEW_PGTESTCA_CERTIFICATE))
            val rootCertificateInputStream = ByteArrayInputStream(getBytes(ROOT_CERTIFICATE))

            val premRootCertificateInputStream =
                ByteArrayInputStream(getBytes(PremiumCertificateConstants.PREM_ROOT_CERTIFICATE))
            val premProdCertificateInputStream =
                ByteArrayInputStream(getBytes(PremiumCertificateConstants.PREM_PROD_CERTIFICATE))
            val premLocalCertificateInputStream =
                ByteArrayInputStream(getBytes(PremiumCertificateConstants.PREM_LOCAL_CERTIFICATE))
            val newRootCertificateInputStream =
                ByteArrayInputStream(getBytes(NewCertificateConstants2024.NEW_2024_ROOT_CERTIFICATE))
            val newIntermediateCertificateInputStream =
                ByteArrayInputStream(getBytes(NewCertificateConstants2024.NEW_2024_INTERMEDIATE_CERTIFICATE))

            productionCAPublicCertificate =
                certificateFactory.generateCertificate(pgCertificateInputStream) as X509Certificate
            devCAPublicCertificate =
                certificateFactory.generateCertificate(pgTestCertificateInputStream) as X509Certificate
            newProductionCAPublicCertificate =
                certificateFactory.generateCertificate(New_pgCertificateInputStream) as X509Certificate
            newDevCAPublicCertificate =
                certificateFactory.generateCertificate(New_pgTestCertificateInputStream) as X509Certificate
            rootCAPublicCertificate =
                certificateFactory.generateCertificate(rootCertificateInputStream) as X509Certificate

            premRootCertificate =
                certificateFactory.generateCertificate(premRootCertificateInputStream) as X509Certificate
            premProdCertificate =
                certificateFactory.generateCertificate(premProdCertificateInputStream) as X509Certificate
            premLocalCertificate =
                certificateFactory.generateCertificate(premLocalCertificateInputStream) as X509Certificate

            newRootCAPublicCertificate =
                certificateFactory.generateCertificate(newRootCertificateInputStream) as X509Certificate
            newIntermediateCAPublicCertificate =
                certificateFactory.generateCertificate(newIntermediateCertificateInputStream) as X509Certificate

            rmgRsaPartyGamingCommonRootCACertificate = certificateFactory.generateCertificate(
                ByteArrayInputStream(getBytes(RMGRSACertificates.Common.RMGRSA_PARTYGAMING_COMMON_ROOT_CA_CERTIFICATE))
            ) as X509Certificate
            rmgRsaCommonRootCACertificate = certificateFactory.generateCertificate(
                ByteArrayInputStream(getBytes(RMGRSACertificates.Common.RMGRSA_COMMON_ROOT_CA_CERTIFICATE))
            ) as X509Certificate

            rmgRsaBwinPartyTestCACertificate = certificateFactory.generateCertificate(
                ByteArrayInputStream(getBytes(RMGRSACertificates.Dev.RMGRSA_BWINPARTY_TEST_CA_CERTIFICATE))
            ) as X509Certificate
            rmgRsaTestCACertificate = certificateFactory.generateCertificate(
                ByteArrayInputStream(getBytes(RMGRSACertificates.Dev.RMGRSA_TEST_CA_CERTIFICATE))
            ) as X509Certificate

            rmgRsaBwinPartyProdCACertificate = certificateFactory.generateCertificate(
                ByteArrayInputStream(getBytes(RMGRSACertificates.Prod.RMGRSA_BWINPARTY_PROD_CA_CERTIFICATE))
            ) as X509Certificate

            rmgRsaProdCertificate = certificateFactory.generateCertificate(
                ByteArrayInputStream(getBytes(RMGRSACertificates.Prod.RMGRSA_PROD_CERTIFICATE))
            ) as X509Certificate

            pgCASubjectName = getBytes(PG_SUBJECT_NAME)
            pgTestCASubjectName = getBytes(PGTESTCA_SUBJECT_NAME)
            rootCASubjectName = getBytes(ROOT_SUBJECT_NAME)
            pgCACommonName = getCNFromCertificate(productionCAPublicCertificate)
            pgTestCACommonNam = getCNFromCertificate(devCAPublicCertificate)
            new_pgCACommonName = getCNFromCertificate(newProductionCAPublicCertificate)
            new_pgTestCACommonNam = getCNFromCertificate(newDevCAPublicCertificate)
            rootCACommonNam = getCNFromCertificate(rootCAPublicCertificate)

            premRootCommonNam = getCNFromCertificate(premRootCertificate)
            premProdCommonNam = getCNFromCertificate(premProdCertificate)
            premLocalCommonNam = getCNFromCertificate(premLocalCertificate)

            newRootCACommonName = getCNFromCertificate(newRootCAPublicCertificate)
            newIntermediateCACommonName = getCNFromCertificate(newIntermediateCAPublicCertificate)

            rmgRsaPartyGamingCommonRootCACertificateName = getCNFromCertificate(
                rmgRsaPartyGamingCommonRootCACertificate
            )
            rmgRsaCommonRootCACertificateName = getCNFromCertificate(rmgRsaCommonRootCACertificate)

            rmgRsaBwinPartyTestCACertificateName = getCNFromCertificate(
                rmgRsaBwinPartyTestCACertificate
            )
            rmgRsaTestCACertificateName = getCNFromCertificate(rmgRsaTestCACertificate)

            rmgRsaBwinPartyProdCACertificateName = getCNFromCertificate(
                rmgRsaBwinPartyProdCACertificate
            )
            rmgRsaProdCertificateName = getCNFromCertificate(rmgRsaProdCertificate)

            //PGConnector.appendToDelegateLog("loaded certificates", PGConnector.DEBUG_LOG_LEVEL);
        } catch (e: Exception) {
            Exception("couldnt load certificates", e).printStackTrace()
            //PGConnector.appendToDelegateLog("couldnt load certificates", e);
        }
    }

    fun getCNFromCertificate(cert: X509Certificate?): String? {
        val serverX500Principal = cert!!.subjectX500Principal
        val serverPrincipalHelper = X500PrincipalHelper(serverX500Principal)
        return serverPrincipalHelper.cN
    }

    fun getBytes(inputChars: CharArray): ByteArray {
        val bytes = ByteArray(inputChars.size)
        var i = 0
        for (inpChar in inputChars) {
            bytes[i++] = (inpChar.code and 0xFF).toByte()
        }
        return bytes
    }

    /**
     * This method that will verify whether the CA Common Name
     * matches one of the certificates CNs in CertificateConstants.java
     */
    fun verifyCACommonName(caCommonName: String): Boolean {
        if (caCommonName == pgTestCACommonNam || caCommonName == rootCACommonNam || caCommonName == pgCACommonName || caCommonName == new_pgTestCACommonNam || caCommonName == new_pgCACommonName || caCommonName == premRootCommonNam || caCommonName == premProdCommonNam || caCommonName == premLocalCommonNam || caCommonName == newRootCACommonName || caCommonName == newIntermediateCACommonName || caCommonName == rmgRsaPartyGamingCommonRootCACertificateName || caCommonName == rmgRsaCommonRootCACertificateName || caCommonName == rmgRsaBwinPartyTestCACertificateName || caCommonName == rmgRsaTestCACertificateName || caCommonName == rmgRsaBwinPartyProdCACertificateName || caCommonName == rmgRsaProdCertificateName
        ) {
            return true
        }
        return false
    }
}
