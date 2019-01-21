package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CHOOSE_IMAGE = 101;
    ImageView imageView;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView = (ImageView) findViewById(R.id.imageView);
        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.one).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.four).setOnClickListener(this);

        findViewById(R.id.settings).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LogInActivity.class));        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.one:
                finish();
                startActivity(new Intent(this, CourseActivity.class));
                break;

            case R.id.two:
                finish();
                startActivity(new Intent(this, TaskActivity.class));
                break;

            case R.id.three:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.four:
                finish();
                startActivity(new Intent(this, NontificationActivity.class));
                break;

            case R.id.settings:
                finish();
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }
}