package com.my.notes.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.my.notes.MainActivity;
import com.my.notes.data.CardData;
import com.my.notes.observer.EventManager;

public class NoteDialogFragment extends DialogFragment {
    private final String collectionPath = "NOTES";
    private EventManager eventManager;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Внимание!")
                .setCancelable(true)
                .setMessage("Удалить заметку?")
                .setPositiveButton("Да",(dialog,id)->{
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection(collectionPath).document(cardData.getId()).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                      @Override
                                                      public void onSuccess(Void unused) {

                                                      }
                                                  }
                            ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    eventManager.notify(cardData);


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
