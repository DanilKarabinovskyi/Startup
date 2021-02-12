package com.example.startup.fragments.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.startup.R
import com.example.startup.databinding.LessonLayoutBinding
import com.example.startup.models.Lesson

class LessonsAdapter(val clickListener: LessonListener):
    ListAdapter<Lesson, LessonsAdapter.ViewHolder>(DeadlinesDiffCallback()) {
    class ViewHolder private constructor(val binding: LessonLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = LessonLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
        fun bind(item: Lesson,clickListener: LessonListener) {
            if(item.readed){
                binding.constrain.setBackgroundResource(R.drawable.rounded_button)
            }
            else{
                binding.constrain.setBackgroundResource(R.drawable.rounded_button_blue)
            }
            binding.lesson = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            else -> {
                val item = getItem(position)
                holder.bind(item, clickListener)
            }
        }
        val item = getItem(position)
        holder.bind(item,clickListener)
    }
}
class DeadlinesDiffCallback: DiffUtil.ItemCallback<Lesson>(){
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
        return oldItem.id== newItem.id
    }

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
        return oldItem == newItem
    }

}

class LessonListener(val clickListener: (topicName:String) -> Unit) {
    fun onClick(lesson: Lesson) = clickListener(lesson.name)
}