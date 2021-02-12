package com.example.startup.labs

import java.math.BigDecimal
import java.util.*
import kotlin.math.pow

fun main() {
        val decimal: Int
        val binary: Int
        val first: Double
        val second: Double
        val third: Double
        val fourth: Double
        val scan = Scanner(System.`in`)
        decimal = scan.nextInt()
        binary = scan.nextInt()
        first = scan.nextDouble()
        second = scan.nextDouble()
        third = scan.nextDouble()
        fourth = scan.nextDouble()
        val binary_1: BooleanArray
        val binary_2: BooleanArray
        val firstNum = first / second
        val secondNum = third / fourth
        binary_1 =
            decimalToBinary(firstNum, binary, decimal)
    for(element in binary_1){
        if(element == true){
            print(1)
        }else{print(0)}

    }
    println(" ")
        binary_2 =
            decimalToBinary(secondNum, binary, decimal)
    for(element in binary_2){
        if(element == true){
            print(1)
        }else{print(0)}

    }
    println(" ")
        val sum_b =
            convertToDecimal(binary_1, binary_2, binary,decimal)
        val result_2 = String.format("%.${decimal}f", sum_b)
        println(result_2)
    }

    fun decimalToBinary(
        num: Double,
        binary_precision: Int,
        decimal_precision: Int
    ): BooleanArray {
        var num = num
        val binary = BooleanArray(binary_precision + 1)

        num *= 10.0.pow(decimal_precision.toDouble()+1)//дописать +1
        num = kotlin.math.floor(num)
        num /= 10.0.pow(decimal_precision.toDouble()+1)//дописать +1
        for (i in 1..binary_precision) {
            num *= 2.0
            if (num > 1) {
                num -= 1.0
                binary[i] = true
            }
        }
        return binary
    }

    fun convertToDecimal(
        binary_1: BooleanArray,
        binary_2: BooleanArray,
        binary_precision: Int,
    decimal_precision: Int
    ): Double {
        val ans = BooleanArray(binary_precision+1)
        for (i in binary_precision downTo 0) {
            if (  ans[i] && binary_2[i] && binary_1[i]) {
                ans[i] = true
                ans[i - 1] = true
            }
            else if (ans[i] && binary_1[i] || ans[i] && binary_2[i] || binary_1[i] && binary_2[i]) {
                ans[i] = false
                ans[i - 1] = true
            }
        else if (ans[i] || binary_1[i] || binary_2[i]) {
                ans[i] = true
            }
        }
        var sum = 0.0
        for (i in 0..binary_precision) {
            if (ans[i]) {
                sum += 2.0.pow((-1 * i).toDouble())
            }
        }
        return sum

    }
