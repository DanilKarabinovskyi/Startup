package com.example.startup.fragments.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.startup.databinding.VideoLayoutBinding
import com.example.startup.models.YouTubeVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideosAdapter() : ListAdapter<YouTubeVideo, VideosAdapter.ViewHolder>(DeadlinesDiffCallback()) {
    class ViewHolder private constructor(val binding: VideoLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = VideoLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
        fun bind(item: YouTubeVideo) {
              binding.youtubeVideoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                            val videoId = "${item.videoId}"
                            youTubePlayer.cueVideo(videoId, 0f)
                }
            })
//            binding.youtubeVideoView.getPlayerUiController().setFullScreenButtonClickListener(View.OnClickListener {
//                if (binding.youtubeVideoView.isFullScreen()) {
//                    binding.youtubeVideoView.exitFullScreen()
//                    requireCo.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//                    // Show ActionBar
//                    if (supportActionBar != null) {
//                        supportActionBar!!.show()
//                    }
//                } else {
//                    binding.youtubeVideoView.enterFullScreen()
//                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//                    // Hide ActionBar
//                    if (supportActionBar != null) {
//                        supportActionBar!!.hide()
//                    }
//                }
//            })
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            else -> {
                val item = getItem(position)
                holder.bind(item)
            }
        }
        val item = getItem(position)
        holder.bind(item)
    }
}
class DeadlinesDiffCallback: DiffUtil.ItemCallback<YouTubeVideo>() {
    override fun areItemsTheSame(oldItem: YouTubeVideo, newItem: YouTubeVideo): Boolean {
        return oldItem.videoId == newItem.videoId
    }

    override fun areContentsTheSame(oldItem: YouTubeVideo, newItem: YouTubeVideo): Boolean {
        return oldItem == newItem
    }

}