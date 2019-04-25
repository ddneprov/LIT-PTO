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

import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.TaskFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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



    public static Integer COUNTER = 0;
    public String answer;
    public  String staff = "STAFF";
    public  String user = "USER";
    private void is_it_USER(final String email)
    {
        //myRef.child("Users").addValueEventListener(new ValueEventListener() {
        myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    String data_mail = dSnapshot.getValue(User.class).getEmail();
                    if (data_mail.toString().equals(email.toString())) {
                        answer = "USER";
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    private void is_it_STAFF(final String email)
    {
        COUNTER += 1;
        //myRef.child("Staff").addValueEventListener(new ValueEventListener() {
        myRef.child("Staff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    String data_mail = dSnapshot.getValue(User.class).getEmail();
                    if(data_mail.toString().equals(email.toString()))
                    {
                        answer = "STAFF";
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null)
        {
            //Globals.status = "";
            final String email = mAuth.getCurrentUser().getEmail();
            is_it_STAFF(email);
            is_it_USER(email);

            if(user.equals(answer))
            {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }

            else if(staff.equals(answer))
            {
                finish();
                startActivity(new Intent(this, STAFF_MainActivity.class));
            }
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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    is_it_STAFF(email.toString());
                    //String line = answer;
                    if ("STAFF".equals(answer))
                    {
                        finish();
                        Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                        startActivity(intent);
                    }

                    //line = answer;
                    if ("USER".equals(answer))
                    {
                        finish();
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
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