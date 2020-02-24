package com.example.tharapk.ui.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tharapk.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import id.zelory.compressor.Compressor;

import static io.opencensus.tags.TagKey.MAX_LENGTH;

@SuppressWarnings("VisibleForTests")
public class PostActivity extends AppCompatActivity {

    private Context mContext = PostActivity.this;
    private static final String TAG = "PostActivity";

    private static final int GALLERY_PICK = 1;

    private Button mPostButton;
    private EditText mDescription;
    private String mCurrentUser, mTeamId;
    private ProgressDialog mProgressDialog;

    // Firebase Variables
    private FirebaseAuth mAuth;
    private StorageReference mUserPostStorage;
    private DatabaseReference mUserPostDatabase;
    private ImageView postImage;
    private TextView imageText;
    private byte[] thumb_byte;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mTeamId = getIntent().getStringExtra("team_id");

        Log.d(TAG, "onCreate: TEAM-id:" + mTeamId);

        //SET BACK BUTTON
        ImageView btBack = findViewById(R.id.iv_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser().getUid();
        mUserPostStorage = FirebaseStorage.getInstance().getReference();
        mUserPostDatabase = FirebaseDatabase.getInstance().getReference();
        mUserPostDatabase.keepSynced(true);
        setupWidgets();

    }

    // SETUP ALL THE WIDGETS
    private void setupWidgets() {
        mDescription = findViewById(R.id.post_description);
        postImage = findViewById(R.id.post_image);
        imageText = findViewById(R.id.post_image_gallery);
        imageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_PICK);

            }
        });

        mPostButton = findViewById(R.id.postBtn);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

    }

    // UPLOADING POST
    private void startPosting() {

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Posting Event Update");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        final String description = mDescription.getText().toString().trim();
        String randomString = random();
        Log.d(TAG, "startPosting: RANDOM :: " + randomString);
        if (randomString.equals("")) {
            for (; randomString.equals(""); ) {
                Log.d(TAG, "startPosting: " + randomString);
                randomString = random();
            }
        }

        final StorageReference filePath;
        filePath = mUserPostStorage.child("event_post_images").child(mCurrentUser).child(randomString);

        if (!TextUtils.isEmpty(description) && mImageUri != null) {
            // Image With Description going to be posted
            // Image is going to be posted

//            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//
//                    Uri downloadUri =uri;
//
//                    //Uplaod post to the database
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(mContext, " ERROR!\n Failed to upload post", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "onFailure: Error message" + e.getMessage());
//                    mProgressDialog.dismiss();
//                }
//            });
            UploadTask uploadTask = filePath.putBytes(thumb_byte);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();

                    }else{
                        filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String downloadUri = task.getResult().toString();
                                DatabaseReference newPost = mUserPostDatabase.child("event_post").child("THAR2k20").push();
                                newPost.child("image_uri").setValue(downloadUri.toString());
                                newPost.child("desc").setValue(description);
                                newPost.child("timestamp").setValue(ServerValue.TIMESTAMP);
                                newPost.child("uid").setValue(mCurrentUser);
                                newPost.child("team_id").setValue(mTeamId);

                                mProgressDialog.dismiss();

                                Toast.makeText(mContext, "Event Update Posted ", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        });
                    }

                }
            });


        } else if (TextUtils.isEmpty(description) && mImageUri != null) {

            // Image is going to be posted
            UploadTask uploadTask = filePath.putBytes(thumb_byte);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(mContext, " ERROR!\n Failed to upload post", Toast.LENGTH_SHORT).show();
                    }else{
                        filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String downloadUri = task.getResult().toString();
                                //Uplaod post to the database
                                DatabaseReference newPost = mUserPostDatabase.child("event_post").child("THAR2k20").push();
                                newPost.child("image_uri").setValue(downloadUri);
                                newPost.child("timestamp").setValue(ServerValue.TIMESTAMP);
                                newPost.child("uid").setValue(mCurrentUser);
                                newPost.child("team_id").setValue(mTeamId);

                                mProgressDialog.dismiss();
                                Toast.makeText(mContext, "Event Update Posted", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        });
                    }

                }
            });

        } else if (!TextUtils.isEmpty(description) && mImageUri == null) {
            // Description  is going to be posted

            //Uplaod post to the database
            DatabaseReference newPost = mUserPostDatabase.child("event_post").child("THAR2k20").push();
            newPost.child("desc").setValue(description);
            newPost.child("image_uri").setValue("none");
            newPost.child("timestamp").setValue(ServerValue.TIMESTAMP);
            newPost.child("uid").setValue(mCurrentUser);
            newPost.child("team_id").setValue(mTeamId);

            mProgressDialog.dismiss();
            Toast.makeText(mContext, "Event Update Posted", Toast.LENGTH_SHORT).show();
            finish();

        } else if (mImageUri == null && TextUtils.isEmpty(description)) {
            //Null Post
            mProgressDialog.dismiss();
            Toast.makeText(mContext, "Empty Post", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            CropImage.activity(mImageUri)
                    .setMinCropWindowSize(300, 300)
                    .start(PostActivity.this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File thumb_filePath = new File(resultUri.getPath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {

                    Bitmap thumb_bitmap = new Compressor(this)
                            .setQuality(70)
                            .compressToBitmap(thumb_filePath);
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                } catch (IOException e) {
                    Log.d(TAG, "onActivityResult:  Exception is Going on ");
                    e.printStackTrace();
                }

                thumb_byte = baos.toByteArray();
                postImage.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d(TAG, "onActivityResult: ERROR : " + error);
            }
        }

    }

    public static String random() {
        String randomString;
        do {
            Random generator = new Random();
            StringBuilder randomStringBuilder = new StringBuilder();
            int randomLength = generator.nextInt(MAX_LENGTH);
            char tempChar;
            for (int i = 0; i < randomLength; i++) {
                tempChar = (char) (generator.nextInt(65) + 30);
                randomStringBuilder.append(tempChar);
            }
            randomString = randomStringBuilder.toString();

        } while (randomString.equals(""));

        return randomString;
    }

}
