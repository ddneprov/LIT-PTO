package com.example.dneprovdanila.litpro_project.staff_fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dneprovdanila.litpro_project.LogInActivity;
import com.example.dneprovdanila.litpro_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class STAFF_SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CHOOSE_IMAGE = 101;
    ImageView imageView;
    // ProgressBar progressBar;
    String profileImageUrl;
    Uri uriProfileImage;
    FirebaseAuth mAuth;


     DatabaseReference myRef;
     FirebaseUser currentUser;


     TextView S_staff_name;
     TextView S_staff_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_activity_settings);

        imageView = (ImageView) findViewById(R.id.profilePhoto);
        //progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        loadUserInformation();///

        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.buttonSave).setOnClickListener(this);
        findViewById(R.id.profilePhoto).setOnClickListener(this);












        S_staff_name = (TextView)findViewById(R.id.S_staff_name);
        S_staff_email = (TextView)findViewById(R.id.S_staff_email);


        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();


        String RegisteredUserID = currentUser.getUid(); // взяли id

        myRef.child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    S_staff_name.setText(dataSnapshot.child("name").getValue().toString());
                    S_staff_email.setText(dataSnapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }




    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {
            if (user.getPhotoUrl() != null)
            {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }
            if(user.getDisplayName() != null)
            {
                //editText.setText(user.getDisplayName());
            }
        }
    }

    private void saveUserInformation() {
        /*String displayName = editText.getText().toString();

        if (displayName.isEmpty()) {
            editText.setError("Введите имя");
            editText.requestFocus();
            return;
        }*/

        FirebaseUser user = mAuth.getCurrentUser();


        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    /*.setDisplayName(displayName)*/
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(STAFF_SettingsActivity.this, "Профиль обновлен", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
//            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressBar.setVisibility(View.GONE);
                            profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profileImageUrl = uri.toString();
                                    Toast.makeText(getApplicationContext(), "Image Upload Successful", Toast.LENGTH_SHORT).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    // ONE
    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }



    private void sendToStart() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(STAFF_SettingsActivity.this, LogInActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            sendToStart();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                sendToStart();
                break;
            case R.id.buttonSave:
                saveUserInformation();
                break;

            case R.id.profilePhoto:
                showImageChooser();
                break;

        }
    }
}