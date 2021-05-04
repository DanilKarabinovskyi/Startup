package com.example.startup.fragments.lessons

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.startup.models.Lesson
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class LessonsFragmentViewModel:ViewModel() {
    private val uiScope = CoroutineScope(Dispatchers.IO)
    lateinit var lessonsLite:List<String>
    lateinit var lessonsProgress:List<Boolean>
    var lessons:MutableList<Lesson> = mutableListOf()

    fun getList(language: String,adapter: LessonsAdapter){
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val docRefProgress = db.collection("progress").document(user!!.uid)
        val docRef = db.collection("articles").document(language).collection("titles").document("value")
            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result?.data?.get("array") != null) {
                        lessonsLite = task.result!!.data!!["array"] as List<String>?
                            ?: mutableListOf<String>() as List<String>
                        docRefProgress.get().addOnCompleteListener { firstTask ->
                            if (firstTask.isSuccessful) {
                                lessonsProgress =
                                    (firstTask.result!!.data!![language] as List<Boolean>).toMutableList()
                                for (i in lessons.indices) {
                                    lessons.removeAt(0)
                                }
                                for (i in lessonsLite.indices) {
                                    lessons.add(
                                        i,
                                        Lesson(i + 1, lessonsLite[i], lessonsProgress[i])
                                    )
                                }
                                adapter.submitList(lessons)
                            } else {
                            }
                        }.addOnFailureListener {

                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.exception)
                    }
                }
        }.addOnFailureListener {
                e -> Log.e(TAG, "Error writing document", e)
        }
    }
}

