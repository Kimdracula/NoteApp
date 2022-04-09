package com.my.notes.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.firestore.FirebaseFirestore
import com.my.notes.MainActivity
import com.my.notes.data.CardData
import com.my.notes.databinding.NoteFragmentBinding
import com.my.notes.observer.EventManager
import com.my.notes.utils.COLLECTION_PATH
import com.my.notes.utils.KEY_PARCEL
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment : Fragment() {
    private lateinit var tvDate: MaterialTextView
    private lateinit var textHeader: TextInputEditText
    private lateinit var textDescription: TextInputEditText
    private var cardData: CardData? = null
   private lateinit var datePickerDialog: DatePickerDialog
    private var picture = 0
    private lateinit var image: ImageView
    private var eventManager: EventManager? = null
    private lateinit var cal: Calendar
    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cal = Calendar.getInstance()
        initViews()
        initButtonDelete()
        initPickerDialog()
        if (arguments != null) {
            cardData = requireArguments().getParcelable(KEY_PARCEL)
            populateViews()
        } else setDefaultDate()
    }

    private fun setDefaultDate() {
        tvDate.text =
            SimpleDateFormat("dd.MMM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        eventManager = mainActivity.eventManager
    }

    override fun onDetach() {
        eventManager = null
        super.onDetach()
    }

    override fun onStop() {
        super.onStop()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val updatedCardData = collectCardData()
        if (arguments == null) {
            firebaseFirestore.collection(COLLECTION_PATH)
                .add(updatedCardData)
                .addOnSuccessListener {
                    Toast.makeText(
                        mainActivity,
                        "Your note has been added",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        mainActivity,
                        "Error while updating note",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            firebaseFirestore.collection(COLLECTION_PATH).document(cardData!!.id!!)
                .set(updatedCardData).addOnSuccessListener {
                    Toast.makeText(
                        mainActivity,
                        "Your note has been updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        mainActivity,
                        "Error while adding note",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        eventManager!!.notify(updatedCardData)
    }

    private fun collectCardData(): CardData {
        val title = Objects.requireNonNull(textHeader.text).toString()
        val description = textDescription.text.toString()
        try {
            picture = cardData!!.picture
        } catch (ignored: NullPointerException) {
        }
        val date: Date? = try {
            dateFromDatePicker
        } catch (ignored: NullPointerException) {
            Calendar.getInstance().time
        }
        return CardData(title, description, picture, date)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        eventManager?.notify(cardData)
    }

    private fun populateViews() {
        textHeader.setText(cardData!!.header)
        textDescription.setText(cardData!!.description)
        tvDate.text =
            cardData!!.date?.let { SimpleDateFormat("dd.MMM.yyyy", Locale.getDefault()).format(it) }
        image.setImageResource(cardData!!.picture)
    }

    private fun initViews() {
        tvDate = binding.textViewDate
        textHeader = binding.editTextHeader
        textDescription = binding.editTextDescription
        image = binding.imageViewNote
        binding.buttonSetDate.setOnClickListener { datePickerDialog.show() }
        binding.floatingActionButton.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 3)
        }
    }

    @SuppressLint("SetTextI18n")
    private var dateSetListener = OnDateSetListener { _, year, month, day ->
        tvDate.text = "$day.$month.$year"
    }



     private fun initPickerDialog() {
        datePickerDialog = DatePickerDialog(
             requireContext(), android.R.style.Theme_Material_Dialog_NoActionBar, dateSetListener,
             cal[Calendar.YEAR],
             cal[Calendar.MONTH],
             cal[Calendar.DAY_OF_MONTH]
         )
        cal[Calendar.HOUR_OF_DAY]
    }

    private val dateFromDatePicker: Date
        get() {
            cal[Calendar.YEAR] = datePickerDialog.datePicker.year
            cal[Calendar.MONTH] = datePickerDialog.datePicker.month
            cal[Calendar.DAY_OF_MONTH] = datePickerDialog.datePicker.dayOfMonth
            return cal.time
        }

    private fun initButtonDelete() {
      binding.buttonDeleteNote.setOnClickListener {
            NoteDialogFragment().show(
                childFragmentManager, "DialogDeleteNote"
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            image.setImageURI(selectedImage)
        }
    }

    companion object {
        fun newInstance(cardData: CardData?): NoteFragment {
            val fragment = NoteFragment()
            val args = Bundle()
            args.putParcelable(KEY_PARCEL, cardData)
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun newInstance(): NoteFragment {
            return NoteFragment()
        }
    }
}
