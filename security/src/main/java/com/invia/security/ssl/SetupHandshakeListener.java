package com.invia.security.ssl;


import java.io.IOException;
import java.net.InetAddress;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;

/**
 * This is the setup sepecific trust manager. Here we have our custom rules written.
 * 
 * @author ivy3890
 * 
 */
public class SetupHandshakeListener {
    private boolean connectedOverIp = false;

    public SetupHandshakeListener(boolean connectedOverIp) {
        this.connectedOverIp=connectedOverIp;
    }
    
    /**
     * Called after the certificates are validated by the trust manager and SSL
     * handshake is complete. This is to handle the setup specific validations.
     * The extracted attributes from the server certificate are exposed as
     * protected variables in this class which may be used for the validation.
     * 
     * @param ipAddress
     * @param hostName
     *            : may not be available always
     * @throws CertificateException
     *             - if the attributes of the server certificate are not valid.
     *             The exception is caught to break the SSLConnection to server
     * @throws SSLPeerUnverifiedException 
     */
    public void validateCertificateAttributes(SSLSocket socket) throws CertificateException, SSLPeerUnverifiedException{
        InetAddress inetAddress = socket.getInetAddress();
        String ipAddress = inetAddress.getHostAddress();
        String hostName = inetAddress.getHostName();
        X509Certificate serverCertificate = SSLUtil.INSTANCE.convert(socket.getSession().getPeerCertificateChain()[0]);
        CertificateAttributes serverCertificateAttributes = SetupTrustManager.Companion.getCertificateattributes().get(SSLUtil.INSTANCE.getString(serverCertificate));
        if(!serverCertificateAttributes.isValid()){
            throw new CertificateException("crt; ssl failed");
        }
        //CertificateAttributes serverCertificateAttributes = extractCertificateAttributes(serverCertificate);
        /**
         * Currently, in order to establish any of the secure connection kinds,
         * the client needs to receive a valid certificate, signed by one of the
         * trusted CAs (whose public certificates are hard-coded in its binary)
         */
        if(!CertificateConstants.INSTANCE.verifyCACommonName(Objects.requireNonNull(serverCertificateAttributes.getCaCommonName()))){
            throw new CertificateException("cn; ssl failed");
        }
        if(!connectedOverIp) {
            /**
             * Client connected with Host name.
             * 3.1. The FQDN, the client connects to, must match one of the following parts of the SSL certificate
             * represented by the server: -Subject CN; or - Subject Alternatine Name with a class "DNS".
             */
            boolean subjectCommonNameMatched = verifySubjectCommonName(hostName,serverCertificateAttributes.getServerCommonName());
            if(subjectCommonNameMatched){
                return;
            }else{
                // Host name is not matched, check if SAN is matched against the host name
                boolean subjectAlternativeDNSMatched = false;
                for (String dns : serverCertificateAttributes.getServerAlternateIA5DNSName()) {
                    subjectAlternativeDNSMatched = verifySubjectCommonName(hostName,dns);
                    if (subjectAlternativeDNSMatched) {
                        break;
                    }
                }
                if (subjectAlternativeDNSMatched) {
                    return;
                }else{
                    throw new CertificateException("adns; ssl failed");
                }
            }
        }
        else {
            /**
             * 4. In case the client connects to a Lobby server by IP, the Lobby server's identity should be checked in the
             * following way:<br/>
             * 4.1. The SSL certificate, presented by the Lobby server, must contain at least one Subject Alternative Name
             * (SAN) with a class "IP Address".<br/>
             * 4.2. The client does "bit-wise-and" between any the "IP Address" value taken from the SAN and the IP it has
             * established connection with.<br/>
             * 4.3. In case the "bit-wise-and" operation produces the same value as some of these taken from the SAN - the
             * identity of the Lobby server is accepted by the client - otherwise - not.<br/>
             */
            boolean subjectAlternativeNameMatched = false;
            for (String certificateIpAddress : serverCertificateAttributes.getServerAlternateIA5IPAddress()) {
                int certificateIntIp = ipToInt(certificateIpAddress);
                int intIpAddress = ipToInt(ipAddress);
                if ((certificateIntIp & intIpAddress) == certificateIntIp) {
                    subjectAlternativeNameMatched = true;
                    break;
                }
            }
            if (subjectAlternativeNameMatched) {
                return;
            }
        }
        throw new CertificateException("ahs; ssl failed");
    }
    
    /**
     * If the CN from the certificate's Subject is a wild-card one and then use the "matcher" 
     * method to match it against the hostName.
     * The CN from the certificate's Subject is NOT a wild-card one and then use the "equals" 
     * method to compare it to the hostName. 
     */
    private boolean verifySubjectCommonName(String hostName,String serverCommonName) {
        if(serverCommonName.contains("*")){
            serverCommonName = serverCommonName.replaceAll("\\*", "[-0-9a-zA-Z]+");
            Pattern commonNamePattern = Pattern.compile(serverCommonName);
            Matcher commonNameMatcher = commonNamePattern.matcher(hostName);
            return commonNameMatcher.matches();
        }else {
            return serverCommonName.equals(hostName);
        }
     }

    private int ipToInt(String inputIp) {
        String[] ips = inputIp.split("\\.");
        int returnInt = 0;

        for (int i = 0; i < ips.length; i++) {
            returnInt <<= 8;
            returnInt |= Integer.parseInt(ips[i]);
        }
        return returnInt;
    }


    public final void handshakeCompleted(SSLSocket socket) throws Exception {

        System.out.println("Conn=>C: SetuphandshakeListener handshakeCompleted, socket"+socket);
        InetAddress inetAddress = socket.getInetAddress();
        String ipAddress = inetAddress.getHostAddress();
        String hostName = inetAddress.getHostName();
        try {
            validateCertificateAttributes(socket);
        } catch (Exception ce) {
//            ConnectionManager.isSSLValid = false;
//			PGConnector.appendToDelegateLog(
//					"the ssl connection is not valid; ip:" + ipAddress
//							+ " host:" + hostName, PGConnector.ERROR_LOG_LEVEL);
//			PGConnector.appendToDelegateLog(
//					"the ssl connection is not valid; ip:" + ipAddress
//							+ " host:" + hostName,ce);
//            ce.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
            	//PGConnector.appendToDelegateLog("Exception while closing the socket in SetupHandShakeListener",e);
//                se.printStackTrace();
            }
            throw  new Exception("SSL Validation Exception");
        }
    }
}
