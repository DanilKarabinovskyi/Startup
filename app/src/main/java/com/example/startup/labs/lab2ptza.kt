package com.example.startup.labs

import java.util.*
import kotlin.math.abs
import kotlin.math.pow

fun main()= with(Scanner(System.`in`)) {
    while(true){
    print(
        "Enter command(1 to convert natural number,\n\t" +
                "2 to convert double number,\n")
    when(nextDouble()) {
        1.0 -> {
            print("Enter number:")
            var number = nextDouble()
            var copyNumb = number
            number = abs(number)
            var size = 0
            var tmp = 2.0
            while (tmp.pow(size) <= number) {
                size++
            }
            print("Integer:")
            val binary = BooleanArray(size)
            for (i in 0 until size) {
                if (number % 2.0 == 0.0) {
                    binary[i] = false
                    number /= 2
                } else {
                    binary[i] = true
                    number--
                    number /= 2
                }
            }
            for (i in size - 1 downTo 0) {
                if (binary[i])
                    print(1)
                else
                    print(0)
            }
            print("*2^0")
            print("\n")
            print("Double: 0.")
            for (i in size - 1 downTo 0) {
                if (binary[i])
                    print(1)
                else
                    print(0)
            }
            print("*2^$size")
            print("\n")
        }
        2.0 -> {
            print("Enter number:")
            var countSize = 0.0
            var number = nextDouble()
            var copyNumb = number
            number = abs(number)
            print("Enter number of digits:")
            var digits = nextInt()
            val binary = BooleanArray(digits)
            for (i in 0 until digits) {
                number *= 2.0
                if (number >= 1) {
                    number -= 1.0
                    binary[i] = true
                }
            }
            for (i in 0 until 32) {
                if (!binary[i]) {
                    countSize++
                }
                else{
                    break
                }
            }
            print("Double: ")
            print("0.")
            for (i in 0 until digits) {
                if (binary[i])
                    print(1)
                else
                    print(0)
            }
            print(" 2^0")
            print("\n")
            print("Integer: ")
            for (i in 0 until digits) {
                if (binary[i])
                    print(1)
                else
                    print(0)
            }
            print(" 2^-$digits")
            print("\n")
        }
        3.0 -> {
            return
        }
        else -> {
            print("WRONG INPUT!!!\n")
        }
    }
    }
}