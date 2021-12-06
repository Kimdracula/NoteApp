package com.homework.homework_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
 StartFragment startFragment;
 AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aboutFragment = new AboutFragment();
        startFragment = new StartFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,startFragment).commit();
        initDrawer();
    }

    private void initDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_about:
                        openAboutFragment();
                        return true;
                    case R.id.nav_settings:
                        openSettingsFragment();
                        return true;
                    case R.id.nav_theme:
                        openThemeFragment();
                        return true;
                    case R.id.nav_share:
                        openShareFragment();
                        return true;
                    case R.id.nav_send:
                        openSendFragment();
                        return true;
                }
                return false;
            }
        });

    }

    private void openSendFragment() {
        Toast.makeText(this,
                "Переходим на другой фрагмент", Toast.LENGTH_SHORT).show();
    }

    private void openShareFragment() {
        Toast.makeText(this,
                "Переходим на другой фрагмент", Toast.LENGTH_SHORT).show();
    }

    private void openThemeFragment() {
        Toast.makeText(this,
                "Переходим на другой фрагмент", Toast.LENGTH_SHORT).show();
    }

    private void openSettingsFragment() {
        Toast.makeText(this,
                "Переходим на другой фрагмент", Toast.LENGTH_SHORT).show();
    }

    private void openAboutFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,aboutFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
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