package com.example.startup.fragments.code_example

import android.content.ContentValues
import android.util.Log
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CodeExampleFragmentViewModel : ViewModel() {
    private lateinit var gettedArticle:String
    fun getList(lesson:String,language: String,article: TextView){
        val db = Firebase.firestore
        val docRef = db.collection("articles").document(language).collection("basics").document(lesson)
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result?.data?.get("codeExample") != null) {
                    gettedArticle = task.result?.data?.get("codeExample") as String
                    if (substring(gettedArticle, "<pre>")) {
                        gettedArticle = replaceRealization(gettedArticle, "<pre>", "\t\t\t")
                    }
                    article.text =
                        HtmlCompat.fromHtml(gettedArticle, HtmlCompat.FROM_HTML_MODE_LEGACY)
                } else {
                    Log.d(ContentValues.TAG, "get failed with ", task.exception)
                }
            }
        }.addOnFailureListener {
                e -> Log.e(ContentValues.TAG, "Error writing document", e)

        }
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
}