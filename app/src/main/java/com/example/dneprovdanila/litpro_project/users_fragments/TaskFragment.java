package com.example.dneprovdanila.litpro_project.users_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.Bottom_Sheet_Dialog;
import com.example.dneprovdanila.litpro_project.Composition;
import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.constraint.Constraints.TAG;

public class TaskFragment extends Fragment implements Bottom_Sheet_Dialog.BottomSheetListner {


    EditText composition;
    EditText composition_title;
    TextView words_count;
    ImageButton sendComposition;

    DatabaseReference myRef;

    public static boolean flag = false;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String value= words_count.getText().toString();
        outState.putString("composition", composition.getText().toString().trim());
        outState.putString("composition_title", composition_title.getText().toString().trim());
        outState.putInt("words_count", Integer.parseInt(value));

       /* outState.putString("composition", "сочинение");
        outState.putString("composition_title", "заголовок блять");
        outState.putInt("words_count", 228);*/
       }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "In frag's on create view");
        View view = inflater.inflate(R.layout.fragment_task, container, false);


        composition = (EditText) view.findViewById(R.id.composition);
        composition_title = (EditText) view.findViewById(R.id.composition_title);
        words_count = (TextView) view.findViewById(R.id.words_count);
        sendComposition = (ImageButton) view.findViewById(R.id.imageButton);

        myRef = FirebaseDatabase.getInstance().getReference();



        if (savedInstanceState != null) {
            String t_composition = savedInstanceState.getString("composition");
            String t_composition_title = savedInstanceState.getString("composition_title");
            Integer t_words_count = savedInstanceState.getInt("words_count");

            composition.setText(t_composition);
            composition_title.setText(t_composition_title);
            words_count.setText(t_words_count);
        }



        /* считаем слова */
        composition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String input = composition.getText().toString().trim().replaceAll("\n", "");
                String[] wordCount=input.split("\\s");
                words_count.setText(String.valueOf(wordCount.length));
            }
        });

        sendComposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TODO: а если ни к кому не привязан ?
                Bottom_Sheet_Dialog bottomSheet = new Bottom_Sheet_Dialog();
                bottomSheet.show(getFragmentManager(), "exemple_bottom_sheet");

                if (!flag)
                {
                    String value= words_count.getText().toString();
                    final int words = Integer.parseInt(value);
                    final String author_id = FirebaseAuth.getInstance().getCurrentUser().getUid();// взяли id


                    Log.e(TAG, "one");
                    myRef.child("Users").child(author_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {

                                Log.e(TAG, "three");

                                User user = dataSnapshot.getValue(User.class);
                                String name = user.getName().toString();
                                Integer points = user.getPoints();
                                myRef.child("Users").child(author_id).child("points").setValue(++points);

                                Composition new_composition = new Composition(author_id.toString(), name, composition_title.getText().toString().trim(), composition.getText().toString().trim(), false, words);
                                FirebaseDatabase.getInstance().getReference().child("Compositions").child("Day1").push().setValue(new_composition);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e(TAG, "four");

                        }
                    });
                }

            }
        });


        return view;
    }






    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String a;
        if (savedInstanceState != null)
        {
            a = savedInstanceState.getString("composition");
            composition.setText(a);
        }
    }

    @Override
    public void onButtonClicked(Boolean agreement) {
        Log.e(TAG, "zero");
        flag = agreement;
    }


   /* @Override
    public void onButtonClicked(Boolean user_agreement) {
        if (user_agreement)
        {
            String value= words_count.getText().toString();
            final int words = Integer.parseInt(value);
            final String author_id = FirebaseAuth.getInstance().getCurrentUser().getUid();// взяли id


            myRef.child("Users").child(author_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {




                        User user = dataSnapshot.getValue(User.class);
                        String name = user.getName().toString();
                        Integer points = user.getPoints();

                        myRef.child("Users").child(author_id).child("points").setValue(++points);
                        Composition new_composition = new Composition(author_id.toString(), name, composition_title.getText().toString().trim(), composition.getText().toString().trim(), false, words);
                        FirebaseDatabase.getInstance().getReference().child("Compositions").child("Day1").push().setValue(new_composition);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }*/
}
