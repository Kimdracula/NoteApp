package com.my.notes.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.my.notes.MainActivity;

public class NoteDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Внимание!")
                .setCancelable(true)
                .setMessage("Удалить заметку?")
                .setPositiveButton("Да",(dialog,id)-> Toast.makeText(activity, "Заметка удалена!", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Нет",(dialog,id)->{
                });
        return builder.create();
    }
}
