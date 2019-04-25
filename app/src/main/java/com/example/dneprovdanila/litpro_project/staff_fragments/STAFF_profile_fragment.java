package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.SettingsActivity;
import com.example.dneprovdanila.litpro_project.Staff;
import com.example.dneprovdanila.litpro_project.User;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class STAFF_profile_fragment extends  Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private ArrayList<User> pupils = new ArrayList<User>();


    public TextView points;

    private RecyclerView mBlogList;
    public ImageView settings;


    public STAFF_profile_fragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_staff_profile_fragment,container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.keepSynced(true);


        mBlogList = (RecyclerView) v.findViewById(R.id.myrecyclerview);
        mBlogList.setHasFixedSize(true);
        final FragmentActivity c = getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        mBlogList.setLayoutManager(layoutManager);

        /*final FragmentActivity t = getActivity();
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.myrecyclerview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(t);
        recyclerView.setLayoutManager(layoutManager2);
*/






/////////////////////////////////////////////////////////////////////////////
        settings = (ImageView) v.findViewById(R.id.settings);
        points = (TextView) v.findViewById(R.id.points);
        myRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid(); // взяли id
        pupils.clear();
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    User pupil = dSnapshot.getValue(User.class);
                    //pupils.add(pupil);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef.child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                points.setText(dataSnapshot.child("points").getValue().toString());
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
/////////////////////////////////////////////////////////////////////////////




        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User, UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewholder>
                (User.class, R.layout.pupil_card, UserViewholder.class, myRef.child("Users")) {

            @Override
            protected void populateViewHolder(UserViewholder viewHolder, User model, int position) {
                viewHolder.setName(model.getName());
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class UserViewholder extends RecyclerView.ViewHolder
    {
        View mView;
        public UserViewholder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }
        public void setName(String name){
            TextView block_name =  (TextView)mView.findViewById(R.id.post_title);
            block_name.setText(name);
        }
    }
}

