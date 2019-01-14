 package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

 public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_email_2, editText_password_2;
    private FirebaseAuth mAuth;
    // ProgressBar progressBar;




     @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.button_signUp).setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);

         //progressBar = (ProgressBar) findViewById(R.id.progressbar);

         editText_email_2 = (EditText) findViewById(R.id.editText_email_2);
         editText_password_2 = (EditText) findViewById(R.id.editText_password_2);

        mAuth = FirebaseAuth.getInstance();

    }

     @Override
     protected void onStart() {
         super.onStart();

         if (mAuth.getCurrentUser() != null) {
             finish();
             startActivity(new Intent(this, MainActivity.class));
         }
     }

    private void registerUser()
    {
        String login = editText_email_2.getText().toString().trim();
        String password = editText_password_2.getText().toString().trim();

        if (login.isEmpty())
        {
            editText_email_2.setError("Введите почту");
            editText_email_2.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches())
        {
            editText_email_2.setError("Неверная почта");
            editText_email_2.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            editText_password_2.setError("Введите пароль");
            editText_password_2.requestFocus();
            return;
        }

        if (password.length() < 7)
        {
            editText_password_2.setError("Пароль должен быть длиннее 6 символов");
            editText_password_2.requestFocus();
            return;
        }

        //
        //progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        //progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) // already registed
                                Toast.makeText(getApplicationContext(), "Email уже зарегестрирован", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();                        }
                    }
                });
    }




     @Override
     public void onClick(View v) {
         switch (v.getId())
         {
             case R.id.button_signUp:
                 registerUser();
                 break;

             case R.id.button_back:
                 finish();// точно?
                 startActivity(new Intent(this, LogInActivity.class));
                 break;


         }
     }
 }
