package com.techknightsrtu.thar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private Context mContext = SignInActivity.this;

    private TextInputLayout etPhoneNumberLayout;
    private TextInputEditText etPhoneNumber;
    private TextView tvAuthenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        tvAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: BUTTON CLICKED" );
                if(!etPhoneNumber.getText().toString().isEmpty()
                        && etPhoneNumber.getText().toString().length() == 10){
                    etPhoneNumberLayout.setError("");

                    String mobileNumber = "+91" + etPhoneNumber.getText().toString().trim();
                    Toast.makeText(mContext, mobileNumber, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, VerifyMobileActivity.class);
                    intent.putExtra("mobile",mobileNumber);
                    startActivity(intent);
                    finish();

                }else {
                    etPhoneNumberLayout.setError("Please enter a valid Phone number");
                }
            }
        });


    }


    private void init() {
        etPhoneNumberLayout = findViewById(R.id.etPhoneNumberLayout);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        tvAuthenticate = findViewById(R.id.tvAuthenticate);
    }

}
