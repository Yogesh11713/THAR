package com.example.tharapk

import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // dp.startAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in))
//        starts the zoom in animation
        Handler().postDelayed({
          //  dp.visibility= View.GONE
            if(isOnline()){
                startActivity(Intent(this,SignExp::class.java))}
//            checks the status of connection
            else{
                startActivity(Intent(this,NoConnection::class.java))
            }
            finish()
        },4000
        )}



    fun isOnline():Boolean{
    val connectivityManager= getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo=connectivityManager.activeNetworkInfo
    return networkInfo!=null && networkInfo.isConnected
}
}
