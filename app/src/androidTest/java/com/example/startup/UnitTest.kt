package com.example.startup

import com.example.startup.models.Lesson
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import org.junit.Test

class UnitTest{

    var lessons = mutableListOf<Lesson>(Lesson(0,"links",false),Lesson(0,"cycles",false),
        Lesson(0,"ez types",false),Lesson(0,"syntax",false),
        Lesson(0,"testing",false),Lesson(0,"binding",false))

    var sortedLessons = mutableListOf<Lesson>(Lesson(0,"binding",false),Lesson(0,"cycles",false),
        Lesson(0,"ez types",false),Lesson(0,"links",false),
        Lesson(0,"syntax",false),Lesson(0,"testing",false))

    @Test
    fun testSubstring() {
        var result = substring("canunivesrsityteachstudentsaswellastheyexpected", "well")
        assertEquals(true, result)
    }
    @Test
    fun testReplace(){
        var str = "canunivesrsityteachstudentsaswellastheyexpectedverywell"
        var resultMyRealization = replaceRealization(str,"well","SOCOOL")
        var result = str.replace("well","SOCOOL")
        assertEquals(resultMyRealization,result)
    }
    @Test
    fun testSorting(){
        sort()
        var result = sortedLessons
        assertEquals(lessons,result)
    }

    fun substring(string: String, subString: String): Boolean {
        if (string.length < subString.length) return false
        var patternHash = 0
        var currentHash = 0
        for (i in 0 until subString.length) {
            patternHash += subString[i].toInt()
            currentHash += string[i].toInt()
        }
        val end = string.length - subString.length + 1
        for (i in 0 until end) {
            if (patternHash == currentHash) if (string.regionMatches(
                    i,
                    subString,
                    0,
                    subString.length
                )
            ) return true
            currentHash -= string[i].toInt()
            if (i != end - 1) currentHash += string[i + subString.length].toInt()
        }
        return false
    }

    fun replaceRealization(string: String, old: String, new:String): String {
        var arrayAfterSplit = string.split(old)
        var str = ""
        arrayAfterSplit.forEach {
            if(it != ""){
                str += "$it" + "$new"
            }
        }
        return str
    }

    fun sort(){
        val size = lessons.size
        for (i in 0 until size){
            for(j in 0 until size){
                if(lessons[i].name < lessons[j].name  ){
                    var tmp = lessons[i]
                    lessons[i] = lessons[j]
                    lessons[j] = tmp
                }
            }
        }
    }
}