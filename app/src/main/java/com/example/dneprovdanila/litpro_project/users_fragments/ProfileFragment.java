package com.example.dneprovdanila.litpro_project.users_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {


    private static final int CHOOSE_IMAGE = 101;
    ImageView profilePhoto;
    FirebaseAuth mAuth;
    TextView pupil_name;
    DatabaseReference jLoginDatabase;
    TextView points;
    DatabaseReference myRef;



    public ProfileFragment() {
    }

    public ImageView settings;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        pupil_name = (TextView) view.findViewById(R.id.pupil_name);
        settings = (ImageView) view.findViewById(R.id.settings);
        points = (TextView) view.findViewById(R.id.points);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.keepSynced(true);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid(); // взяли id
        jLoginDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);

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



}


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


