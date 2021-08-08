package com.example.farminghealthassistant2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TakingPicturesActivity extends AppCompatActivity {

    Button logoutBtn;// --Commented out by Inspection (08/08/2021 23:27):uploadBtn, seeAnalysisBtn;
    ImageButton cameraBtn;
    ImageView takenPhoto;
    private FirebaseAuth mAuth;
    private static final int pic_id = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takingpictures);

        cameraBtn = findViewById(R.id.imageButton);
        takenPhoto = findViewById(R.id.taken_pic_ImageView);
        logoutBtn = findViewById(R.id.logout_btn);
//        uploadBtn = findViewById(R.id.upload_btn);
//        seeAnalysisBtn = findViewById(R.id.see_analysis_btn);
        mAuth = FirebaseAuth.getInstance();

        cameraBtn.setOnClickListener(view -> {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //noinspection deprecation
            startActivityForResult(camera, pic_id);
        });

        logoutBtn.setOnClickListener(view -> logOutUser());
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //If user is not signed in take them back to login page
        if (currentUser == null){
            startActivity(new Intent(TakingPicturesActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id){
            assert data != null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            takenPhoto.setImageBitmap(photo);
        }
    }

    private void logOutUser() {
        mAuth.signOut();
        Toast.makeText(TakingPicturesActivity.this, "You have successfully signed out.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TakingPicturesActivity.this, LoginActivity.class));
    }
}