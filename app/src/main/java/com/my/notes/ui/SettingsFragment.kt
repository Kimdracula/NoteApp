package com.my.notes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.my.notes.R
import com.google.android.material.switchmaterial.SwitchMaterial
import android.widget.Toast
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switchMaterial1: SwitchMaterial = view.findViewById(R.id.switch1)
        val switchMaterial2: SwitchMaterial = view.findViewById(R.id.switch2)
        val switchMaterial3: SwitchMaterial = view.findViewById(R.id.switch3)
        val switchMaterial4: SwitchMaterial = view.findViewById(R.id.switch4)
        switchMaterial1.setOnClickListener {
            Toast.makeText(
                context,
                R.string.dark_theme_applyed, Toast.LENGTH_SHORT
            ).show()
        }
        switchMaterial2.setOnClickListener {
            Toast.makeText(
                context,
                R.string.light_theme_applyed, Toast.LENGTH_SHORT
            ).show()
        }
        switchMaterial3.setOnClickListener {
            Toast.makeText(
                context,
                R.string.purple_theme_applyed, Toast.LENGTH_SHORT
            ).show()
        }
        switchMaterial4.setOnClickListener {
            Toast.makeText(
                context,
                R.string.blue_theme_applyed, Toast.LENGTH_SHORT
            ).show()
        }
    }
}