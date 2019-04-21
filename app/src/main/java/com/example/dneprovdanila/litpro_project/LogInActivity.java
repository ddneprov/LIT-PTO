package com.example.dneprovdanila.litpro_project;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.regex.Pattern;

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


import static com.example.dneprovdanila.litpro_project.R.id.button_registration;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{


    EditText editText_login_1, editText_password_1;
    FirebaseAuth mAuth;
    DatabaseReference jLoginDatabase;
    //ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();

        editText_login_1 = (EditText) findViewById(R.id.editText_login_1);
        editText_password_1 = (EditText) findViewById(R.id.editText_password_1);
        //progressBar = (ProgressBar) кfindViewById(R.id.progressbar);

        findViewById(R.id.button_registration).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);

    }



    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        /*else
        {
            Intent sign_up = new Intent(this, SignUpActivity.class);
            startActivity(sign_up);
            finish();
        }*/
    }



    private void userLogin() {
        String email = editText_login_1.getText().toString().trim();
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

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    //finish();


                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String RegisteredUserID = currentUser.getUid();

                    jLoginDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);
                    jLoginDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String userType = dataSnapshot.child("userType").getValue().toString();

                            if(userType.equals("Resident")){
                                Intent intentResident = new Intent(LogInActivity.this, MainActivity.class);
                                intentResident.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intentResident);
                                finish();
                            }else if(userType.equals("Guard")){
                                Intent intentMain = new Intent(LogInActivity.this, MainActivity.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intentMain);
                                finish();
                            }else if(userType.equals("Police")){
                                Intent intentMain = new Intent(LogInActivity.this, MainActivity.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intentMain);
                                finish();
                            }else{
                                Toast.makeText(LogInActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                        //finish();
                        ///ВЕРНИ
                        /*Intent intent = new Intent(LogInActivity.this, MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


   /* private void loginUser() {

        final String userLoginEmail = editText_login_1.getText().toString().trim();
        final String userLoginPassword = editText_password_1.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(userLoginEmail, userLoginPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = currentUser.getUid();

                            jLoginDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);

                            jLoginDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String userType = dataSnapshot.child("userType").getValue().toString();
                                    if(userType.equals("Resident")){
                                        Intent intentResident = new Intent(LoginActivity.this, ResidentActivity.class);
                                        intentResident.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intentResident);
                                        finish();
                                    }else if(userType.equals("Guard")){
                                        Intent intentMain = new Intent(LoginActivity.this, SecurityGuardActivity.class);
                                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intentMain);
                                        finish();
                                    }else if(userType.equals("Police")){
                                        Intent intentMain = new Intent(LoginActivity.this, PoliceActivity.class);
                                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intentMain);
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });*/


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_login:
                userLogin();
                break;

            case button_registration:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}