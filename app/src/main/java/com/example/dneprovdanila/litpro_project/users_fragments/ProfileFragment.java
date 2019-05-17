package com.example.dneprovdanila.litpro_project.users_fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.User;
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_profile_fragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {


    private static final int CHOOSE_IMAGE = 101;
    ImageView profilePhoto;
    FirebaseAuth mAuth;
    TextView pupil_name;
    DatabaseReference jLoginDatabase;
    TextView points;
    DatabaseReference myRef;



    private RecyclerView mBlogList;
    public ImageView settings;
    FirebaseUser currentUser;

    Integer pupil_current_namber = 0;

    public ProfileFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        pupil_name = (TextView) view.findViewById(R.id.pupil_name);
        settings = (ImageView) view.findViewById(R.id.settings);
        points = (TextView) view.findViewById(R.id.points);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.keepSynced(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String RegisteredUserID = currentUser.getUid(); // взяли id
        jLoginDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);



        mBlogList = (RecyclerView) view.findViewById(R.id.myrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mBlogList.setLayoutManager(layoutManager);



        jLoginDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                points.setText(user.getPoints().toString());
                pupil_name.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profilePhoto = (ImageView) view.findViewById(R.id.profilePhoto);
        if (currentUser.getPhotoUrl() != null)
        {
            Glide.with(this)
                    .load(currentUser.getPhotoUrl().toString())
                    .into(profilePhoto);
        }



        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
            if (mAuth.getCurrentUser() != null) {

                pupil_current_namber = 0;

                FirebaseRecyclerAdapter<User, ProfileFragment.UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, ProfileFragment.UserViewholder>
                        (User.class, R.layout.pupil_card_mark, ProfileFragment.UserViewholder.class, myRef.child("Users")) {

                    @Override
                    protected void populateViewHolder(UserViewholder viewHolder, User model, int position) {
                        viewHolder.setName(model.getName()); // выводим имя
                        viewHolder.setNumber(++position); // номер студента в списке
                        viewHolder.setPoints(Integer.toString(model.getPoints()));
                        final String user_id = model.getId();
                        final String staff_id = currentUser.getUid();

                        //Switch select_status = (Switch) viewHolder.mView.findViewById(R.id.select_status);
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

        public void setPoints(String points)
        {
            TextView pupil_points = (TextView)mView.findViewById(R.id.pupil_points);
            pupil_points.setText(points);
        }
    }

}


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


