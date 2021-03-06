package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class START_activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {


            String RegisteredUserID = mAuth.getCurrentUser().getUid(); // взяли id


            FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        finish();
                        Intent intent = new Intent(START_activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    throw databaseError.toException(); // Don't ignore errors
                }
            });


            FirebaseDatabase.getInstance().getReference().child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        finish();
                        Intent intent = new Intent(START_activity.this, STAFF_MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    throw databaseError.toException(); // Don't ignore errors
                }
            });

            //TODO: придумай как избавиться
           /* finish();
            startActivity(new Intent(START_activity.this, LogInActivity.class));*/



        }
        else
        {
            finish();
            startActivity(new Intent(START_activity.this, LogInActivity.class));
        }
        //finish();
        //startActivity(new Intent(START_activity.this, LogInActivity.class));
    }
}
