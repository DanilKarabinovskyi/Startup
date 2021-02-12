package com.example.startup.fragments.links_to_articles

import android.content.Intent
import android.net.Uri
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
import com.example.startup.databinding.FragmentLinksToArticlesBinding
import java.util.*


class LinksToArticlesFragment(private  val lessonArg: String, private  val languageArg: String) :Fragment() {
    private lateinit var binding: FragmentLinksToArticlesBinding
    private lateinit var language:String
    private lateinit var lesson:String
    private lateinit var viewModel: LinksToArticlesFragmentViewModel
    lateinit var  adapter: LinksToArticlesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_links_to_articles,
            container,
            false)
        viewModel = ViewModelProvider(this).get(LinksToArticlesFragmentViewModel::class.java)
        binding.linksToArticlesViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        lesson = lessonArg.toLowerCase(Locale.ROOT)
        language = languageArg
        adapter = LinksToArticlesAdapter(lesson,language,LinkToArticleListener { linkUrl ->
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
            startActivity(browserIntent)
        })
        binding.linksToArticlesRecycler.adapter = adapter
        val manager = GridLayoutManager(requireActivity() as SecondActivity, 1)
        binding.linksToArticlesRecycler.layoutManager = manager
        viewModel.getList(lesson,language,adapter)

        return binding.root
    }
}