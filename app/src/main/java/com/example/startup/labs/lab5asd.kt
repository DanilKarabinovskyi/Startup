package com.example.startup.labs

import java.util.*

fun main()= with(Scanner(System.`in`)){
    class  Hash{
        var key = mutableListOf<Int>()
        var value = mutableListOf<Int>()
        fun add(keyV:Int,valueV:Int){
            key.add(keyV)
            value.add(valueV)
        }
        fun remove(keyV:Int){
            value.removeAt(key.indexOf(keyV))
            key.remove(keyV)
        }
        fun change(keyV:Int,newValueV:Int){
            value[key.indexOf(keyV)] = newValueV
        }
        fun print(){
            for (i in 0 until key.size){
                println("key: ${key[i]} value: ${value[i]}")
            }
        }
    }

    var hash = Hash()
    for(i in 0..8){
        hash.add(i, (0..1000000).random())
    }
    hash.print()
    println("operations")
    hash.remove(3)
    hash.add(8,777)
    hash.change(1,9999)
    hash.remove(8)
    hash.print()
}