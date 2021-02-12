package com.example.startup.models

import androidx.lifecycle.LifecycleOwner

data class Lessons (
    var studentList: ArrayList<LessonLite> = arrayListOf()
)