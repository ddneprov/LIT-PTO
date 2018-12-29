 package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

 public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_email_2, editText_password_2;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.button_signUp).setOnClickListener(this);


        editText_email_2 = (EditText) findViewById(R.id.editText_email_2);
        editText_password_2 = (EditText) findViewById(R.id.editText_password_2);
        mAuth = FirebaseAuth.getInstance();

    }


    private void registerUser()
    {
        String login = editText_email_2.getText().toString().trim();
        String password = editText_password_2.getText().toString().trim();

        if (login.isEmpty())
        {
            editText_email_2.setError("Empty login");
            editText_email_2.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches())
        {
            editText_email_2.setError("Nonvalid login");
            editText_email_2.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            editText_password_2.setError("Password login");
            editText_password_2.requestFocus();
            return;
        }

        if (password.length() < 7)
        {
            editText_password_2.setError("Pls password lenght > 6");
            editText_password_2.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Успешная регистрация", Toast.LENGTH_SHORT).show();

                        }
                    }
                });









    }



     @Override
     public void onClick(View v) {
         switch (v.getId())
         {
             case R.id.button_signUp:
                 registerUser();
                 startActivity(new Intent(this, MainActivity.class));
                 break;
             case R.id.button_back:
                 startActivity(new Intent(this, LogInActivity.class));
                 break;


         }
     }
 }
