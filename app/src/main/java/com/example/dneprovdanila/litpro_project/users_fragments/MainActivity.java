package com.example.dneprovdanila.litpro_project.users_fragments;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.dneprovdanila.litpro_project.Bottom_Sheet_Dialog;
import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_TaskFragment;
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_profile_fragment;
import com.example.dneprovdanila.litpro_project.users_fragments.HomeFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.MessageFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.NontificationFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.ProfileFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.TaskFragment;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements Bottom_Sheet_Dialog.BottomSheetListner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new STAFF_TaskFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            //finish();
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.navigation_task:
                            //finish();
                            selectedFragment = new TaskFragment();
                            break;

                        case R.id.navigation_message:
                            //finish();
                            selectedFragment = new MessageFragment();
                            break;

                        case R.id.navigation_nontification:
                            //finish();
                            selectedFragment = new NontificationFragment();
                            break;

                        case R.id.navigation_profile:
                            //finish();
                            selectedFragment = new ProfileFragment();
                            break;

                    }

                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    //        selectedFragment).commit();

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onButtonClicked(Boolean agreement) {
        Log.e(TAG, "zero 0");
        TaskFragment.flag = agreement;
    }
}