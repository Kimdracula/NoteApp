package com.homework.homework_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    FragmentList fragmentList;
    AddFragment addFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentList = new FragmentList();
        addFragment = new AddFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!checkLandOrient()){
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragmentList).commit();
        }
else{
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentList).commit();
            fragmentManager.beginTransaction().add(R.id.fragment_add_note_container,addFragment).commit();
}
    }

    public Boolean checkLandOrient(){
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}