package com.example.startup.fragments.links_to_articles

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.startup.databinding.LinkToArticleLayoutBinding
import com.example.startup.fragments.lessons.LessonsAdapter
import com.example.startup.models.Lesson
import com.example.startup.models.LinkToArticle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LinksToArticlesAdapter(
   internal val lesson: String,
   val language: String,
   val clickListener: LinkToArticleListener
):
    ListAdapter<LinkToArticle, LinksToArticlesAdapter.ViewHolder>(DeadlinesDiffCallback()) {
    class ViewHolder private constructor(val binding: LinkToArticleLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = LinkToArticleLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
        fun bind(text:String,item: LinkToArticle, clickListener: LinkToArticleListener) {
            binding.linkToArticle = item
            binding.linkToArticleTextView.text = text
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        lateinit var linksToArticlesDescription:List<String>
        val db = Firebase.firestore
        val docRef = db.collection("articles").document(language).collection("basics").document(lesson)
        Log.d(ContentValues.TAG,"before----------------------------------------------------------------------------")
        docRef.get().addOnCompleteListener { task ->
            Log.d(ContentValues.TAG,"in-------------------------------------------------------------------------------------")
            if (task.isSuccessful) {
                linksToArticlesDescription = task.result!!.data!!["descriptionLinksToArticles"] as List<String>
                when (holder) {
                    else -> {
                        val item = getItem(position)
                        holder.bind(linksToArticlesDescription[position],item, clickListener)
                    }
                }
                val item = getItem(position)
                holder.bind(linksToArticlesDescription[position],item,clickListener)
            }
        }.addOnFailureListener {
                e -> Log.e(ContentValues.TAG, "Error writing document", e)
        }

    }
}
class DeadlinesDiffCallback: DiffUtil.ItemCallback<LinkToArticle>(){
    override fun areItemsTheSame(oldItem: LinkToArticle, newItem: LinkToArticle): Boolean {
        return oldItem.linkUrl== newItem.linkUrl
    }
    override fun areContentsTheSame(oldItem: LinkToArticle, newItem: LinkToArticle): Boolean {
        return oldItem == newItem
    }
}
class LinkToArticleListener(val clickListener: (link:String) -> Unit) {
    fun onClick(linkToArticle: LinkToArticle) = clickListener(linkToArticle.linkUrl)
}