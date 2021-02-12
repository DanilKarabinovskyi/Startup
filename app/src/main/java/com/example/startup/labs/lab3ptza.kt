package com.example.startup.labs

import java.util.*
import kotlin.math.abs
import kotlin.math.pow

fun main()= with(Scanner(System.`in`)) {
    var counter = 1
    var firstN = 0.0
    var secondN = 0.0
    if(counter ==1) {
        counter++
        print(
            "Enter first number:\n"
        )
        var firstNum = nextLine()
        var arrayAfterSplit = firstNum.toString().split(".")
        var countSize = 0.0
        var newFirstNum = firstNum.toDouble()
        var copyNum = newFirstNum
        newFirstNum = abs(newFirstNum)
        firstN = newFirstNum
        var size = 0
        var tmp = 2.0
        var rightBinary = BooleanArray(40)
        var left = arrayAfterSplit[0].toInt()
        var right = "0.${arrayAfterSplit[1]}".toDouble()
        var copyLeft = arrayAfterSplit[0].toInt()
        var copyRight = "0.${arrayAfterSplit[1]}".toDouble()
        if (left == 0) {
            size = 1
        } else {
            while (tmp.pow(size) <= newFirstNum) {
                size++
            }
        }
        val leftBinary = BooleanArray(23)
        for (i in 0 until size) {
            if (left % 2.0 == 0.0) {
                leftBinary[i] = false
                left /= 2
            } else {
                leftBinary[i] = true
                left--
                left /= 2
            }
        }
        for (i in 0 until 32) {
            right *= 2.0
            if (right >= 1) {
                right -= 1.0
                rightBinary[i] = true
            }
        }
        for (i in 0 until 32) {
            if (!rightBinary[i]) {
                countSize++
            } else {
                break
            }
        }
        print("Number in binary system: ")
        for (i in size - 1 downTo 0) {
            if (leftBinary[i])
                print(1)
            else
                print(0)
        }
        if (copyRight != 0.0) {
            print(".")
            for (i in 0 until 32) {
                if (rightBinary[i])
                    print(1)
                else
                    print(0)
            }
        }
        print("\n")
        print("Mantisa: ")
        if (copyNum < 1.0) {
            print("0.")
            for (i in 0 until (23 + countSize).toInt()) {
                if (i < 32) {
                    if (rightBinary[i] && i > countSize - 1)
                        print(1)
                    else if (!rightBinary[i] && i > countSize - 1)
                        print(0)
                } else {
                    print(0)
                }
            }
            print("\n")
        } else {
            print("0.")
            for (i in size - 1 downTo 0) {
                if (leftBinary[i])
                    print(1)
                else
                    print(0)
            }
            for (i in 0 until 23 - size) {
                if (rightBinary[i])
                    print(1)
                else
                    print(0)
            }
            print("\n")
        }
        print("Exponent:")
        if (newFirstNum < 1.0) {
            print("1.")
            tmp = 2.0
            size = 0

            while (tmp.pow(size) <= countSize) {
                size++
            }
            var binary = BooleanArray(size)

            for (i in 0 until size) {
                if (countSize % 2.0 == 0.0) {
                    binary[i] = false
                    countSize /= 2
                } else {
                    binary[i] = true
                    countSize--
                    countSize /= 2
                }
            }
            if (size < 7) {
                for (i in size..6) {
                    print(0)
                }
            }
            for (i in size - 1 downTo 0) {
                if (binary[i])
                    print(1)
                else
                    print(0)
            }
            print("\n")
        } else {
            print("0.")
            tmp = size.toDouble()
            size = 0
            while (2.0.pow(size) <= tmp) {
                size++
            }
            var newBinary = BooleanArray(size)
            for (i in 0 until size) {
                if (tmp % 2.0 == 0.0) {
                    leftBinary[i] = false
                    tmp /= 2
                } else {
                    leftBinary[i] = true
                    tmp--
                    tmp /= 2
                }
            }
            if (size < 7) {
                for (i in size..6) {
                    print(0)
                }
            }
            for (i in size - 1 downTo 0) {
                if (leftBinary[i])
                    print(1)
                else
                    print(0)
            }

        }
        print("\n")
    }
}