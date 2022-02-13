package com.homework.homework_6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homework.homework_6.observer.EventManager;
import com.homework.homework_6.ui.AboutFragment;
import com.homework.homework_6.ui.ExitDialogFragment;
import com.homework.homework_6.ui.SettingsFragment;
import com.homework.homework_6.ui.StartFragment;

public class MainActivity extends AppCompatActivity {
 StartFragment startFragment;
 AboutFragment aboutFragment;
 SettingsFragment settingsFragment;
    EventManager eventManager;

    public FirebaseFirestore getDb() {
        return db;
    }

    FirebaseFirestore db;


    public EventManager getEventManager() {
        return eventManager;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsFragment = new SettingsFragment();
        aboutFragment = new AboutFragment();
        startFragment = new StartFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,startFragment).commit();
        initDrawer();
        eventManager = new EventManager();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    private void initDrawer() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();


            switch (id) {
                case R.id.nav_about:
                    openAboutFragment();
                    return true;
                case R.id.nav_settings:
                    openSettingsFragment();
                case R.id.nav_theme:
                case R.id.nav_share:
                case R.id.nav_send:
                    makeMessage();
                    return true;
            }
            return false;
        });

    }

    private void openSettingsFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,settingsFragment);
        fragmentCommit(fragmentTransaction);
    }

    private void fragmentCommit(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void makeMessage() {
        Toast.makeText(getApplicationContext(),
                R.string.plug_message, Toast.LENGTH_SHORT).show();
    }

    private void openAboutFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,aboutFragment);
        fragmentCommit(fragmentTransaction);
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            new ExitDialogFragment().show(getSupportFragmentManager(), "DialogTAG");

        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}