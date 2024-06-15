package com.example.test_movie_app

import io.mockk.InternalPlatformDsl.toArray
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMParser

import org.bouncycastle.jce.provider.PEMUtil
import org.bouncycastle.util.encoders.Base64
import org.junit.Test
import java.io.StringReader
import java.security.Security
import java.security.cert.X509Certificate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //initFn(11)
        //val height = listOf(1,8,6,2,5,4,8,3,7)
        //maxArea(height)
        //val list = arrayOf<Int>(-1, 0, 1, 2, -1, -4)
        //threeSum(list)
        certToHeaArray()
        val a: Int = 0xff


    }

    fun pemToHexArray(pemContent: String): List<String> {
        // Remove PEM header and footer
        val pemHeader = "-----BEGIN CERTIFICATE-----"
        val pemFooter = "-----END CERTIFICATE-----"
        val base64Content = pemContent.replace(pemHeader, "").replace(pemFooter, "").trim()

        // Decode Base64 content to binary
        val binaryContent = Base64.decode(base64Content)

        // Convert binary to hex
        val hexArray = binaryContent.map { byte -> String.format("0x%02x", byte) }

        return hexArray
    }



    fun certToHeaArray(){
        for( data in RMGRSACertificates().certificates) {
            println()
            println()
            println("============start==================")
            println("version:"+data.version)
            println("issuerDN:"+data.issuerDN)
            println("issuerUniqueID:"+data.issuerUniqueID)
            println("issuerX500Principal.name:"+data.issuerX500Principal.name)
            println("issuerX500Principal.encoded:"+data.issuerX500Principal.encoded)
            data.issuerAlternativeNames?.let {
                for (name in data.issuerAlternativeNames) {
                    println("issuer name: $name")
                }
            }
            println("notBefore:"+data.notBefore)
            println("notAfter:"+data.notAfter)
            println("============end==================")
            println()
        }
    }

    private fun initFn(  sum:Int) {
        val list = listOf(1,2,4, 6,8, 10, 12)

        var front = 0
        var back = list.size-1
        var isLoop = true;
        var iteration = 1;
        while( isLoop){
            if( list[front] < list[back] ){
                println("==>${iteration++}, pointer: $front(${list[front]}) ==>$back(${list[back]}), = ${list[front]+list[back]}, ")
                var tempSum = list[front] + list[back]
                if(  tempSum== sum ){
                    println("==>${iteration++}, final pointer: $front==>$back, ((${list[front]}) ==> ${list[back]}), = ${list[front]+list[back]}, ")

                    isLoop = false
                }else if( tempSum < sum ){
                    front++
                }else{
                    back--
                }
            }else{
                println("==>All iterations completed($iteration), desired sum is not generated ")
                isLoop = false
            }
        }
    }

    fun maxArea(height: List<Int>): Int {
        var left = 0
        var right = height.size - 1
        var maxArea = 0

        while (left < right) {
            val width = right - left
            val minHeight = minOf(height[left], height[right])
            val area = width * minHeight
            maxArea = maxOf(maxArea, area)

            println("==> ($left, $right)=> (${height[left]},${height[right]},  minHeight:$minHeight * width:$width , area:$area, maxArea:$maxArea")

            // Move the pointer pointing to the shorter line inward
            if (height[left] < height[right]) {
                left++
            } else {
                right--
            }
        }

        return maxArea
    }

    fun threeSum(nums: Array<Int>): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        nums.sort() // Sort the array to use two-pointer technique

        for (i in 0 until nums.size - 2) {
            if (i > 0 && nums[i] == nums[i - 1]) continue // Skip duplicate elements


            var left = i + 1
            var right = nums.size - 1

            while (left < right) {
                val sum = nums[i] + nums[left] + nums[right]
                println("==> ($i, $left, $right) --> (${nums[i]}, ${nums[left]}, ${nums[right]}) = ${nums[i] + nums[left]+ nums[right]}")

                when {
                    sum == 0 -> {
                        result.add(listOf(nums[i], nums[left], nums[right]))
                        // Move the left pointer to the right to the next different number
                        while (left < right && nums[left] == nums[left + 1]) left++
                        // Move the right pointer to the left to the next different number
                        while (left < right && nums[right] == nums[right - 1]) right--
                        left++
                        right--
                    }
                    sum < 0 -> left++
                    else -> right--
                }
            }
        }

        return result
    }

    @Test
    fun smallestSubarrayWithGivenSum() {

        val S= 7
        val  arr = intArrayOf(2, 1, 5, 2, 3, 2)// Output: 2

        var windowSum = 0
        var minLength = Int.MAX_VALUE
        var windowStart = 0
        println("==> windowStart:$windowStart,  windowEnd:0, minLength:$minLength, windowSum:$windowSum, arr:(2, 1, 5, 2, 3, 2)")

        for (windowEnd in arr.indices) {
            windowSum += arr[windowEnd]
            if(windowStart==1 || windowStart ==2){
                println("")
            }

            while (windowSum >= S) {
                minLength = minOf(minLength, windowEnd - windowStart + 1)
                windowSum -= arr[windowStart]
                windowStart++
                println("========> windowStart:$windowStart,  windowEnd:$windowEnd, minLength:$minLength, windowSum:$windowSum")

            }
            println("==> windowStart:$windowStart,  windowEnd:$windowEnd, minLength:$minLength, windowSum:$windowSum")
        }
        println("==> ${ if (minLength == Int.MAX_VALUE) 0 else minLength }")
    }

}