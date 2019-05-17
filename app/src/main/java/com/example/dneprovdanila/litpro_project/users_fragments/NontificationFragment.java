package com.example.dneprovdanila.litpro_project.users_fragments;

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
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_NontificationFragment;
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_composition;
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

public class NontificationFragment extends Fragment {


    private RecyclerView composition_RW;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;

    public NontificationFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_nontification,container, false);


        composition_RW = (RecyclerView) v.findViewById(R.id.user_composition_RW);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        composition_RW.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        return v;    }


    @Override
    public void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {

            FirebaseRecyclerAdapter<Composition, NontificationFragment.UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Composition, NontificationFragment.UserViewholder>
                    (Composition.class, R.layout.new_composition_card, NontificationFragment.UserViewholder.class, FirebaseDatabase.getInstance().getReference().child("Compositions").child("Day1")) {


                @Override
                protected void populateViewHolder(final NontificationFragment.UserViewholder viewHolder, final Composition model, int position) {

                    final String listPostKey = getRef(position).getKey().toString();

                    //final String author_of_this_composition;
                    final String this_user = currentUser.getUid().toString();


                    // смотрим автора этого сочинения
                    FirebaseDatabase.getInstance().getReference().child("Compositions").child("Day1").child(listPostKey).child("author_id").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                final String author_of_this_composition = dataSnapshot.getValue(String.class);
                                if (this_user.equals(author_of_this_composition) && model.getChecked())
                                {
                                    final String user_id = model.getAuthor_id();
                                    final String staff_id = currentUser.getUid();


                                    viewHolder.setTitle(model.getComposition_title());
                                    viewHolder.setName(model.getAuthor_name());

                                    viewHolder.kView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getActivity(), USER_composition.class);

                                            intent.putExtra("feedback", model.getFeedback().toString());
                                            intent.putExtra("author", model.getAuthor_name().toString());
                                            intent.putExtra("composition", model.getComposition().toString());
                                            intent.putExtra("composition_id", listPostKey.toString());
                                            intent.putExtra("title", model.getComposition_title().toString());
                                            intent.putExtra("mark_text", Integer.toString(model.getMark()));

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










                    /*if(model.getChecked())
                    {
                        final String user_id = model.getAuthor_id();
                        final String staff_id = currentUser.getUid();

                        Log.e(TAG, "!CHEKED");

                      *//*  viewHolder.setTitle(model.getComposition_title());
                        viewHolder.setName(model.getAuthor_name());*//*


                        viewHolder.setTitle(model.getComposition_title());
                        viewHolder.setName(model.getAuthor_name());

                        viewHolder.kView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), USER_composition.class);

                                intent.putExtra("feedback", model.getFeedback().toString());
                                intent.putExtra("author", model.getAuthor_name().toString());
                                intent.putExtra("composition", model.getComposition().toString());
                                intent.putExtra("composition_id", listPostKey.toString());
                                intent.putExtra("title", model.getComposition_title().toString());


                                startActivity(intent);
                            }
                        });
                    }
                    else
                    {
                        Log.e(TAG, "non checked");
                    }*/
                }
            };
            composition_RW.setAdapter(firebaseRecyclerAdapter);
        }
    }



    public static class UserViewholder extends RecyclerView.ViewHolder {
        View kView;

        public UserViewholder(View itemView) {
            super(itemView);
            kView = itemView;

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public void setName(String name){
            TextView block_name =  (TextView)kView.findViewById(R.id.name_surname);
            block_name.setText(name);
        }

        public void setTitle(String title) {
            TextView pupil_number = (TextView)kView.findViewById(R.id.com_title);
            //pupil_number.setText(String.valueOf(title));
            pupil_number.setText(title);
        }

        public void SetPhoto(String url)
        {
            /*Glide.with(this)
                    .load(currentUser.getPhotoUrl().toString())
                    .into(profilePhoto);*/
        }
    }
}
