package com.example.startup.fragments.collection_topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.startup.R
import com.example.startup.SecondActivity
import com.example.startup.databinding.FragmentHomeBinding
import com.example.startup.databinding.FragmentTabLayoutBinding
import com.example.startup.fragments.article.ArticleFragment
import com.example.startup.fragments.code_example.CodeExampleFragment
import com.example.startup.fragments.home.HomeFragmentViewModel
import com.example.startup.fragments.links_to_articles.LinksToArticlesFragment
import com.example.startup.fragments.videos.VideosFragment
import kotlinx.android.synthetic.main.fragment_tab_layout.*
import java.util.*

class CollectionTopicFragment : Fragment() {

    private lateinit var demoCollectionPagerAdapter: TopicCollectionPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var language:String
    private lateinit var lesson:String
    private lateinit var binding: FragmentTabLayoutBinding
    private lateinit var viewModel: CollectionTopicFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tab_layout,
            container,
            false)
        viewModel = ViewModelProvider(this).get(CollectionTopicFragmentViewModel::class.java)

        binding.collectionViewModel = viewModel

        val arguments = arguments?.let { CollectionTopicFragmentArgs.fromBundle(it) }

        language = arguments!!.language
        lesson = arguments.lessonName
        lesson = lesson.toLowerCase(Locale.ROOT)
        lesson = lesson.replace(" ","")
        binding.completedButton.setOnClickListener { viewModel.completed(lesson,language,
            requireActivity() as SecondActivity
        ) }

        demoCollectionPagerAdapter = TopicCollectionPagerAdapter(childFragmentManager)

        viewPager = binding.viewpager

        var firstFragment = ArticleFragment(lesson,language)
        var secondFragment = VideosFragment(lesson,language)
        var thirdFragment = LinksToArticlesFragment(lesson,language)
        var fourthFragment = CodeExampleFragment(lesson,language)

        viewPager.adapter = demoCollectionPagerAdapter

        demoCollectionPagerAdapter.addFragment(firstFragment,"Article")
        demoCollectionPagerAdapter.addFragment(secondFragment,"Videos")
        demoCollectionPagerAdapter.addFragment(thirdFragment,"links")
        demoCollectionPagerAdapter.addFragment(fourthFragment,"Examples")

        demoCollectionPagerAdapter.notifyDataSetChanged()

        return binding.root
    }
}