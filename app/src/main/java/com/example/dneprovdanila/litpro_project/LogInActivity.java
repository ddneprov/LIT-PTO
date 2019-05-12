package com.example.dneprovdanila.litpro_project;



import android.content.Intent;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.MainActivity;
import com.example.dneprovdanila.litpro_project.users_fragments.TaskFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    Button a;
    FirebaseUser user;
    ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_auth);
        //overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

        ///overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);


        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        editText_login_1 = (EditText) findViewById(R.id.editText_login_1);
        editText_password_1 = (EditText) findViewById(R.id.editText_password_1);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        a = (Button)findViewById(R.id.button_registration);

        user = mAuth.getCurrentUser();
        findViewById(R.id.button_registration).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
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



        Toast.makeText(getApplicationContext(), "рас", Toast.LENGTH_SHORT).show();


        progressBar.setVisibility(View.VISIBLE);
            Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        final String RegisteredUserID = FirebaseAuth.getInstance().getCurrentUser().getUid(); // взяли id

//addValueEventListener
                        ///
                        Toast.makeText(getApplicationContext(), "пробуем зайти в юзера", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    Toast.makeText(getApplicationContext(), "пробуем юзера и он существует", Toast.LENGTH_SHORT).show();

                                    finish();
                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "dataSnapshot юзера не существует", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                throw databaseError.toException(); // Don't ignore errors

                            }
                        });
                        ///




                        /*DatabaseReference a = FirebaseDatabase.getInstance().getReference().child("Users");
                        DatabaseReference b = FirebaseDatabase.getInstance().getReference().child("Staff");


                        ValueEventListener listner = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    Toast.makeText(getApplicationContext(), "dataSnapshot существует !!!", Toast.LENGTH_SHORT).show();


                                    if (dataSnapshot.getRef() == FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID))
                                    {
                                        finish();
                                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else if (dataSnapshot.getRef() == FirebaseDatabase.getInstance().getReference().child("Staff").child(RegisteredUserID))
                                    {
                                        finish();
                                        Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "dataSnapshot  не существует", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        a.addValueEventListener(listner);
                        b.addValueEventListener(listner);*/


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                   /* FirebaseAuth.getInstance().getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                                            boolean isEmailVerified = user.isEmailVerified();
                                            if(isEmailVerified)
                                            {
                                                finish();
                                                Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(), "Подтвердите письмо на почте", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///
                        Toast.makeText(getApplicationContext(), "ELSE", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "пробуем зайти в стаф", Toast.LENGTH_SHORT).show();

                        FirebaseDatabase.getInstance().getReference().child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    Toast.makeText(getApplicationContext(), "пробуем стаф и он существует", Toast.LENGTH_SHORT).show();


                                    finish();
                                    Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "dataSnapshot стафа не существует", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                throw databaseError.toException(); // Don't ignore errors
                            }
                        });

                    }

///


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*FirebaseAuth.getInstance().getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                                            boolean isEmailVerified = user.isEmailVerified();
                                            if(isEmailVerified)
                                            {
                                                finish();
                                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(), "Подтвердите письмо на почте", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                    else
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Неверная почта или пароль", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_login:
                userLogin();
                break;

            case R.id.button_registration:
                finish();
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}






 /*Toast.makeText(getApplicationContext(), "ПЕРЕХОД", Toast.LENGTH_SHORT).show();

                        try {
                            Toast.makeText(getApplicationContext(), "пробуем зайти в стаф", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference().child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        Toast.makeText(getApplicationContext(), "пробуем стаф и он существует", Toast.LENGTH_SHORT).show();


                                        finish();
                                        Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                                        startActivity(intent);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                   *//* FirebaseAuth.getInstance().getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                                            boolean isEmailVerified = user.isEmailVerified();
                                            if(isEmailVerified)
                                            {
                                                finish();
                                                Intent intent = new Intent(LogInActivity.this, STAFF_MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(), "Подтвердите письмо на почте", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });*//*
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });*/