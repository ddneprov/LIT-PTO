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

import java.util.regex.Pattern;

import static com.example.dneprovdanila.litpro_project.R.id.button_registration;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText_login_1, editText_password_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        findViewById(R.id.button_registration).setOnClickListener(this);

        editText_login_1 = (EditText) findViewById(R.id.editText_login_1);
        editText_password_1 = (EditText) findViewById(R.id.editText_password_1);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.button_login:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case button_registration:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
    //check
}
