package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.content.Intent;
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

    static Integer pupil_current_namber = 0;


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



    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

            FirebaseRecyclerAdapter<User, UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewholder>
                    (User.class, R.layout.pupil_card, UserViewholder.class, myRef.child("Users")) {


                @Override
                protected void populateViewHolder(UserViewholder viewHolder, User model, int position) {
                    viewHolder.setName(model.getName()); // выводим имя
                    //pupil_current_namber++;
                    //viewHolder.setNumber(pupil_current_namber); // номер студента в списке


                    Switch select_status = (Switch) viewHolder.mView.findViewById(R.id.select_status);

                    final String user_id = model.getId();
                    final String staff_id = currentUser.getUid();

                    assert model.getTeacher_id() != null;

                    if(!"".equals(model.getTeacher_id()))// если ученик уже выбран другим проверяющим то ставим отметку выбранно
                        select_status.toggle();

                    /*if("".equals(model.getTeacher_id()) || model.getTeacher_id().equals(staff_id))// если не выбран, или выбран нами, то слушаем
                    {*/
                        select_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean selected) {
                                if(selected) // если проверяющий выбрал ученика
                                {
                                    /*убираем у студента*/
                                    myRef.child("Users").child(user_id).child("selected").setValue(true);
                                    myRef.child("Users").child(user_id).child("teacher_id").setValue(staff_id);
                                }
                                else // если отменил
                                {
                                    /*убираем у проверяющего*/
                                    myRef.child("Users").child(user_id).child("selected").setValue(false);
                                    myRef.child("Users").child(user_id).child("teacher_id").setValue("");
                                }
                            }
                        });
                    //}
                }
            };
            mBlogList.setAdapter(firebaseRecyclerAdapter);
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