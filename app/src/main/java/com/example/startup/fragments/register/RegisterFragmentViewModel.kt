package com.example.startup.fragments.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.service.controls.ControlsProviderService

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.decodeBitmap
import androidx.core.graphics.decodeDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.startup.databinding.FragmentRegisterBinding
import com.example.startup.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*

class RegisterFragmentViewModel:ViewModel() {

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private  var selectedPhotoUri: Uri? = null

    fun performRegister(context: Context, email:String, password:String,name:String) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }
        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                // else if successful
                Log.d(TAG, "Successfully created user with uid: ${it.result?.user?.uid}")
                uploadImageToFirebaseStorage(name)
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to create user: ${it.message}")
                Toast.makeText(context, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage(name:String) {

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        if(selectedPhotoUri != null){
            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "File Location: $it")

                        saveUserToFirebaseDatabase(name, it.toString())
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to upload image to storage: ${it.message}")
                }
        }
        else {
            saveUserToFirebaseDatabase(name, "")
        }
    }

    private fun saveUserToFirebaseDatabase(name:String,profileImageUrl: String ) {

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        var user = User(uid, name, profileImageUrl)

        val userFb = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
            photoUri = profileImageUrl.toUri()
        }

        userFb!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }


        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Finally we saved the user to Firebase Database")
                navigateToHome()
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to set value to database: ${it.message}")
            }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getPhoto(
        requestCode: Int, resultCode: Int, data: Intent?,
        context: Context,
        photoView: CircleImageView
        ){
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            selectedPhotoUri = data.data!!
            val image = context?.contentResolver?.let { ImageDecoder.createSource(it,
                selectedPhotoUri!!
            ) }
            val bitmap = image?.let { ImageDecoder.decodeBitmap(it) }
            photoView.setImageBitmap(bitmap)
        }
    }

    fun navigateToHome(){
        _navigateToHome.value = true
    }

    fun navigateToHomeComplete(){
        _navigateToHome.value = false
    }

}