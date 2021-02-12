package com.example.startup.fragments.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.startup.R
import com.example.startup.SecondActivity
import com.example.startup.databinding.FragmentRegisterBinding
import com.example.startup.fragments.login.LoginFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(){
    private lateinit var binding: FragmentRegisterBinding

    private lateinit var viewModel: RegisterFragmentViewModel



    @SuppressLint("LongLogTag")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getPhoto(requestCode, resultCode, data,requireContext(),selectphoto_imageview_register)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(RegisterFragmentViewModel::class.java)
        binding.registerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.registerButtonRegister.setOnClickListener {
            viewModel.performRegister(
                requireContext(),
                binding.emailEdittextRegister.text.toString(),
                binding.passwordEdittextRegister.text.toString(),
                binding.usernameEdittextRegister.text.toString())
        }
        binding.alreadyHaveAccountTextView.setOnClickListener {
            val manager: FragmentManager? = activity?.supportFragmentManager
            val transaction: FragmentTransaction? = manager!!.beginTransaction()
            transaction!!.replace(R.id.fragment_container, LoginFragment())
            transaction.commit()
        }
        binding.selectphotoButtonRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }
        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                val intent = Intent(context, SecondActivity::class.java)
                context!!.startActivity(intent)
                viewModel.navigateToHomeComplete()
            }
        })


        return binding.root
    }

}