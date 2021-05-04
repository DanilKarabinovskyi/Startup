package com.example.startup.fragments.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.startup.R
import com.example.startup.databinding.FragmentArticleBinding
import com.example.startup.fragments.collection_topic.CollectionTopicFragment
import java.util.*

class ArticleFragment(private  val lessonArg: String, private  val languageArg: String) : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var language:String
    private lateinit var lesson:String
    private lateinit var viewModel: ArticleFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding= DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_article,
        container,
        false)
        lesson = lessonArg
        language = languageArg
        viewModel = ViewModelProvider(this).get(ArticleFragmentViewModel::class.java)
        binding.articleViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getList(lesson,language,binding.webView)
        return binding.root
    }

}