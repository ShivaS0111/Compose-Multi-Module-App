package com.invia.security.ssl

import javax.security.auth.x500.X500Principal

/**
 * X500PrincipalHelper
 *
 *
 * Helper class to extract pieces (attributes) of an X500Principal object for
 * display in the UI.
 *
 *
 * This helper uses the X500Principal.RFC2253 format of X500Principal.getname()
 * to parse an X500Principal name into it's component parts.
 *
 *
 * In principals which contain multiple occurrences of the same attribute,the
 * default for all the methods is to return the most significant (first)
 * attribute found.
 *
 */
class X500PrincipalHelper(principal: X500Principal) {
    private val rdnNameArray: MutableList<List<String>?> = ArrayList()

    init {
        parseDN(principal.getName(X500Principal.RFC2253))
    }

    val cN: String?
        /**
         * Gets the most significant common name (CN) attribute from the given
         * X500Principal object. For names that contains multiple attributes of this
         * type. The first (most significant) one will be returned
         *
         *
         *
         * @return the Most significant common name attribute.
         */
        get() = findPart(attrCN)

    val oU: String?
        /**
         * Gets the most significant Organizational Unit (OU) attribute from the
         * given X500Principal object. For names that contains multiple attributes
         * of this type. The first (most significant) one will be returned
         *
         *
         *
         * @return the Most significant OU attribute.
         */
        get() = findPart(attrOU)

    val o: String?
        /**
         * Gets the most significant Organization (O) attribute from the given
         * X500Principal object. For names that contains multiple attributes of this
         * type. The first (most significant) one will be returned
         *
         *
         *
         * @return the Most significant O attribute.
         */
        get() = findPart(attrO)

    val c: String?
        /**
         * Gets the Country (C) attribute from the given X500Principal object.
         *
         *
         *
         * @return the C attribute.
         */
        get() = findPart(attrC)

    val l: String?
        /**
         * Gets the Locale (L) attribute from the given X500Principal object.
         *
         *
         *
         * @return the L attribute.
         */
        get() = findPart(attrL)

    val sT: String?
        /**
         * Gets the State (ST) attribute from the given X500Principal object.
         *
         *
         *
         * @return the ST attribute.
         */
        get() = findPart(attrST)

    val sTREET: String?
        /**
         * Gets the Street (STREET) attribute from the given X500Principal object.
         *
         *
         *
         * @return the STREET attribute.
         */
        get() = findPart(attrSTREET)

    val eMAILDDRESS: String?
        /**
         * Gets the Email Address (EMAILADDRESS) attribute from the given
         * X500Principal object.
         *
         *
         *
         * @return the EMAILADDRESS attribute.
         */
        get() = findPart(attrEMAIL)

    val uID: String?
        get() = findPart(attrUID)

    /**
     * Derived From: org.eclipse.osgi.internal.verifier - DNChainMatching.java
     *
     * Takes a distinguished name in canonical form and fills in the rdnArray
     * with the extracted RDNs.
     *
     * @param dn
     * the distinguished name in canonical form.
     * @throws IllegalArgumentException
     * if a formatting error is found.
     */
    @Throws(IllegalArgumentException::class)
    private fun parseDN(dn: String) {
        var startIndex = 0
        var c = '\u0000'
        var nameValues: MutableList<String>? = ArrayList()
        // Clear the existing array, in case this instance is being re-used
        rdnNameArray.clear()
        while (startIndex < dn.length) {
            var endIndex = startIndex
            while (endIndex < dn.length) {
                c = dn[endIndex]
                if (c == ',' || c == '+') break
                if (c == '\\') {
                    endIndex++ // skip the escaped char
                }
                endIndex++
            }
            require(endIndex <= dn.length) { "unterminated escape $dn" } //$NON-NLS-1$

            nameValues!!.add(dn.substring(startIndex, endIndex))
            if (c != '+') {
                rdnNameArray.add(nameValues)
                nameValues = if (endIndex != dn.length) ArrayList()
                else null
            }
            startIndex = endIndex + 1
        }
        require(nameValues == null) {
            "improperly terminated DN $dn" //$NON-NLS-1$
        }
    }

    /**
     * Returns an ArrayList containing all the values for the given attribute
     * identifier.
     *
     *
     *
     * @param attributeID
     * String containing the X500 name attribute whose values are to
     * be returned
     * @return ArrayList containing the string values of the requested
     * attribute. Values are in the order they occur. May be empty.
     */
    fun getAllValues(attributeID: String): List<String> {
        val retList = ArrayList<String>()
        val searchPart = attributeID + attrTerminator
        val iter: Iterator<List<String>?> = rdnNameArray.iterator()
        while (iter.hasNext()) {
            val nameList = iter.next()
            val namePart = nameList!![0]
            if (namePart.startsWith(searchPart)) {
                // Return the string starting after the ID string and the = sign
                // that follows it.
                retList.add(namePart.toString().substring(searchPart.length))
            }
        }
        return retList
    }

    private fun findPart(attributeID: String): String? {
        return findSignificantPart(attributeID, MOSTSIGNIFICANT)
    }

    private fun findSignificantPart(attributeID: String, significance: Int): String? {
        var retNamePart: String? = null
        val searchPart = attributeID + attrTerminator
        val iter: Iterator<List<String>?> = rdnNameArray.iterator()
        while (iter.hasNext()) {
            val nameList = iter.next()
            val namePart = nameList!![0]
            if (namePart.startsWith(searchPart)) {
                // Return the string starting after the ID string and the = sign
                // that follows it.
                retNamePart = namePart.substring(searchPart.length)
                // By definition the first one is most significant
                if (significance == MOSTSIGNIFICANT) break
            }
        }
        return retNamePart
    }

    companion object {
        var LEASTSIGNIFICANT: Int = 0
        var MOSTSIGNIFICANT: Int = 1
        const val attrCN: String = "CN"
        const val attrOU: String = "OU"
        const val attrO: String = "O"
        const val attrC: String = "C"
        const val attrL: String = "L"
        const val attrST: String = "ST"
        const val attrSTREET: String = "STREET"
        const val attrEMAIL: String = "EMAILADDRESS"
        const val attrUID: String = "UID"
        private const val attrTerminator = "="
    }
}
