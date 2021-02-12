package com.example.startup.fragments.voice_input

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.startup.R
import com.example.startup.databinding.FragmentVoiceInputBinding
import com.example.startup.fragments.lessons.*
import java.util.*

class VoiceInput: Fragment() {
    private lateinit var binding: FragmentVoiceInputBinding
    private lateinit var language:String
    private lateinit var viewModel: VoiceInputViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_voice_input,
            container,
            false)
        viewModel = ViewModelProvider(this).get(VoiceInputViewModel::class.java)
        binding.voiceInputViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val arguments = arguments?.let { LessonsFragmentArgs.fromBundle(it) }
        binding.button.setOnClickListener {
            speak()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100){
            if(resultCode ==Activity.RESULT_OK && data != null){
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.text.text = result!![0]
            }
        }
    }
    fun speak(){
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi, lets speak")
        startActivityForResult(mIntent,100)
    }

}