 package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

 public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

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
                 registerUser();
             }
         });
     }


     @Override
     protected void onStart() {
         super.onStart();
         /*if (mAuth.getCurrentUser() != null) {
             finish();
             startActivity(new Intent(this, START_activity.class));
         }*/
     }

     @Override
     public void onClick(View v) {
         switch (v.getId()) {
             case R.id.button_back:
                 finish();
                 startActivity(new Intent(this, LogInActivity.class));
                 break;
             case R.id.button_signUp:
                 registerUser();
                 break;
         }
     }



 ////////////////////// ////////////////////// ////////////////////// ////////////////////// ////////////////////// ////////////////////// ////////////////////// //////////////////////



    private void registerUser(){//String email, final String password, String display_name) {

        final String display_name = mDisplayName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        if (email.isEmpty()) {
            mEmail.setError("Введите почту");
            mEmail.requestFocus();
            return;
        }

        if (display_name.isEmpty()) {
            mDisplayName.setError("Нужно ввести имя");
            mDisplayName.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Неверная почта");
            mEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mPassword.setError("Придумайте пароль");
            mPassword.requestFocus();
            return;
        }

        if (password.length() < 7) {
            mPassword.setError("Пароль должен содержать больше 6 симолов");
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

                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                            //User user = new User(email, display_name, password, 0, false, id, "");
                            ArrayList<String> pupils = new ArrayList<String>();
                            Staff staff = new Staff(display_name, email, password, pupils, 0, "", id);

                           /* FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Готово!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });*/

                            FirebaseDatabase.getInstance().getReference("Staff").child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid())
                                    .setValue(staff).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Готово!", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(SignUpActivity.this, STAFF_MainActivity.class));
                                        finish();

                                    }
                                }
                            });

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