package com.invia.security.ssl

import java.io.ByteArrayInputStream
import java.security.cert.CertificateEncodingException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import javax.security.cert.X509Certificate

object SSLUtil {
    fun intIp(ip: String): Int {
        var intIp = 0
        val ips = ip.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        intIp = toInt(ips[3]) shl 24 and (toInt(ips[2]) shl 16) and (toInt(
            ips[1]
        ) shl 8) and toInt(ips[0])
        return intIp
    }

    private fun toInt(s: String): Int {
        return s.toInt()
    }

    fun convert(cert: X509Certificate): java.security.cert.X509Certificate? {
        try {
            val encoded = cert.encoded
            val bis = ByteArrayInputStream(encoded)
            val cf = CertificateFactory.getInstance("X.509")
            return cf.generateCertificate(bis) as java.security.cert.X509Certificate
        } catch (e: CertificateEncodingException) {
        } catch (e: javax.security.cert.CertificateEncodingException) {
        } catch (e: CertificateException) {
        }
        return null
    }

    @Throws(CertificateEncodingException::class)
    fun getString(cert: java.security.cert.X509Certificate): String {
        val encoded = cert.encoded
        return String(encoded)
    }

    @Throws(javax.security.cert.CertificateEncodingException::class)
    fun getString(cert: X509Certificate): String {
        val encoded = cert.encoded
        return String(encoded)
    }
}
