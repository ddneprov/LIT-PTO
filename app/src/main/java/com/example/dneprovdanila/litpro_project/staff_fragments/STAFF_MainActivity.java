package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.users_fragments.HomeFragment;


public class STAFF_MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);


        BottomNavigationView bottomNav = findViewById(R.id.staff_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        //getSupportFragmentManager().beginTransaction().replace(R.id.staff_fragment_container, new STAFF_TaskFragment()).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.staff_fragment_container, new STAFF_TaskFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                       /* case R.id.staff_navigation_task:
                            selectedFragment = new STAFF_TaskFragment();
                            break;*/

                        case R.id.staff_navigation_message:
                            selectedFragment = new STAFF_MessageFragment();
                            break;

                        case R.id.staff_navigation_profile:
                            selectedFragment = new STAFF_profile_fragment();
                            break;

                        case R.id.staff_navigation_nontification:
                            selectedFragment = new STAFF_NontificationFragment();
                            break;
                    }

                   // getSupportFragmentManager().beginTransaction().replace(R.id.staff_fragment_container, selectedFragment).commit();

                    getSupportFragmentManager().beginTransaction().replace(R.id.staff_fragment_container, selectedFragment).commit();


                    return true;
                    //return false;

                }
            };




    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.staff_navigation_task:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.staff_navigation_message:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.staff_navigation_profile:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;

                case R.id.staff_navigation_nontification:
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    return true;
            }
            return false;
        }
    };*/


}