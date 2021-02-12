package com.example.startup.fragments.links_to_articles

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.startup.fragments.lessons.LessonsAdapter
import com.example.startup.models.Lesson
import com.example.startup.models.LinkToArticle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LinksToArticlesFragmentViewModel: ViewModel() {
    lateinit var linksToArticlesLite:List<String>
    var linksToArticles:MutableList<LinkToArticle> = mutableListOf()

    fun getList(lesson:String,language: String,adapter: LinksToArticlesAdapter){
        val db = Firebase.firestore
        val docRef = db.collection("articles").document(language).collection("basics").document(lesson)
        Log.d(ContentValues.TAG,"before----------------------------------------------------------------------------")
        docRef.get().addOnCompleteListener { task ->
            Log.d(ContentValues.TAG,"in-------------------------------------------------------------------------------------")
            if (task.isSuccessful) {
                linksToArticlesLite = task.result!!.data!!["linksToArticles"] as List<String>
                var count = 0
                if(linksToArticles.isEmpty() ){
                    linksToArticlesLite.forEach {
                        linksToArticles.add(count, LinkToArticle(linkUrl = it))
                        count++
                    }
                }
                adapter.submitList(linksToArticles)
                Log.d(ContentValues.TAG, "DocumentSnapshot data: " + task!!.result!!.data!!["array"])
            } else {
                Log.d(ContentValues.TAG, "get failed with ", task.exception)
            }
        }.addOnFailureListener {
                e -> Log.e(ContentValues.TAG, "Error writing document", e)
        }
        Log.d(ContentValues.TAG,"after----------------------------------------------------------------------------------------")
    }
}