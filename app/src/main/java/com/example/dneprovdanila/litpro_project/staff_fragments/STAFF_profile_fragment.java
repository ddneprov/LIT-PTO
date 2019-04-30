package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.support.v4.app.Fragment;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.SettingsActivity;
import com.example.dneprovdanila.litpro_project.Staff;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class STAFF_profile_fragment extends  Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    //private ArrayList<User> pupils = new ArrayList<User>();


    public TextView points;
    public TextView staff_name;
    public TextView status;

    private RecyclerView mBlogList;
    public ImageView settings;
    FirebaseUser currentUser;

    Integer pupil_current_namber = 0;


    public STAFF_profile_fragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_staff_profile_fragment,container, false);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.keepSynced(true);



        mBlogList = (RecyclerView) v.findViewById(R.id.myrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mBlogList.setLayoutManager(layoutManager);



/////////////////////////////////////////////////////////////////////////////
        settings = (ImageView) v.findViewById(R.id.settings);
        points = (TextView) v.findViewById(R.id.points);
        staff_name = (TextView) v.findViewById(R.id.staff_name);
        status = (TextView) v.findViewById(R.id.status);
        currentUser = mAuth.getCurrentUser();

        String RegisteredUserID = currentUser.getUid(); // взяли id



        myRef.child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                points.setText(dataSnapshot.child("points").getValue().toString());
                staff_name.setText(dataSnapshot.child("name").getValue().toString());
                status.setText(dataSnapshot.child("status").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });
        return v;
    }


    Boolean flag = true;
    Boolean disable_right_now = false;
    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

            pupil_current_namber = 0;

            FirebaseRecyclerAdapter<User, UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewholder>
                    (User.class, R.layout.pupil_card, UserViewholder.class, myRef.child("Users")) {

                @Override
                protected void populateViewHolder(UserViewholder viewHolder, User model, int position) {
                    viewHolder.setName(model.getName()); // выводим имя
                    viewHolder.setNumber(++position); // номер студента в списке
                    final String user_id = model.getId();
                    final String staff_id = currentUser.getUid();
                    Switch select_status = (Switch) viewHolder.mView.findViewById(R.id.select_status);   /* изначально все включены */

                    /* если не пустой, но занят другим, то нельзя менять switch*/
                    if(!"".equals(model.getTeacher_id()) && !model.getTeacher_id().equals(staff_id))
                        select_status.setEnabled(false);




                    // если не прикреплен проверяющий то выключаем
                    if("".equals(model.getTeacher_id()) && !disable_right_now)// если ученик уже выбран другим проверяющим то ставим отметку выбранно
                        select_status.toggle();

                    disable_right_now = false;
                    select_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean does_selected) {
                            if(does_selected) // если проверяющий выбрал ученика
                            {
                                // myRef.child("Users").child(user_id).child("selected").setValue(true);
                                myRef.child("Users").child(user_id).child("teacher_id").setValue(staff_id);
                                //flag = false;
                                disable_right_now = true;
                            }
                            else// если отменил
                            {
                                /*убираем у проверяющего*/
                                //myRef.child("Users").child(user_id).child("selected").setValue(false);
                                myRef.child("Users").child(user_id).child("teacher_id").setValue("");
                                //flag = false;
                                disable_right_now = true;
                            }
                        }
                    });
                }
            };
            mBlogList.setAdapter(firebaseRecyclerAdapter);
            //firebaseRecyclerAdapter.notifyDataSetChanged();
        }
    }


    public static class UserViewholder extends RecyclerView.ViewHolder {
        View mView;

        public UserViewholder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setName(String name){
            TextView block_name =  (TextView)mView.findViewById(R.id.post_title);
            block_name.setText(name);
        }

        public void setNumber(Integer number)
        {
            TextView pupil_number = (TextView)mView.findViewById(R.id.pupil_number);
            pupil_number.setText(String.valueOf(number));
        }
    }



    @Override
    public void onStop() {
        super.onStop();

    }
}



  /* //если к ученику прикреплен этот проверяющий то замочек
                    if(model.getTeacher_id().equals(staff_id)) /// ставим замочек если проверяющий выбрал себе ученика
                    {
                        Drawable d = getResources().getDrawable(android.R.drawable.ic_lock_lock);
                        ImageView yours =  (ImageView)viewHolder.mView.findViewById(R.id.yours);
                        yours.setImageDrawable(d);
                    }*/
