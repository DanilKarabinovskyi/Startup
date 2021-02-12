package com.example.startup.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.startup.R
import com.example.startup.R.layout
import com.example.startup.databinding.FragmentHomeBinding
import com.example.startup.fragments.home.HomeFragmentDirections.actionHomeFragmentToLessonsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class HomeFragment:Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeFragmentViewModel

    @SuppressLint("LongLogTag", "CommitPrefEdits", "ShowToast", "WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
        binding = DataBindingUtil.inflate(
        inflater,
        layout.fragment_home,
        container,
        false)
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        binding.homeViewModel = viewModel
        val uid = FirebaseAuth.getInstance().uid
        val user = Firebase.auth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        user!!.displayName.let {
            if (it != null) {
                Log.d(
                    "ControlsProviderServic-------------------------------------------------------------------------------------------e.TAG",
                    it.toString()
                )
            }
        }
        viewModel.createProgress()
        binding.cardKotlin.setOnClickListener {
            navController.navigate(actionHomeFragmentToLessonsFragment("kotlin"))
        }
        binding.cardPython.setOnClickListener {
            navController.navigate(actionHomeFragmentToLessonsFragment("python"))
        }
        binding.cardJava.setOnClickListener {
            navController.navigate(actionHomeFragmentToLessonsFragment("java"))
        }
        binding.cardJavaScript.setOnClickListener {
            navController.navigate(actionHomeFragmentToLessonsFragment("javascript"))
        }

        return binding.root
    }
}