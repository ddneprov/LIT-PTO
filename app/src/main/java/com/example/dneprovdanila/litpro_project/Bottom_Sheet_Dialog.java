package com.example.dneprovdanila.litpro_project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Bottom_Sheet_Dialog extends BottomSheetDialogFragment {

    private BottomSheetListner mListner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_confirm, container, false);


        Button send_agree = v.findViewById(R.id.bottom_yes);
        //Button send_disagree = v.findViewById(R.id.bottom_no);


        send_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListner.onButtonClicked(true);
                dismiss();
            }
        });

      /*  send_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListner.onButtonClicked(false);
                dismiss();
            }
        });*/


        return  v;
    }


    public interface BottomSheetListner {
        void onButtonClicked(Boolean agreement);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListner = (BottomSheetListner) context;

        }catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "u should implement BottomSheetListner");
        }
    }
}
