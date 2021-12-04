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
    SwitchMaterial switchMaterial1;
    SwitchMaterial switchMaterial2;
    SwitchMaterial switchMaterial3;
    SwitchMaterial switchMaterial4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchMaterial1 = view.findViewById(R.id.switch1);
        switchMaterial2 = view.findViewById(R.id.switch2);
        switchMaterial3 = view.findViewById(R.id.switch3);
        switchMaterial4 = view.findViewById(R.id.switch4);

        switchMaterial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Dark Theme установлена", Toast.LENGTH_SHORT).show();
            }
        });

        switchMaterial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Light Theme установлена", Toast.LENGTH_SHORT).show();
            }
        });

        switchMaterial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Purple Theme установлена", Toast.LENGTH_SHORT).show();
            }
        });


        switchMaterial4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Blue Theme установлена", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
