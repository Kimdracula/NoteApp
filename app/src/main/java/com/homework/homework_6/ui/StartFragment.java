package com.homework.homework_6.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.homework.homework_6.R;



public class StartFragment extends Fragment{
    AboutFragment aboutFragment;
    SettingsFragment settingsFragment;
    RecycleListFragment recycleListFragment;
    MaterialButton buttonAbout;
    MaterialButton buttonSettings;
    MaterialButton buttonGoToList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aboutFragment = new AboutFragment();
        settingsFragment = new SettingsFragment();
      recycleListFragment = new RecycleListFragment();

        buttonAbout = view.findViewById(R.id.buttonAbout);
        buttonSettings = view.findViewById(R.id.buttonSettings);
        buttonGoToList = view.findViewById(R.id.buttonGoToList);

        buttonAbout.setOnClickListener(view1 -> getChildFragmentManager().beginTransaction().replace(R.id.main_menu_container,aboutFragment).commit());

        buttonSettings.setOnClickListener(view12 -> getChildFragmentManager().beginTransaction().replace(R.id.main_menu_container,settingsFragment).commit());

        buttonGoToList.setOnClickListener(view13 -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,recycleListFragment);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        });

    }
}
