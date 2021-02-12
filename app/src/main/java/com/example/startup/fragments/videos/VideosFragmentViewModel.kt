package com.example.startup.fragments.videos

import android.content.ContentValues
import android.util.Log
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import com.example.startup.models.Lesson
import com.example.startup.models.YouTubeVideo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VideosFragmentViewModel:ViewModel() {
    var videosUrls:MutableList<YouTubeVideo> = mutableListOf()
    lateinit var videosUrlsLite:List<String>
    fun getList(lesson:String,language: String,adapter: VideosAdapter){
        val db = Firebase.firestore
        val docRef = db.collection("articles").document(language).collection("basics").document(lesson)
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                videosUrlsLite = task.result!!.data!!["links"] as List<String>
                if(videosUrls.isEmpty() ){
                    var count = 0
                    videosUrlsLite.forEach {
                        videosUrls.add(count, YouTubeVideo(videoId = it))
                        count++
                    }
                }
                adapter.submitList(videosUrls)
                Log.d(ContentValues.TAG, "DocumentSnapshot data: " + task.result!!.data!!["value"])
            } else {
                Log.d(ContentValues.TAG, "get failed with ", task.exception)
            }
        }.addOnFailureListener {
                e -> Log.e(ContentValues.TAG, "Error writing document", e)

        }
    }
}