package com.homework.homework_6.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.homework.homework_6.MainActivity;
import com.homework.homework_6.R;
import com.homework.homework_6.data.CardData;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.observer.EventManager;

import java.util.Calendar;

public class AddFragment extends Fragment implements Login {
    EditText headerText;
    EditText description;
    MaterialButton setDateButton;
    TextView textViewSetDate;
    CardData cardData;
    EventManager eventManager;
    Calendar dateAndTime=Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_fragment, container, false);
    }

    public static AddFragment newInstance(CardData cardData) {
        AddFragment addFragment = new AddFragment();
        Bundle args = new Bundle();
        args.putParcelable(login, cardData);
        addFragment.setArguments(args);
        return addFragment;
    }

    // Для добавления новых данных
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(login);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        headerText = view.findViewById(R.id.editTextHead);
        description = view.findViewById(R.id.editTextDescription);
        setDateButton = view.findViewById(R.id.buttonSetDate);
       // datePicker = view.findViewById(R.id.datePicker);
        textViewSetDate = view.findViewById(R.id.textViewDate);
        setInitialDateTime();
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
                setTime(view);
            }
        });
    }

    private void setInitialDateTime() {

        textViewSetDate.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(getContext(), t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) context;
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
        String title = this.headerText.getText().toString();
        String description = this.description.getText().toString();
        return new CardData(title, description);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventManager.notify(cardData);
    }

}





