package com.example.startup.fragments.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.startup.models.Lesson
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException

class HomeFragmentViewModel:ViewModel() {
    fun createProgress(){
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val docRef = db.collection("progress").document(user!!.uid)

        docRef.get().addOnCompleteListener { firstTask ->
            Log.d(ContentValues.TAG,"in----------------")
            if (firstTask.isSuccessful) {
                if(firstTask.result!!.exists()){
                    return@addOnCompleteListener
                }else{
                    createLists()
                }
            } else {
                createLists()
            }
        }.addOnFailureListener {
        }
    }
    fun createLists(){
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val docRef = db.collection("progress").document(user!!.uid)
        val lang = mutableListOf<String>("python","kotlin","java", "javascript")
        var array = Array(4){ mutableListOf<Boolean>() }
        for(i in 0..3){
            val docRefUse = db.collection("articles").document(lang[i]).collection("titles").document("value").get().addOnCompleteListener { task ->
                Log.d(ContentValues.TAG,"in-------------------------------------------------------------------------------------")
                if (task.isSuccessful) {
                    var lessons = task.result!!.data!!["array"] as List<String>
                    for(j in lessons.indices){
                        array[i].add(false)
                    }

                    docRef.set(hashMapOf(
                        lang[0] to array[0],
                        lang[1] to array[1],
                        lang[2] to array[2],
                        lang[3] to array[3]
                    ))
                    if(i == 3){

                    }
                } else {
                    Log.d(ContentValues.TAG, "get failed with ", task.exception)
                }
            }.addOnFailureListener {
                    e -> Log.e(ContentValues.TAG, "Error writing document", e)
            }

        }
    }
}