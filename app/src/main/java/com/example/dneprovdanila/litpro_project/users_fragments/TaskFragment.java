package com.example.dneprovdanila.litpro_project.users_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dneprovdanila.litpro_project.R;

public class TaskFragment extends Fragment {


    EditText composition;
    EditText composition_title;
    TextView words_count;
    ImageButton sendComposition;


    public TaskFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task,container, false);

        composition = (EditText) view.findViewById(R.id.composition);
        composition_title = (EditText) view.findViewById(R.id.composition_title);
        words_count = (TextView) view.findViewById(R.id.words_count);
        sendComposition = (ImageButton) view.findViewById(R.id.imageButton);


        composition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                /*if (charSequence.equals(' ')) {
                    words++;
                    words_count.setText(Integer.toString(words));

                }*/

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String input = composition.getText().toString().trim().replaceAll("\n", "");
                String[] wordCount=input.split("\\s");
                words_count.setText(String.valueOf(wordCount.length));
            }

        });

        return view;
    }
}
