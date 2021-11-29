package com.homework.homework_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    FragmentList fragmentList;
    AddFragment addFragment;
    NoteFragment noteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentList = new FragmentList();
        addFragment = new AddFragment();
        noteFragment = new NoteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!checkLandOrient()){
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentList).commit();
        }
else{
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentList).commit();
            fragmentManager.beginTransaction().replace(R.id.fragment_note_container,noteFragment).commit();
}
    }

    public Boolean checkLandOrient(){
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}