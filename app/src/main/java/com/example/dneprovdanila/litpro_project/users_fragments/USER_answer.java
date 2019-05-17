package com.example.dneprovdanila.litpro_project.users_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_MainActivity;
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_answer;
import com.google.firebase.database.DatabaseReference;


public class USER_answer extends AppCompatActivity implements  View.OnClickListener{

    private GestureDetectorCompat lSwipeDetector;

    RelativeLayout main_layout;


    TextView feedback;
    TextView mark;

    String composition_text;
    String feedback_text;
    String title_text;
    String composition_id;
    String mark_text;

    DatabaseReference dRef;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test3);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        lSwipeDetector = new GestureDetectorCompat(this, new MyGestureListener());
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);


        feedback = (TextView)findViewById(R.id.feedback);
        mark = (TextView)findViewById(R.id.mark);

        composition_text = getIntent().getExtras().getString("composition");
        feedback_text = getIntent().getExtras().getString("feedback");
        title_text = getIntent().getExtras().getString("title");
        composition_id = getIntent().getExtras().getString("composition_id");
        mark_text = getIntent().getExtras().getString("mark_text");



        feedback.setText(feedback_text);
        mark.setText(mark_text);




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




    @Override
    public void onBackPressed() {

        Intent intent = new Intent(USER_answer.this, MainActivity.class);
        startActivity(intent);
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


                Intent intent = new Intent(USER_answer.this, USER_composition.class);

                intent.putExtra("title", title_text);
                intent.putExtra("feedback", feedback_text);
                intent.putExtra("mark_text", mark_text);
                intent.putExtra("composition", composition_text);
                intent.putExtra("composition_id", composition_id);

                startActivity(intent);
            }
            return false;
        }
    }
}