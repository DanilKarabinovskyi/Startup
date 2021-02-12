package com.example.startup.labs

import java.util.*

fun main()= with(Scanner(System.`in`)){
    var max = -77777.7
    val numbers = DoubleArray(10)
    print("array before: ")
    for (i in 0..9){
        numbers[i] = (0..100).random().toDouble()
        val a = numbers[i]
        print("$a ")
        if(numbers[i] >= 27){
            numbers[i] = numbers[i] / 2
        }
        if(numbers[i] > max){
            max = numbers[i]
        }
    }
    print("\n")
    print("array: ")
    numbers.forEach {print("$it ")}
    print("\n")
    print("Max: $max")
    fun shellsSort(compareAndAppropriate:IntArray, array: IntArray) {
        var i: Int
        var j: Int
        var l: Int
        val size = array.size
        var step = size
        do{
            step /= 2
            i = step
            while (i < size) {
                j = i
                l = array[i]
                compareAndAppropriate[0]++
                while (j >= step && array[j - step] > l) {
                    compareAndAppropriate[0]++
                    array[j] = array[j - step]
                    compareAndAppropriate[1]++
                    j -= step
                }
                array[j] = l
                i++
            }
        } while(step != 1)
    }
}

