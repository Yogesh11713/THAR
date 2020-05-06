package com.techknightsrtu.thar.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.techknightsrtu.thar.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignInActivity extends AppCompatActivity  {

    private static final String TAG = "AdminSignInActivity";

    private Context mContext = AdminSignInActivity.this;

    EditText teamId;
    Button getAccess;

    String memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        teamId = findViewById(R.id.et_team_id);
        getAccess = findViewById(R.id.bt_getaccess);

        //SET BACK BUTTON
        ImageView btBack = findViewById(R.id.iv_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("adminAccess");


        getAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                memberId = teamId.getText().toString().trim();


                Log.d(TAG, "onClick: MemberId"+memberId);

                if (TextUtils.isEmpty(memberId)) {

                    Toast.makeText(mContext, "Please Enter team Id", Toast.LENGTH_SHORT).show();
                    teamId.setError("Enter your team ID");

                } else if (!memberId.matches("[a-zA-Z0-9]*")) {

                    Toast.makeText(mContext, "ID should only contains alphabets and numbers", Toast.LENGTH_SHORT).show();
                    teamId.setError("Enter only numbers and alphabets");

                }
               else {

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(memberId)){

                                Toast.makeText(mContext, "Access Approved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(mContext,AdminDashboardActivity.class);
                                i.putExtra("team_id",teamId.getText().toString());
                                startActivity(i);

                            }else{

                                Toast.makeText(mContext, "Wrong Team Id", Toast.LENGTH_SHORT).show();
                                teamId.setError("ID not found");

                            }


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(mContext, "Something went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

       


    }


}
