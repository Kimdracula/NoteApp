package com.my.notes.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.my.notes.MainActivity
import com.my.notes.data.DataSource
import com.my.notes.observer.EventManager

class NoteDialogFragment : DialogFragment() {
    private val collectionPath = "NOTES"
    private var eventManager: EventManager? = null
    private var dataSource: DataSource? = null
    private var position = 0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity() as MainActivity
        if (arguments != null && requireArguments().containsKey("name")) {
            val bundle = this.arguments
            position = bundle!!.getInt("position_to_delete")
        }
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Внимание!")
            .setCancelable(true)
            .setMessage("Удалить заметку?")
            .setPositiveButton(
                "Да"
            ) { _: DialogInterface?, _: Int ->
                val firebaseFirestore = FirebaseFirestore.getInstance()
                firebaseFirestore.collection(collectionPath)
                    .document(dataSource!!.getData(position).id!!).delete()
                    .addOnSuccessListener {
                        Log.d(
                            "Success TAG",
                            "Its ok with reading Firebase "
                        )
                    }
                    .addOnFailureListener {
                        Log.d(
                            "Error TAG",
                            "get failed with reading Firebase "
                        )
                    }
                Toast.makeText(activity, "Заметка удалена!", Toast.LENGTH_SHORT).show()
                TODO("Нужен переход на главный фрагмент")
            }
            .setNegativeButton("Нет") { _: DialogInterface?, _: Int -> }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val mainActivity = context as MainActivity
        eventManager = mainActivity.eventManager
    }

    override fun onDetach() {
        eventManager = null
        super.onDetach()
    }
}