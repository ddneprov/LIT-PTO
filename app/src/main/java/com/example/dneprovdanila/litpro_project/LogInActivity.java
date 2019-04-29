package com.example.dneprovdanila.litpro_project;



import android.content.Intent;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.TaskFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_login_1, editText_password_1;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        editText_login_1 = (EditText) findViewById(R.id.editText_login_1);
        editText_password_1 = (EditText) findViewById(R.id.editText_password_1);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        findViewById(R.id.button_registration).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null)
        {

            String RegisteredUserID = mAuth.getCurrentUser().getUid(); // взяли id
            progressBar.setVisibility(View.GONE);

            myRef.child("Users").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener()  {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        finish();
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            myRef.child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        finish();
                        Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void userLogin() {
        final String email = editText_login_1.getText().toString().trim();
        String password = editText_password_1.getText().toString().trim();

        if (email.isEmpty()) {
            editText_login_1.setError("Email is required");
            editText_login_1.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editText_password_1.setError("Password is required");
            editText_password_1.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    //is_it_STAFF(email.toString());
                    //is_it_USER(email.toString());
                    //String line = answer;



                        String RegisteredUserID = mAuth.getCurrentUser().getUid(); // взяли id
                        myRef.child("Users").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    finish();
                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                //return;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    myRef.child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                finish();
                                Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                userLogin();
                break;
            case R.id.button_registration:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}