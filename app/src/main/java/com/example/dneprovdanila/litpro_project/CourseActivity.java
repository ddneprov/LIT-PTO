package com.example.dneprovdanila.litpro_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.four).setOnClickListener(this);
        findViewById(R.id.five).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.two:
                finish();
                startActivity(new Intent(this, TaskActivity.class));
                break;

            case R.id.three:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.four:
                finish();
                startActivity(new Intent(this, NontificationActivity.class));
                break;

            case R.id.five:
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }
}
