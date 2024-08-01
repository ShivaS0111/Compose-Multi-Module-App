package com.example.test_movie_app

import android.os.Build.VERSION_CODES
import org.bouncycastle.util.Integers
import java.util.Date


object Codility {

    fun findMaxBinaryGap(N:Int):Int{
        val binaryString = Integer.toBinaryString(N)
        val zeroSequences = binaryString.split("1")
        println("===>$N binary: $binaryString,  ==>"+zeroSequences.size);
        var maxGap = 0
        for ((i, gap) in zeroSequences.withIndex()) {
            println("===>gap string: $gap");
            if (!(i == 0 || i == zeroSequences.size - 1)) {
                val len = gap.length
                println("===>max gap: $len> $maxGap");
                if (len > maxGap) {
                    maxGap = len
                }
            }
        }
        // Since binary gaps must be surrounded by '1's, ignore the first and last element if they are not valid gaps
        val validGaps = if (binaryString.startsWith("0"))
            zeroSequences.drop(1)
        else zeroSequences

        val finalGaps = if (binaryString.endsWith("0")) validGaps.dropLast(1) else validGaps

        // Find the longest valid sequence of zeros
        val longestGap = finalGaps.maxByOrNull { it.length }

        // Return the length of the longest sequence or 0 if there are no valid gaps
        val gap = longestGap?.length ?: 0

        println("===>max gap: $maxGap : $gap");
        return maxGap
    }

    private fun numberToBinaryArray(number: Int): IntArray {
        // Convert number to binary string
        val binaryString = number.toString(2)

        // Convert binary string to array of integers
        val binaryArray = binaryString.map { it.toString().toInt() }.toIntArray()

        return binaryArray
    }

    fun findMaxGap(N: Int): Int {
        // Implement your solution here

        val a = numberToBinaryArray(N)

        for( aa in a) print(aa)
        println()

        var maxGap = 0
        var startPos =0
        var endPos =0

        a.forEachIndexed { i, element ->
            if( element == 1 ) {
                if( endPos>startPos ){
                    if( maxGap < endPos-startPos){
                        maxGap = endPos- startPos
                    }
                }
                startPos =i
                endPos=i
            }else{
                endPos=i
            }
        }
        return maxGap
    }

    fun findMaxGap1(N: Int): Int {
        var number = N
        var maxGap = 0
        var currentGap = -1 // Start with -1 to ignore leading zeros

        while (number > 0) {
            println("==> $number and 1 = ${number and 1}, binary:${ Integer.toBinaryString(number) }")
            if (number and 1 == 1) { // Check if the last bit is 1
                if (currentGap > maxGap) {
                    maxGap = currentGap
                }
                currentGap = 0 // Reset the current gap
            } else if (currentGap != -1) { // Only count zeros after the first 1
                currentGap++
            }
            var temp = number shr 1
            println("==> $number shr 1 = ${temp}, binary:${ Integer.toBinaryString(temp) }")
            number = temp // Right shift the number by 1 bit
            println("---------------------")
        }

        return maxGap
    }

}


fun main() {
    var n =1081
    //println( Codility.findMaxBinaryGap(n))
    //println( Codility.findMaxGap(n))
    //println( Codility.findMaxGap1(n))

    println(Date())

}