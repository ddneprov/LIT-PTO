package com.example.dneprovdanila.litpro_project.staff_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.users_fragments.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class STAFF_answer extends AppCompatActivity implements  View.OnClickListener{

    private GestureDetectorCompat lSwipeDetector;

    RelativeLayout main_layout;
    String composition_text;
    String author_text;
    String title_text;
    String composition_id;
    String author_id;


    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    ImageButton sAnswer;
    EditText answer;
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
        composition_id = getIntent().getExtras().getString("composition_id");
        author_id = getIntent().getExtras().getString("author_id");


        lSwipeDetector = new GestureDetectorCompat(this, new MyGestureListener());
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);

        mark = (EditText)findViewById(R.id.mark);
        answer = (EditText)findViewById(R.id.answer);
        sAnswer = (ImageButton) findViewById(R.id.send_to_pupil);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();



        sAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentUser = mAuth.getCurrentUser();
                final String RegisteredUserID = currentUser.getUid(); // взяли id


                myRef.child("Staff").child(RegisteredUserID).child("points").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {

                            Integer zp = Integer.parseInt(dataSnapshot.getValue().toString());
                            zp++;
                            myRef.child("Staff").child(RegisteredUserID).child("points").setValue(zp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



                myRef.child("Users").child(author_id).child("points").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {

                            Integer zp = Integer.parseInt(dataSnapshot.getValue().toString());
                            zp += Integer.parseInt(mark.getText().toString());
                            myRef.child("Users").child(author_id).child("points").setValue(zp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



                FirebaseDatabase.getInstance().getReference().child("Compositions").child("Day1").child(composition_id).child("feedback").setValue(answer.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Compositions").child("Day1").child(composition_id).child("mark").setValue(Integer.parseInt(mark.getText().toString()));
                FirebaseDatabase.getInstance().getReference().child("Compositions").child("Day1").child(composition_id).child("checked").setValue(true);


                Intent intent = new Intent(STAFF_answer.this, STAFF_MainActivity.class);
                startActivity(intent);

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
                Intent intent = new Intent(STAFF_answer.this, STAFF_composition.class);

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(STAFF_answer.this, STAFF_MainActivity.class);
        startActivity(intent);
    }
}