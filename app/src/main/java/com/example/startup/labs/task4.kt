package com.example.startup.labs

import java.util.*

fun main()= with(Scanner(System.`in`)){
    //Обявление и заполнение массивов
    //Массив для сравнения количества копирований и сравнений
    var checkCompares: IntArray = intArrayOf(0,0)
    var numbers1Quick = mutableListOf<Int>()
    var numbers2Quick = mutableListOf<Int>()
    var numbers3Quick = mutableListOf<Int>()
    var numbers1=  IntArray(100)
    var numbers2=  IntArray(1000)
    var numbers3 = IntArray(10000)
    for (i in 0..99){
        numbers1Quick.add((0..1000).random())
    }
    for (i in 0..999){
        numbers2Quick.add((0..1000).random())
    }
    for (i in 0..9999){
        numbers3Quick.add((0..1000).random())
    }
    for (i in 0..99){
        numbers1[i] = (0..1000).random()
    }
    for (i in 0..999){
        numbers2[i] = (0..1000).random()
    }
    for (i in 0..9999){
        numbers3[i] = (0..1000).random()
    }


    //1st Array
    println("ONE")
    print("\n")
    var timeBefore = System.currentTimeMillis()
    //Сорт
    bubbleSort(checkCompares, numbers1)
    //Сравнение времени
    var timeAfter = System.currentTimeMillis() - timeBefore
    print("Time1 : $timeAfter\n")
    print(" Compare and appropriate:")
    checkCompares.forEach { print("$it  ") }
    checkCompares = intArrayOf(0,0)
    print("\n")
    print("Array before sort(100 elements):")
    numbers1Quick.forEach{
        print("$it ")
    }
    print("\n")
    timeBefore = System.currentTimeMillis()
    //Сорт
    var newNumbers1 =
        quicksort(checkCompares, numbers1Quick)
    //Сравнение времени
    timeAfter = System.currentTimeMillis() - timeBefore
    print("Array after sort:")
    newNumbers1.forEach{
        print("$it ")
    }
    print("\n")
    print("Time2 : $timeAfter\n")
    print(" Compare and appropriate:")
    checkCompares.forEach { print("$it  ") }
    checkCompares = intArrayOf(0,0)

    print("\n")


    //2nd Array
    println("TWO")
    print("\n")
    timeBefore = System.currentTimeMillis()
    //Сорт
    bubbleSort(checkCompares, numbers2)
    timeAfter = System.currentTimeMillis() - timeBefore
    print("Time1 : $timeAfter\n")
    print(" Compare and appropriate:")
    checkCompares.forEach { print(" $it  ") }
    checkCompares = intArrayOf(0,0)
    print("\n")
    timeBefore = System.currentTimeMillis()
    //Сорт
    var newNumbers2 =
        quicksort(checkCompares, numbers2Quick)
    timeAfter = System.currentTimeMillis() - timeBefore
    print("Time2 : $timeAfter\n")
    print(" Compare and appropriate:")
    checkCompares.forEach { print(" $it  ") }
    checkCompares = intArrayOf(0,0)

    print("\n")


    //3rd Array
    println("THREE")
    print("\n")
    timeBefore = System.currentTimeMillis()
    bubbleSort(checkCompares, numbers3)
    timeAfter = System.currentTimeMillis() - timeBefore
    print("Time1 : $timeAfter\n")
    print(" Compare and appropriate:")
    checkCompares.forEach { print(" $it  ") }
    checkCompares = intArrayOf(0,0)
    print("\n")
    timeBefore = System.currentTimeMillis()
    var newNumbers3 =
        quicksort(checkCompares, numbers3Quick)
    timeAfter = System.currentTimeMillis() - timeBefore

    print("Time2 : $timeAfter\n")
    print(" Compare and appropriate:")
    checkCompares.forEach { print(" $it  ") }

}

fun bubbleSort(checkCompares:IntArray,array:IntArray){
    var temp: Int
    for (i in array.size - 1 downTo 1) {
        for (j in 0 until i) {
            checkCompares[0]++
            if (array[j] > array[j + 1]) {
                temp = array[j]
                array[j] = array[j + 1]
                array[j + 1] = temp
                checkCompares[1]++
            }
        }
    }
}
fun quicksort(checkCompares:IntArray,items:List<Int>):List<Int>{
    if (items.count() < 2){
        return items
    }
    val pivot = items[items.count()/2]

    val equal = items.filter {
        it == pivot
    }
    val less = items.filter {
        it < pivot
    }
    val greater = items.filter {
        it > pivot
    }

    checkCompares[0]++
    checkCompares[1]++
    return quicksort(
        checkCompares,
        less
    ) + equal + quicksort(checkCompares, greater)
}
