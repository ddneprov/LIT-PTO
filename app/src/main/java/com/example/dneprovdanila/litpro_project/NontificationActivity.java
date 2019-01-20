package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NontificationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nontification);

        findViewById(R.id.five).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.one).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.one:
                finish();
                startActivity(new Intent(this, CourseActivity.class));
                break;

            case R.id.two:
                finish();
                startActivity(new Intent(this, TaskActivity.class));
                break;

            case R.id.three:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.five:
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }
}
