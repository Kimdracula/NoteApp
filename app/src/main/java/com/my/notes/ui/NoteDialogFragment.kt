package com.my.notes.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.my.notes.MainActivity;
import com.my.notes.data.DataSource;
import com.my.notes.observer.EventManager;

public class NoteDialogFragment extends DialogFragment {
    private final String collectionPath = "NOTES";
    private EventManager eventManager;
    DataSource dataSource;
    int position;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        if (getArguments() != null && getArguments().containsKey("name")) {
            Bundle bundle = this.getArguments();
            position = bundle.getInt("position_to_delete");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Внимание!")
                .setCancelable(true)
                .setMessage("Удалить заметку?")
                .setPositiveButton("Да",(dialog,id)->{
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection(collectionPath).document(dataSource.getData(position).getId()).delete()
                            .addOnSuccessListener(unused -> {
                                        Log.d("Success TAG", "Its ok with reading Firebase ");
                            }
                            ).addOnFailureListener(e -> {
                        Log.d("Error TAG", "get failed with reading Firebase ");
                            });


                    Toast.makeText(activity, "Заметка удалена!", Toast.LENGTH_SHORT).show();}
                )
                .setNegativeButton("Нет",(dialog,id)->{
                });
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity)context;
        eventManager = mainActivity.getEventManager();
    }



    @Override
    public void onDetach() {
        eventManager = null;
        super.onDetach();
    }
}
