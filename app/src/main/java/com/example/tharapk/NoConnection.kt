package com.example.tharapk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//IF INTERNET IS NOT CONNECTED THEN THIS PAGE WILL APPEAR

class NoConnection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection)
    }
}
