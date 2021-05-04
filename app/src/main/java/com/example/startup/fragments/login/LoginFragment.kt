package com.example.startup.fragments.login

import android.R.attr.fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.startup.R
import com.example.startup.SecondActivity
import com.example.startup.databinding.FragmentLoginBinding
import com.example.startup.fragments.register.RegisterFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_voice_input.view.*


class LoginFragment:Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: LoginFragmentViewModel

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private var auth: FirebaseAuth = Firebase.auth
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handleResult(requestCode,resultCode,data!!,requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(LoginFragmentViewModel::class.java)
        binding.loginViewModel = viewModel
        binding.toRegisterTextview.setOnClickListener {
            val manager: FragmentManager? = activity?.supportFragmentManager
            val transaction: FragmentTransaction? = manager?.beginTransaction()
            transaction!!.replace(R.id.fragment_container, RegisterFragment())
            transaction.commit()
        }
        binding.loginButtonLogin.setOnClickListener {
            viewModel.performLogin(
                binding.emailEdittextLogin.text.toString(),
                binding.passwordEdittextLogin.text.toString(),
                requireContext())

        }
        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                    val intent = Intent(context, SecondActivity::class.java)
                    requireActivity().finish()
                    context!!.startActivity(intent)
                viewModel.navigateToHomeComplete()
            }
        })
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        mGoogleSignInClient.signOut()
        binding.signInBtn.setOnClickListener{
//            mGoogleSignInClient.signOut()
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, viewModel.RC_SIGN_IN)
        }
        if (auth.currentUser != null) {
            viewModel.navigateToHome()
        }



        return binding.root
    }

}