package com.example.startup.fragments.article

import android.content.ContentValues
import android.util.Log
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ArticleFragmentViewModel:ViewModel() {
    private lateinit var gettedArticle:String
    fun getList(lesson:String,language: String,article:TextView){
        val db = Firebase.firestore
        val docRef = db.collection("articles").document(language).collection("basics").document(lesson)
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                gettedArticle = task.result!!.data!!["value"] as String
                var newGettedArticle = gettedArticle.replace("<pre>","\t\t\t")
                article.text = HtmlCompat.fromHtml(newGettedArticle, HtmlCompat.FROM_HTML_MODE_LEGACY)
                Log.d(ContentValues.TAG, "DocumentSnapshot data: " + task.result!!.data!!["value"])
            } else {
                Log.d(ContentValues.TAG, "get failed with ", task.exception)
            }
        }.addOnFailureListener {
                e -> Log.e(ContentValues.TAG, "Error writing document", e)

        }
    }

}