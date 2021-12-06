package com.homework.homework_6;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ExitDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Caution!")
                .setCancelable(true)
                .setMessage("Do you really want to exit?")
                .setPositiveButton("Yes",(dialog,id)->{
                    Toast.makeText(activity, "See you later!", Toast.LENGTH_SHORT).show();
    requireActivity().finish();
    })
                .setNegativeButton("No",(dialog,id)->{
                });
        Dialog dialog = builder.create();
        return dialog;
    }
}
