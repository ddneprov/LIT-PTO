package com.example.dneprovdanila.litpro_project.staff_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dneprovdanila.litpro_project.R;
import com.example.dneprovdanila.litpro_project.staff_fragments.STAFF_profile_fragment;
import com.example.dneprovdanila.litpro_project.users_fragments.HomeFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.MessageFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.NontificationFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.ProfileFragment;
import com.example.dneprovdanila.litpro_project.users_fragments.TaskFragment;



public class STAFF_MainActivity extends AppCompatActivity{

  /*  final Fragment fragment1 = new STAFF_TaskFragment();
    final Fragment fragment2 = new STAFF_MessageFragment();
    final Fragment fragment3 = new STAFF_profile_fragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        BottomNavigationView bottomNav = findViewById(R.id.staff_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.staff_fragment_container, new STAFF_TaskFragment()).commit();

       /* fm.beginTransaction().add(R.id.staff_fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.staff_fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.staff_fragment_container,fragment1, "1").commit();*/

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.staff_navigation_task:
                            //finish();
                            selectedFragment = new STAFF_TaskFragment();
                            break;

                        case R.id.staff_navigation_message:
                            //finish();
                            selectedFragment = new STAFF_MessageFragment();
                            break;

                        case R.id.staff_navigation_profile:
                            //finish();
                            selectedFragment = new STAFF_profile_fragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.staff_fragment_container,
                            selectedFragment).commit();
                    return true; }
            };
}




  /*  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
            }
            return false;
        }
    };*/
