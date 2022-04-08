package com.my.notes.ui

import android.app.Dialog
import android.os.Bundle
import com.my.notes.MainActivity
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ExitDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity() as MainActivity
        val builder =
            AlertDialog.Builder(activity)
        builder.setTitle("Внимание!")
            .setCancelable(true)
            .setMessage("Вы действительно хотитие выйти?")
            .setPositiveButton("Да") { dialog: DialogInterface?, id: Int ->
                Toast.makeText(activity, "До свидания!", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
            .setNegativeButton("Нет") { dialog: DialogInterface?, id: Int -> }
        return builder.create()
    }
}