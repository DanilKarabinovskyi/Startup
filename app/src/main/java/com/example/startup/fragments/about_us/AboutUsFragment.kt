package com.example.startup.fragments.about_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.startup.R
import com.example.startup.databinding.FragmentArticleBinding
import kotlinx.android.synthetic.main.fragment_about_us.*
import kotlinx.android.synthetic.main.fragment_about_us.view.*

class AboutUsFragment: Fragment() {
    private lateinit var binding: AboutUsFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_about_us, container, false)

//        val inputText = rootView.outlinedTextField.editText?.text.toString()

//        outlinedTextField.editText?.doOnTextChanged { inputText, _, _, _ ->
//            // Respond to input text change
//        }
        return rootView
    }
}