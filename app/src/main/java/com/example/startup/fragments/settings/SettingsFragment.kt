package com.example.startup.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.startup.R
import com.example.startup.databinding.FragmentSettingsBinding

class SettingsFragment:Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false)
        binding.textGallery.text = "sdfsdfsf\nsdfsdfs\nsdfsdf"
        return binding.root
    }
}