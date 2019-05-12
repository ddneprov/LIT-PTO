package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dneprovdanila.litpro_project.Composition;
import com.example.dneprovdanila.litpro_project.R;
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

import static android.support.constraint.Constraints.TAG;

public class STAFF_NontificationFragment extends Fragment {


    private RecyclerView composition_RW;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;

    public STAFF_NontificationFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_staff_nontification,container, false);


        composition_RW = (RecyclerView) v.findViewById(R.id.composition_RW);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        composition_RW.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        return v;
    }






    Boolean mine_and_nonchecked = false;

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

            FirebaseRecyclerAdapter<Composition, STAFF_NontificationFragment.UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Composition, STAFF_NontificationFragment.UserViewholder>
                    (Composition.class, R.layout.new_composition_card, STAFF_NontificationFragment.UserViewholder.class, myRef.child("Compositions").child("Day1")) {


                @Override
                protected void populateViewHolder(final STAFF_NontificationFragment.UserViewholder viewHolder, final Composition model, int position) {
//                    myRef.child("Staff").child("pupils")


                    if(!model.getChecked())
                    {
                        final String user_id = model.getAuthor_id();
                        final String staff_id = currentUser.getUid();

                        Log.e(TAG, "!CHEKED");


/*
                        viewHolder.setTitle(model.getComposition_title());
                        viewHolder.setName(model.getAuthor_name());*/



                        FirebaseDatabase.getInstance().getReference().child("Staff").child(staff_id).child("pupils").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e(TAG, "try dataSnapshot 1");
                                if (dataSnapshot.exists())
                                {
                                    Log.e(TAG, "dataSnapshot exist 2");
                                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                    ArrayList<String> pupils =  dataSnapshot.getValue(t);

                                    if(pupils.contains(user_id.toString()))
                                    {
                                        Log.e(TAG, "CHOOSED 3");
                                        viewHolder.setTitle(model.getComposition_title());
                                        viewHolder.setName(model.getAuthor_name());

                                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getActivity(), test.class);


                                                intent.putExtra("title", model.getComposition_title().toString());
                                                intent.putExtra("author", model.getAuthor_name().toString());
                                                intent.putExtra("composition", model.getComposition().toString());
                                                //intent.putExtra("words", model.getWords_count().toString());

                                                startActivity(intent);


                                            }
                                        });


                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            };
            composition_RW.setAdapter(firebaseRecyclerAdapter);
        }
    }



    public static class UserViewholder extends RecyclerView.ViewHolder {
        View mView;

        public UserViewholder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public void setName(String name){
            TextView block_name =  (TextView)mView.findViewById(R.id.name_surname);
            block_name.setText(name);
        }

        public void setTitle(String title) {
            TextView pupil_number = (TextView)mView.findViewById(R.id.com_title);
            pupil_number.setText(String.valueOf(title));
        }

        public void SetPhoto(String url)
        {
            /*Glide.with(this)
                    .load(currentUser.getPhotoUrl().toString())
                    .into(profilePhoto);*/
        }
    }
}
