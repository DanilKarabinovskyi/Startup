package com.example.startup.labs

fun main(){
    class  HashMap{
        var key = mutableListOf<Int>()//створення масивів
        var value = mutableListOf<Int>()
        fun push(keyV:Int, valueV:Int){
            key.add(keyV)//додавання елемента в массив ключів
            value.add(valueV)//додавання елемента в массив значень
        }
        fun delete(keyV:Int){
            //видалення значення за індексом ключа
            value.removeAt(key.indexOf(keyV))
            //видалення ключа
            key.remove(keyV)
        }
        fun refactor(keyV:Int, newValueV:Int){
            //зміна значення другого массива за ключем
            value[key.indexOf(keyV)] = newValueV
        }
        fun watchAll(){
            for (i in 0 until key.size){
                //вивід ключа та значення
                println("key: ${key[i]} value: ${value[i]}")
            }
        }
    }

    var hash = HashMap()
    for(i in 0..8){
        hash.push(i, (0..1000000).random())
    }
    hash.watchAll()
    println("*after changes")
    hash.delete(1)
    hash.push(10,1001001)
    hash.refactor(2,123456)
    hash.delete(3)
    hash.watchAll()
}