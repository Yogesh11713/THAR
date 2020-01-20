package com.example.tharapk

import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.net.ConnectivityManager
import android.net.Credentials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  HOLD THE SPLASH SCREEN FOR 4 SECONDS
        Handler().postDelayed({

            //  CHECK FOR INTERNET CONNECTION
            if(isOnline()){

                //  CHECK FOR USER AUTHENTICATION
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {

                    // USER ALREADY SIGNED-IN : REDIRECTING USER TO HOME ACTIVITY
                    startActivity(Intent(this,HomeActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TASK))
                    finish()

                }else{
                    // USER NOT SIGNED-IN : REDIRECTING USER TO SIGNEXP ACTIVITY
                    startActivity(Intent(this,SignExp::class.java))
                }

            }else{
                // NO INTERNET CONNECTION
                startActivity(Intent(this,NoConnection::class.java))
            }

            finish()
        },4000
        )}


    //  CHECK WHETHER USER IS CONNECTED TO INTERNET OR NOT
    fun isOnline():Boolean{

        val connectivityManager= getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo

        return networkInfo!=null && networkInfo.isConnected
    }
}
