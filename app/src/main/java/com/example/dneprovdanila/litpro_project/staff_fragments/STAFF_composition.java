package com.example.dneprovdanila.litpro_project.staff_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dneprovdanila.litpro_project.LogInActivity;
import com.example.dneprovdanila.litpro_project.R;


public class STAFF_composition extends AppCompatActivity implements  View.OnClickListener{

    private GestureDetectorCompat lSwipeDetector;

    RelativeLayout main_layout;

    TextView composition;
    TextView author;
    TextView title;

    String composition_text;
    String author_text;
    String title_text;
    String composition_id;
    String author_id;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        lSwipeDetector = new GestureDetectorCompat(this, new MyGestureListener());
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);



        TextView composition = (TextView)findViewById(R.id.composition);
        TextView author = (TextView)findViewById(R.id.author);
        TextView title = (TextView)findViewById(R.id.title);

        composition_text = getIntent().getExtras().getString("composition");
        author_text = getIntent().getExtras().getString("author");
        title_text = getIntent().getExtras().getString("title");
        composition_id = getIntent().getExtras().getString("composition_id");
        author_id = getIntent().getExtras().getString("author_id");

        author.setText(author_text);
        composition.setText(composition_text);
        title.setText(title_text);





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

        Intent intent = new Intent(STAFF_composition.this, STAFF_MainActivity.class);
        startActivity(intent);
    }





    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_DISTANCE)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {

                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);


                Intent intent = new Intent(STAFF_composition.this, STAFF_answer.class);

                intent.putExtra("title", title_text);
                intent.putExtra("author", author_text);
                intent.putExtra("composition", composition_text);
                intent.putExtra("composition_id", composition_id);
                intent.putExtra("author_id", author_id);



                startActivity(intent);
            }
            return false;
        }
    }
}