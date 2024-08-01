package com.invia.security.ssl

import java.security.cert.X509Certificate

class CertificateAttributes {
    var serverCommonName: String? = null

    /**certificate authority common name */
    var caCommonName: String? = null
    var serverCertificate: X509Certificate? = null
    var isValid: Boolean = false

    /**
     * IA5 is 7bit representation for characters. The ASCII and IA5 chars match
     * for A-Z, a-z, 0-9 <br></br>
     * IA5:: http://publib.boulder.ibm.com/cgi-bin/bookmgr/BOOKS/qb3aq501/F.58;
     * ASCII:: http://publib.boulder.ibm.com/cgi-bin/bookmgr/BOOKS/qb3aq501/F.14
     */
    var serverAlternateIA5DNSName: Set<String> = HashSet()
    var serverAlternateIA5IPAddress: Set<String> = HashSet()

    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("SCN- $serverCommonName")
        sb.append(" | CCN- $caCommonName")
        sb.append(" | isValid- $isValid")
        sb.append(" | SADN- ")
        val it = serverAlternateIA5DNSName.iterator()
        while (it.hasNext()) {
            sb.append(it.next() + ",")
        }
        sb.append(" | SADA- ")
        val it1 = serverAlternateIA5IPAddress.iterator()
        while (it1.hasNext()) {
            sb.append(it1.next() + ",")
        }

        return sb.toString()
    }
}
