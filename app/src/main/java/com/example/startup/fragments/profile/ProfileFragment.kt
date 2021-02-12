package com.example.startup.fragments.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.startup.R
import com.example.startup.databinding.FragmentLoginBinding
import com.example.startup.databinding.FragmentProfileBinding
import com.example.startup.fragments.home.HomeFragment
import com.example.startup.fragments.lessons.LessonsFragmentDirections
import com.example.startup.fragments.login.LoginFragmentViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL

class ProfileFragment:Fragment() {

    lateinit var name: String
    lateinit var userUrl: Uri
    @ObsoleteCoroutinesApi
    @SuppressLint("LongLogTag")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getNewPhoto(
            requestCode,
            resultCode,
            data!!,
            requireContext(),
            imageview_photo_profile
        )

    }

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel
    private val user = Firebase.auth.currentUser
    val url = user!!.photoUrl

    @ObsoleteCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
//        name_user_profile.setText(user!!.displayName)
        viewModel = ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)
        binding.profileViewModel = viewModel
        viewModel.initName(binding)
        binding.imageviewPhotoProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        binding.saveProfileButton.setOnClickListener {
            viewModel.saveChanges(binding)

            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
            Toast.makeText(context, "Please reboot program to see changes", Toast.LENGTH_SHORT).show()
        }

        viewModel.setPhoto(binding)

        return binding.root
    }
}


