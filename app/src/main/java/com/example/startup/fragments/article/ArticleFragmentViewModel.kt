package com.example.startup.fragments.article

import android.content.ContentValues
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ArticleFragmentViewModel:ViewModel() {
    private lateinit var gettedArticle:String
    fun getList(lesson:String,language: String,article: WebView){
        val db = Firebase.firestore
        val docRef = db.collection("articles").document(language).collection("basics").document(lesson)
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result?.data?.get("value") != null) {
                    gettedArticle = task.result?.data?.get("value") as String
                    article.loadDataWithBaseURL(null, gettedArticle, "text/html", "utf-8", null);
                } else {
                    Log.d(ContentValues.TAG, "get failed with ", task.exception)
                }
            }
        }.addOnFailureListener {
                e -> Log.e(ContentValues.TAG, "Error writing document", e)

        }
    }

}