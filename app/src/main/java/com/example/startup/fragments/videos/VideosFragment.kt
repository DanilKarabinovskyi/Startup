package com.example.startup.fragments.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.startup.R
import com.example.startup.SecondActivity
import com.example.startup.databinding.FragmentVideosBinding
import java.util.*

class VideosFragment(private  val lessonArg: String, private  val languageArg: String) :Fragment() {

    private lateinit var binding: FragmentVideosBinding
    private lateinit var viewModel: VideosFragmentViewModel
    private lateinit var language:String
    private lateinit var lesson:String
    lateinit var  adapter: VideosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_videos,
            container,
            false)
        viewModel = ViewModelProvider(this).get(VideosFragmentViewModel::class.java)
        binding.videosViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        lesson = lessonArg.toLowerCase(Locale.ROOT)
        language = languageArg
        val manager = GridLayoutManager(requireActivity() as SecondActivity, 1)
        adapter = VideosAdapter()
        binding.videosRecycler.adapter = adapter
        binding.videosRecycler.layoutManager = manager
        viewModel.getList(lesson,language,adapter)
        return binding.root
    }

}