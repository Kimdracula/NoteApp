package com.my.notes.data;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class DataSourceImp implements DataSource {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String collectionPath = "NOTES";
    private ArrayList <CardData> notes;

    @Override
    public DataSource init(CardDataResponse cardDataResponse) {
        notes = new ArrayList<>();
        firebaseFirestore.collection(collectionPath).orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list) {
                    CardData c = d.toObject(CardData.class);
                    c.setId(d.getId());
                    notes.add(c);
                    cardDataResponse.initialized(DataSourceImp.this);
                }
            }

        }).addOnFailureListener(e -> {
            Log.d("Error TAG", "get failed with reading Firebase ");

        });
        if (cardDataResponse != null){
            cardDataResponse.initialized(this);
        }
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

}
