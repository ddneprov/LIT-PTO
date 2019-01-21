 package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.os.Binder;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

 public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

     //EditText editText_email_2, editText_password_2;

     /*private TextInputLayout mDisplayName;
     private TextInputLayout mEmail;
     private TextInputLayout mPassword;*/

     private EditText mDisplayName;
     private EditText mEmail;
     private EditText mPassword;


     private Button mSignUp;
     private Button mBack;


     private FirebaseAuth mAuth;
     // ProgressBar progressBar;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_sign_up);

         // FireBase Auth
         mAuth = FirebaseAuth.getInstance();


         mDisplayName = (EditText) findViewById(R.id.reg_display_name);
         mEmail = (EditText) findViewById(R.id.reg_email);
         mPassword = (EditText) findViewById(R.id.reg_password);
         mSignUp = (Button) findViewById(R.id.button_signUp);
         mBack = (Button) findViewById(R.id.button_back);


         mSignUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String display_name = mDisplayName.getText().toString().trim();
                 String email = mEmail.getText().toString().trim();
                 String password = mPassword.getText().toString().trim();
                 registerUser(email, password, display_name);
             }
         });
     }


     @Override
     protected void onStart() {
         super.onStart();
         if (mAuth.getCurrentUser() != null) {
             finish();
             startActivity(new Intent(this, MainActivity.class));
         }
     }

     @Override
     public void onClick(View v) {
         switch (v.getId()) {
             case R.id.button_back:
                 finish();// точно?
                 startActivity(new Intent(this, LogInActivity.class));
                 break;
             /*case R.id.button_signUp:
                 String display_name = mDisplayName.getEditText().toString();
                 String email = mDisplayName.getEditText().toString();
                 String password = mDisplayName.getEditText().toString();
                 registerUser(display_name, email, password);
                 break;*/
         }
     }



















 ////////////////////// ////////////////////// ////////////////////// ////////////////////// ////////////////////// ////////////////////// ////////////////////// //////////////////////



    private void registerUser(String email, String password, String display_name)
    {

        if (email.isEmpty())
        {
            mEmail.setError("Введите почту");
            mEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("Неверная почта");
            mEmail.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            mPassword.setError("Введите пароль");
            mPassword.requestFocus();
            return;
        }

        if (password.length() < 7)
        {
            mPassword.setError("Пароль должен быть длиннее 6 символов");
            mPassword.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        //progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
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
 }
