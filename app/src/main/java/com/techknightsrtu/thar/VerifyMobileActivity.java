package com.techknightsrtu.thar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techknightsrtu.thar.ui.home.HomeActivity;

import java.util.concurrent.TimeUnit;

public class VerifyMobileActivity extends AppCompatActivity {

    private static final String TAG = "VerifyMobileActivity";

    private Context mContext = VerifyMobileActivity.this;

    private TextView tvOtpInformation;
    private Button btGetIn;
    private TextInputEditText etOTP;
    private TextInputLayout etOTPLayout;
    private RelativeLayout rlProgress;

    private FirebaseAuth mAuth;

    private String mobileNumber, verificationId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);

        init();
        setMobileNumber();
        setOtpInformation();
        sendVerificationCode(mobileNumber);

        btGetIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = etOTP.getText().toString().trim();
                if(!code.isEmpty() && code.length()==6){
                    verifyCode(code);
                }else{
                    etOTPLayout.setError("Please enter code ...");
                    etOTPLayout.requestFocus();
                }
            }
        });


    }


    private void verifyCode(String code){
        rlProgress.setVisibility(View.VISIBLE);

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            long creationTimestamp = user.getMetadata().getCreationTimestamp();
                            long lastSignInTimestamp = user.getMetadata().getLastSignInTimestamp();

                            if (creationTimestamp == lastSignInTimestamp) {
                                //do create new user
                                //Inflate the dialog with custom view
                                final View mDialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_thar_id_dialog, null);

                                //AlertDialogBuilder
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext)
                                        .setView(mDialogView)
                                        .setCancelable(false);
                                //show dialog
                                final AlertDialog  mAlertDialog = mBuilder.show();

                                mDialogView.findViewById(R.id.dialogSubmitBtn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mAlertDialog.dismiss();

                                        //get text from EditTexts of custom layout
                                        EditText editText = mDialogView.findViewById(R.id.dialogTharIdEt);
                                        String thar = editText.getText().toString();

                                        if(!thar.equals("")){
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference ref = database.getReference().child("Users").child(mAuth.getUid());
                                            ref.child("thar_id").setValue(thar);
                                            ref.child("phone_number").setValue(mobileNumber);
                                            Intent intent= new Intent(mContext, HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();

                                        }else{
                                            editText.setError("Please enter the thar id");
                                        }

                                    }
                                });



                            } else {
                                //user is exists, just do login
                                Intent intent= new Intent(mContext, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }

                    }
                });
    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                etOTP.setText(code);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyMobileActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }
    };

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }


    private void setOtpInformation() {
        tvOtpInformation.setText("We've sent an SMS with an activation code to your phone "+mobileNumber);
    }


    private void setMobileNumber() {
        mobileNumber = getIntent().getStringExtra("mobile");
    }

    private void init() {

        tvOtpInformation = findViewById(R.id.tvOtpInformation);
        btGetIn = findViewById(R.id.btGetIn);
        etOTP = findViewById(R.id.etOTP);
        etOTPLayout = findViewById(R.id.etOTPLayout);
        rlProgress = findViewById(R.id.rlProgress);
        mAuth = FirebaseAuth.getInstance();

    }

}
