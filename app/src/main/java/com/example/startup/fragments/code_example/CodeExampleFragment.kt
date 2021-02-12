package com.example.startup.fragments.code_example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.startup.R
import com.example.startup.databinding.FragmentArticleBinding
import com.example.startup.databinding.FragmentCodeExampleBinding
import com.example.startup.fragments.article.ArticleFragmentViewModel
import java.util.*

class CodeExampleFragment(private  val lessonArg: String, private  val languageArg: String) : Fragment() {
    private lateinit var binding: FragmentCodeExampleBinding
    private lateinit var language:String
    private lateinit var lesson:String
    private lateinit var viewModel: CodeExampleFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_code_example,
            container,
            false)
        lesson = lessonArg.toLowerCase(Locale.ROOT)
        language = languageArg
        viewModel = ViewModelProvider(this).get(CodeExampleFragmentViewModel::class.java)
        binding.codeExampleViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getList(lesson,language,binding.codeExample)
        return binding.root
    }
}