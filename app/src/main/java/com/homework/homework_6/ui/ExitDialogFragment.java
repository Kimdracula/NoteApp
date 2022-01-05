package com.homework.homework_6.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.homework.homework_6.MainActivity;

public class ExitDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Внимание!")
                .setCancelable(true)
                .setMessage("Вы действительно хотитие выйти?")
                .setPositiveButton("Да",(dialog,id)->{
                    Toast.makeText(activity, "До свидания!", Toast.LENGTH_SHORT).show();
    requireActivity().finish();
    })
                .setNegativeButton("Нет",(dialog,id)->{
                });
        Dialog dialog = builder.create();
        return dialog;
    }
}
