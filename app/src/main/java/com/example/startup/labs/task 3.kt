package com.example.startup.labs

import java.util.*

fun main()= with(Scanner(System.`in`)){
    val numbers: IntArray = intArrayOf( 5 , 112 , 4 , 3 )
    for (i in 3 downTo 0){
        var num = numbers[i]
        print("$num " + " ")
    }
}