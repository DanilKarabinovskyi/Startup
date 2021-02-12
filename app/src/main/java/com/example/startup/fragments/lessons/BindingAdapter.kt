package com.example.startup.fragments.lessons

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.example.startup.R
import com.example.startup.models.Lesson

@BindingAdapter("lessonTitle")
fun TextView.setTitle(item: Lesson) {
    text = item.name
}

@BindingAdapter("lessonId")
fun TextView.setId(item: Lesson) {
    text = item.id.toString()
}

@BindingAdapter("lessonBackground")
fun ConstraintLayout.setBackground(item: Lesson) {
   if(item.readed){
        background = R.drawable.rounded_button.toDrawable()
   }else{
       background = R.drawable.rounded_button_blue.toDrawable()
   }
}