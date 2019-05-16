package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class STAFF_profile_fragment extends  Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    //private ArrayList<User> pupils = new ArrayList<User>();


    public TextView points;
    public TextView staff_name;
    public TextView status;

    Uri uriProfileImage;
    ImageView profilePhoto;

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


        profilePhoto = (ImageView)v.findViewById(R.id.profilePhoto) ;
        String RegisteredUserID = currentUser.getUid(); // взяли id

        if (currentUser.getPhotoUrl() != null)
        {
            Glide.with(this)
                    .load(currentUser.getPhotoUrl().toString())
                    .into(profilePhoto);
        }




        myRef.child("Staff").child(RegisteredUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    points.setText(dataSnapshot.child("points").getValue().toString());
                    staff_name.setText(dataSnapshot.child("name").getValue().toString());
                    status.setText(dataSnapshot.child("status").getValue().toString());
                }
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

            pupil_current_namber = 0;

            FirebaseRecyclerAdapter<User, UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewholder>
                    (User.class, R.layout.pupil_card, UserViewholder.class, myRef.child("Users")) {

                @Override
                protected void populateViewHolder(UserViewholder viewHolder, User model, int position) {
                    viewHolder.setName(model.getName()); // выводим имя
                    viewHolder.setNumber(++position); // номер студента в списке
                    final String user_id = model.getId();
                    final String staff_id = currentUser.getUid();
                    Switch select_status = (Switch) viewHolder.mView.findViewById(R.id.select_status);

                    if("".equals(model.getTeacher_id()))// если не присвоен проверяющий{
                        select_status.setChecked(false);

                    if(!"".equals(model.getTeacher_id()))
                    {
                        select_status.setChecked(true); // вкл
                        if(!staff_id.equals(model.getTeacher_id()))
                            select_status.setEnabled(false); // нужно
                    }

                    //select_status.setChecked(true); // вкл
                    //select_status.setClickable(false);//select_status.toggle();
                    //select_status.setChecked(true); // вкл
                    //select_status.setChecked(!select_status.isChecked());


                    select_status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (((Switch) v).isChecked()) {

                                /// добавляем ученика в список учителю
                                myRef.child("Staff").child(staff_id).child("pupils").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                            ArrayList<String> pupils =  dataSnapshot.getValue(t);
                                            pupils.add(user_id);
                                            myRef.child("Staff").child(staff_id).child("pupils").setValue(pupils);
                                            //Staff staff = dataSnapshot.getValue(Staff.class);
                                            //staff.Add_New_Pupil(user_id);
                                        }
                                        else //TODO: исправь pupils
                                        {
                                            /// если у преподователя нет списка то создаем новый
                                            ArrayList<String> pupils = new ArrayList<>();
                                            pupils.add(user_id);

                                            //myRef.child("Staff").child(staff_id).setValue("pupils");
                                            myRef.child("Staff").child(staff_id).child("pupils").setValue(pupils);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });


                                /// записываем ученику учителя
                                myRef.child("Users").child(user_id).child("teacher_id").setValue(staff_id.toString());
                            }
                            else /**   если выключили switch   */
                            {
                                /// удаляем из списка
                                myRef.child("Staff").child(staff_id).child("pupils").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                            ArrayList<String> pupils =  dataSnapshot.getValue(t);

                                            if (pupils.contains(user_id.toString()))
                                                pupils.remove(user_id.toString());

                                            myRef.child("Staff").child(staff_id).child("pupils").setValue(pupils);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });


                                /// убираем учителя у ученика
                                myRef.child("Users").child(user_id).child("teacher_id").setValue("".toString());
                            }
                        }
                    });
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