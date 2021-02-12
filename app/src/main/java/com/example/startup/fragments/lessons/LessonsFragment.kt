package com.example.startup.fragments.lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.startup.R
import com.example.startup.SecondActivity
import com.example.startup.databinding.FragmentLessonsBinding

class LessonsFragment : Fragment() {
    private lateinit var binding:FragmentLessonsBinding
    private lateinit var language:String
    private lateinit var viewModel: LessonsFragmentViewModel
    lateinit var  adapter: LessonsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lessons,
            container,
            false)
        viewModel = ViewModelProvider(this).get(LessonsFragmentViewModel::class.java)
        binding.lessonsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val arguments = arguments?.let { LessonsFragmentArgs.fromBundle(it) }
        language = arguments!!.language
        adapter = LessonsAdapter(LessonListener { lessonName ->
            findNavController().navigate(LessonsFragmentDirections
                .actionLessonsFragmentToCollectionTopicFragment(lessonName, language))
        })
        binding.lessonsList.adapter = adapter
        val manager = GridLayoutManager(requireActivity() as SecondActivity, 1)
        binding.lessonsList.layoutManager = manager
        viewModel.getList(language,adapter)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onOptionsItemSelected(thisItem: MenuItem): Boolean {
        when (thisItem.itemId) {
            R.id.sort_menu_item -> {
                viewModel.sort()
                adapter.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(thisItem)
    }
}