package com.homework.homework_6;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;


public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwitchMaterial switchMaterial1 = view.findViewById(R.id.switch1);
        SwitchMaterial switchMaterial2 = view.findViewById(R.id.switch2);
        SwitchMaterial switchMaterial3 = view.findViewById(R.id.switch3);
        SwitchMaterial switchMaterial4 = view.findViewById(R.id.switch4);

        switchMaterial1.setOnClickListener(view1 -> Toast.makeText(getContext(),
                R.string.dark_theme_applyed, Toast.LENGTH_SHORT).show());

        switchMaterial2.setOnClickListener(view12 -> Toast.makeText(getContext(),
                R.string.light_theme_applyed, Toast.LENGTH_SHORT).show());

        switchMaterial3.setOnClickListener(view13 -> Toast.makeText(getContext(),
                R.string.purple_theme_applyed, Toast.LENGTH_SHORT).show());


        switchMaterial4.setOnClickListener(view14 -> Toast.makeText(getContext(),
                R.string.blue_theme_applyed, Toast.LENGTH_SHORT).show());
    }
    }
