package com.homework.homework_6.data;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.homework.homework_6.MainActivity;
import com.homework.homework_6.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataSourceImp implements DataSource {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String collectionPath = "NOTES";
    RecycleAdapter recycleAdapter;

    private ArrayList <CardData> notes;
    public DataSourceImp init(){
    notes = new ArrayList<>();
        firebaseFirestore.collection(collectionPath).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    // if the snapshot is not empty we are
                    // hiding our progress bar and adding
                    // our data in a list.
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        // after getting this list we are passing
                        // that list to our object class.
                        CardData c = d.toObject(CardData.class);
                        notes.add(c);
                        recycleAdapter.notifyDataSetChanged();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                //    Toast.makeText(CourseDetails.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                }
            });
            return this;
    }
    @Override
    public CardData getData(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public void changeData(int position, CardData cardData) {
        notes.set(position, cardData);
    }

    @Override
    public void addData(CardData cardData) {
        notes.add(cardData);
    }

    @Override
    public CardData deleteData(int position) {
        return notes.remove(position);
    }

    @Override
    public ArrayList<CardData> list() {
        return notes;
    }
}
