package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dneprovdanila.litpro_project.R;

public class STAFF_MessageFragment extends Fragment {


    public STAFF_MessageFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_staff_message,container, false);

        return v;
    }
}
