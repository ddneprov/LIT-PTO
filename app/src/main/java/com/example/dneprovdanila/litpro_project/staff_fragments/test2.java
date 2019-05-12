package com.example.dneprovdanila.litpro_project.staff_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.users_fragments.MainActivity;


public class test2 extends AppCompatActivity implements  View.OnClickListener{

    private GestureDetectorCompat lSwipeDetector;

    RelativeLayout main_layout;
    String composition_text;
    String author_text;
    String title_text;


    Button sAnswer;
    TextView words_count;
    EditText mark;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        composition_text = getIntent().getExtras().getString("composition");
        author_text = getIntent().getExtras().getString("author");
        title_text = getIntent().getExtras().getString("title");
        lSwipeDetector = new GestureDetectorCompat(this, new MyGestureListener());
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);

        ImageButton sAnswer = (ImageButton)findViewById(R.id.send_to_pupil);
        TextView words_count = (TextView)findViewById(R.id.mark);


        sAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        main_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return lSwipeDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e2, MotionEvent e1, float velocityX, float velocityY){
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_DISTANCE)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {

                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                Intent intent = new Intent(test2.this, test.class);

                intent.putExtra("title", composition_text);
                intent.putExtra("author", author_text);
                intent.putExtra("composition", title_text);
                startActivity(intent);
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(test2.this, STAFF_MainActivity.class);
        startActivity(intent);
    }
}