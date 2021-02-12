package com.example.startup

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL


class SecondActivity:AppCompatActivity() {

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Thread.sleep(500)
        val user = Firebase.auth.currentUser
        setSupportActionBar(toolBar)

        val navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        val navHeader =  nav_view.getHeaderView(0)

        NavigationUI.setupWithNavController(nav_view,navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navHeader.setOnClickListener {
            navController.navigate(R.id.profileFragment)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        val url = user!!.photoUrl
        val name = user.displayName
        if(user.photoUrl == null){
            doNetwork(navHeader!!,name!!)
            Picasso
                .get()
                .load(R.drawable.avatar)
                .fit()
                .centerInside()
                .into(navHeader.header_photo)
        }
        else{
            doNetwork(navHeader,name!!)
            Picasso
                .get()
                .load(url)
                .fit()
                .centerInside()
                .into(navHeader.header_photo)
        }
        var manager = supportFragmentManager

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out_menu_item -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                this.finish()
                this.startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun doNetwork(view:View, name:String) {
        GlobalScope.launch(Dispatchers.Main) {
           view.header_name.text = name
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController,drawerLayout)

    }
}