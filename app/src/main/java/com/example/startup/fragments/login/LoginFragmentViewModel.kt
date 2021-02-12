package com.example.startup.fragments.login

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.startup.SecondActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragmentViewModel:ViewModel() {


    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private var auth: FirebaseAuth = Firebase.auth

    val RC_SIGN_IN: Int = 1

    fun performLogin(email:String,password:String,context: Context) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("Login", "Successfully logged in: ${it.result?.user?.uid}")
                navigateToHome()

            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT)
                    .show()

        }
    }
    fun handleResult(requestCode:Int,resultCode: Int,data:Intent,context: Context){
        if (requestCode == RC_SIGN_IN ) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken!!, context)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed===============================================================================================================================", e)
            }
        }else {
            Toast.makeText(context, "Problem in execution order :(", Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String,context: Context) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    navigateToHome()
//                    val intent = Intent(context, SecondActivity::class.java)
//                    context.startActivity(intent)

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication Failed.", Toast.LENGTH_LONG).show()
                }
            }
    }




    fun navigateToHome(){
        _navigateToHome.value = true
    }

    fun navigateToHomeComplete(){
        _navigateToHome.value = false
    }
}