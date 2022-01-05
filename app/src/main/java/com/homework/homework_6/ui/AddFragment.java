package com.homework.homework_6.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.homework.homework_6.R;

import java.util.Calendar;

public class AddFragment extends Fragment {
    EditText headerText;
    EditText description;
    MaterialButton setDateButton;
    DatePicker datePicker;
    TextView textViewSetDate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


       headerText = view.findViewById(R.id.editTextHead);
        description = view.findViewById(R.id.editTextDescription);
        setDateButton = view.findViewById(R.id.buttonSetDate);
        datePicker = view.findViewById(R.id.datePicker);
        textViewSetDate = view.findViewById(R.id.textViewDate);
        initViews();
    }




    public void initViews(){
        datePicker.setCalendarViewShown(false);
        Calendar today = Calendar.getInstance();
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) -> textViewSetDate.setText("Год: " + year + " " + "Месяц: "
                        + (monthOfYear + 1) + " " + "День: " + dayOfMonth));

        setDateButton.setOnClickListener(view -> {
            //Тут буду реализовывать сохранение даты в переменную
            Toast.makeText(getContext(),
                    "Дата установлена", Toast.LENGTH_SHORT).show();
        });

        setDateButton.setOnLongClickListener(v -> {
            setCurrentDateOnView();

            return true;
        });
    }

    private void setCurrentDateOnView() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        textViewSetDate.setText(new StringBuilder()
                .append(day).append(".").append(month + 1).append(".")
                .append(year));

        // Устанавливаем текущую дату для DatePicker
        datePicker.init(year, month, day, null);}

    }



