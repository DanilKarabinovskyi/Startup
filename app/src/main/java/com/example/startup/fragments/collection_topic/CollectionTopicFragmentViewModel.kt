package com.example.startup.fragments.collection_topic

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.startup.R
import com.example.startup.SecondActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CollectionTopicFragmentViewModel:ViewModel() {
    fun completed(lesson:String,language: String,activity: SecondActivity){
        val navController = Navigation.findNavController(activity,R.id.nav_host_fragment)
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val docRefId = db.collection("progress").document(user!!.uid)
        val docRef = db.collection("articles").document(language).collection("titles").document("value")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var lessonsLite = task.result!!.data!!["array"] as List<String>
                docRefId.get().addOnCompleteListener { newTask ->
                    if (newTask.isSuccessful) {
                        var readedList = (newTask.result!!.data!![language] as List<Boolean>).toMutableList()
                        var count = 0
                        lessonsLite.forEach {
                            var newIt = it.replace(" ", "")
                            newIt = newIt.toLowerCase()
                            if(newIt == lesson){
                                readedList[count] = true
                            }
                            count++
                        }
                        docRefId.update(
                            hashMapOf(
                                language to readedList
                            ) as Map<String, Any>
                        )
                        navController.popBackStack()
                    } else {
                        Log.d(ContentValues.TAG, "get failed with ", task.exception)
                    }
                }.addOnFailureListener {
                        e -> Log.e(ContentValues.TAG, "Error writing document", e)
                }

            } else {
                Log.d(ContentValues.TAG, "get failed with ", task.exception)
            }
        }.addOnFailureListener {
                e -> Log.e(ContentValues.TAG, "Error writing document", e)
        }
    }
}
