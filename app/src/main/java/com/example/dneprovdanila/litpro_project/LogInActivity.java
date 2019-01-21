package com.example.dneprovdanila.litpro_project;


import android.content.Intent;
import android.support.annotation.NonNull;
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



import static com.example.dneprovdanila.litpro_project.R.id.button_registration;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText_login_1, editText_password_1;
    FirebaseAuth mAuth;
    //ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();

        editText_login_1 = (EditText) findViewById(R.id.editText_login_1);
        editText_password_1 = (EditText) findViewById(R.id.editText_password_1);
        //progressBar = (ProgressBar) findViewById(R.id.progressbar);

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
                    finish();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






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
