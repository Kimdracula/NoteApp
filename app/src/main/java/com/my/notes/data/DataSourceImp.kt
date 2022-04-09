package com.my.notes.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class DataSourceImp : DataSource {
    private var firebaseFirestore = FirebaseFirestore.getInstance()
    private var collectionPath = "NOTES"
    private var notes: MutableList<CardData> =ArrayList()

    override fun init(cardDataResponse: CardDataResponse): DataSource {
        firebaseFirestore.collection(collectionPath).orderBy("date", Query.Direction.DESCENDING)
            .get().addOnSuccessListener { queryDocumentSnapshots: QuerySnapshot ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (d in list) {
                        val c = d.toObject(CardData::class.java)
                        c?.id = d.id
                        if (c != null) {
                            notes.add(c)
                        }
                        cardDataResponse.initialized(this@DataSourceImp)
                    }
                }
            }.addOnFailureListener {
                Log.d(
                    "Error TAG",
                    "get failed with reading Firebase "
                )
            }
        cardDataResponse.initialized(this)
        return this
    }

    override fun getData(position: Int): CardData = notes[position]

    override fun size(): Int = notes.size


    override fun changeData(position: Int, cardData: CardData) {
            notes[position] = cardData

    }

    override fun addData(cardData: CardData) {
            notes.add(cardData)

    }

    override fun deleteData(position: Int): CardData = notes.removeAt(position)

}