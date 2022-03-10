package com.homework.homework_6.ui;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.homework.homework_6.MainActivity;
import com.homework.homework_6.R;
import com.homework.homework_6.data.CardData;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.observer.EventManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteFragment extends Fragment implements Login {
    private MaterialTextView textViewDate;
    private  TextInputEditText textHeader;
    private  TextInputEditText textDescription;
    private CardData cardData;
    private DatePickerDialog datePickerDialog;
    private int picture;
    private ImageView image;
    private EventManager eventManager;
    private Calendar cal = Calendar.getInstance();


    // Для редактирования данных
    public static NoteFragment newInstance(CardData cardData) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(login, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_fragment,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTextViews(view);
        initButtonDelete(view);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(login);
            populateViews();}
        else setDefaultDate();
        }

    private void setDefaultDate() {
        textViewDate.setText(new SimpleDateFormat("dd.MMM.yyyy").format(Calendar.getInstance().getTime()));
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

    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    private CardData collectCardData() {
        String title = this.textHeader.getText().toString();
        String description = this.textDescription.getText().toString();
        try {
            this.picture = cardData.getPicture();
        }catch (NullPointerException ignored) {
          //  picture = R.drawable.ic_theme;
        }
        Date date;
        try{
        date = getDateFromDatePicker();}
        catch (NullPointerException ignored){
            date = Calendar.getInstance().getTime();
        }
        return new CardData(title, description,picture, date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventManager.notify(cardData);
    }

    private void populateViews() {
          textHeader.setText(cardData.getHeader());
          textDescription.setText(cardData.getDescription());
          textViewDate.setText(new SimpleDateFormat("dd.MMM.yyyy").format(cardData.getDate()));
          image.setImageResource(cardData.getPicture());

    }

    private void initTextViews(View view) {
        image = view.findViewById(R.id.imageViewNote);
        textViewDate = view.findViewById(R.id.textViewDate);
        MaterialButton setDateButton = view.findViewById(R.id.buttonSetDate);
        setDateButton.setOnClickListener(view1 -> showDatePickerDialog());
        textHeader = view.findViewById(R.id.editTextHeader);
        textDescription = view.findViewById(R.id.editTextDescription);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 3);
            }
        });
    }



    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
textViewDate.setText(day + "." + month + "."+ year);
        }
    };


    private void showDatePickerDialog() {
        datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Material_Dialog_NoActionBar, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
                cal.get(Calendar.HOUR_OF_DAY);
        datePickerDialog.show();
    }


    private Date getDateFromDatePicker() {

        cal.set(Calendar.YEAR, this.datePickerDialog.getDatePicker().getYear());
        cal.set(Calendar.MONTH, this.datePickerDialog.getDatePicker().getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePickerDialog.getDatePicker().getDayOfMonth());
        return cal.getTime();
    }

    private void initButtonDelete(View view) {
        MaterialButton deleteNoteButton = view.findViewById(R.id.buttonDeleteNote);
        deleteNoteButton.setOnClickListener(view1 -> new NoteDialogFragment().show(getChildFragmentManager(), "DialogDeleteNote"));
    }

    @Override
 public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK&&data!=null){
            Uri selectedImage = data.getData();
            image.setImageURI(selectedImage);
        }
    }

}

