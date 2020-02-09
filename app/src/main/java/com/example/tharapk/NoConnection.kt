package com.example.tharapk

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.tharapk.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_no_connection.*
import kotlinx.android.synthetic.main.activity_sign_exp.*

//IF INTERNET IS NOT CONNECTED THEN THIS PAGE WILL APPEAR

class NoConnection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection)

        //  SETUP RETRY BUTTON
        bt_retry.setOnClickListener {

            //  CHECK FOR INTERNET CONNECTION
            if(isOnline()){

                //  CHECK FOR USER AUTHENTICATION
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {

                    // USER ALREADY SIGNED-IN : REDIRECTING USER TO HOME ACTIVITY
                    startActivity(Intent(this, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finish()

                }else{
                    // USER NOT SIGNED-IN : REDIRECTING USER TO SIGNEXP ACTIVITY
                    startActivity(Intent(this,SignExp::class.java))
                    finish()
                }

            }else{
                // NO INTERNET CONNECTION
                Toast.makeText(applicationContext,"No Connection, Retry later",Toast.LENGTH_SHORT).show()
            }

        }


    }

    //  CHECK WHETHER USER IS CONNECTED TO INTERNET OR NOT
    fun isOnline():Boolean{

        val connectivityManager= getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo

        return networkInfo!=null && networkInfo.isConnected
    }
}


