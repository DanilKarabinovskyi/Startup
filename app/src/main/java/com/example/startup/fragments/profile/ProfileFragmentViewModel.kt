package com.example.startup.fragments.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.startup.R
import com.example.startup.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL
import java.util.*

class ProfileFragmentViewModel:ViewModel() {

    private val user = Firebase.auth.currentUser
    val uid = FirebaseAuth.getInstance().uid ?: ""
    val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
    var selectedPhotoUri:Uri? = null
    lateinit var  uriToChange:Uri
    private val url = user!!.photoUrl
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    @ObsoleteCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.P)

    fun getNewPhoto(
        requestCode: Int, resultCode: Int, data: Intent?,
        context: Context,
        photoView: CircleImageView
    ){
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            selectedPhotoUri = data.data!!
            val image = context.contentResolver?.let { ImageDecoder.createSource(it,
                selectedPhotoUri!!
            ) }

            val bitmap = image?.let { ImageDecoder.decodeBitmap(it) }
            photoView.setImageBitmap(bitmap)



        }
    }
    fun initName(binding: FragmentProfileBinding){
        uiScope.launch {
            binding.nameUserProfile.setText(user!!.displayName!!.toString().trim())
        }

    }

    @ObsoleteCoroutinesApi
    fun setPhoto(binding: FragmentProfileBinding){
        uiScope.launch {
//            Thread.sleep(500)
        }
//        runBlocking(newSingleThreadContext("dedicatedThread")) {
            if (url == null) {
//                binding.imageviewPhotoProfile.setImageResource(R.drawable.avatar)
                Picasso
                    .get()
                    .load(R.drawable.avatar)
                    .fit()
                    .centerInside()
                    .into(binding.imageviewPhotoProfile)
            } else {
//                val bm = BitmapFactory.decodeStream(URL(url.toString()).content as InputStream)
//                binding.imageviewPhotoProfile.setImageBitmap(bm)
                Picasso
                    .get()
                    .load(url)
                    .fit()
                    .centerInside()
                    .into(binding.imageviewPhotoProfile)
            }
//        }
    }

    fun saveChanges(binding:FragmentProfileBinding){
        val name = binding.nameUserProfile.text.toString()
        uriToChange = if(selectedPhotoUri != null){
            selectedPhotoUri as Uri
        } else{
            url!!
        }

//        newUrl = selectedPhotoUri!!

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        if(selectedPhotoUri != null){
            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d(ControlsProviderService.TAG, "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(ControlsProviderService.TAG, "File Location: $it")

                        saveUser(name, it.toString())
                    }
                }
                .addOnFailureListener {
                    Log.d(ControlsProviderService.TAG, "Failed to upload image to storage: ${it.message}")
                }
        }
        else {
            saveUser(name, "")
        }
    }

    fun saveUser(name:String,profileImageUrl: String ){

        val profileUpdates = userProfileChangeRequest {
            displayName = name
            photoUri = profileImageUrl.toUri()
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ControlsProviderService.TAG, "User profile updated.")
                }
            }
    }

    fun getMyUrl(): Uri? {
        return selectedPhotoUri
    }
}