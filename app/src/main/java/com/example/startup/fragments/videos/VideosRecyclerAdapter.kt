package com.example.startup.fragments.videos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.example.startup.R
import com.example.startup.models.YouTubeVideo
import kotlinx.android.synthetic.main.video_layout.view.*

class VideosRecyclerAdapter internal constructor(private val youtubeVideoList: List<YouTubeVideo>) :
    RecyclerView.Adapter<VideosRecyclerAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_layout, parent, false)
        return VideoViewHolder(view)
    }
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.videoWeb.loadData(youtubeVideoList[position].videoId, "text/html", "utf-8")
    }
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoWeb: WebView = itemView.findViewById(R.id.youtube_video_view)
        init {
            videoWeb.settings.javaScriptEnabled = true
            videoWeb.webChromeClient = object : WebChromeClient() {
            }
        }
    }
    override fun getItemCount(): Int {
        return youtubeVideoList.size
    }
}