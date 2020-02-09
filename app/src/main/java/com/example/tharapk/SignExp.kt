package com.example.tharapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.tharapk.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_exp.*


//COMPLETELY SIGN IN PAGE
class SignExp : AppCompatActivity() {

    //  ACTIVITY CONSTANTS
    val TAG = "SignExpActivity"
    val RC_SIGN_IN: Int = 1

    //GOOGLE SIGN-IN API
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    //  ACTIVITY FIELDS
    lateinit var mLoading:RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_exp)

        // INITIALIZE LOADING FIELD
        mLoading = findViewById(R.id.logInLoading)

        //  CONFIGURE GOOGLE SIGN-IN
        configureGoogleSignIn()

    }


    private fun configureGoogleSignIn() {

        //  ENABLE GOOGLE SIGN-IN API
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

        //  SETUP GOOGLE SIGN-IN BUTTON
        google_button.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            // MAKE LOADING VISIBILE
            mLoading.visibility = View.VISIBLE

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {

                // MAKE LOADING GONE
                mLoading.visibility = View.GONE
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()

            }

        }

    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        //  SIGNING INTO THE ACCOUNT
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                val intent= Intent(this@SignExp, HomeActivity::class.java)
                startActivity(intent)
                finish()
                // MAKE LOADING GONE
                mLoading.visibility = View.GONE

            } else {
                // MAKE LOADING GONE
                mLoading.visibility = View.GONE
                Log.w(TAG, "Google sign in failed" + it.result)
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()

            }
        }

    }


}
