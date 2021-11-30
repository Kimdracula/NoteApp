package com.homework.homework_6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class AddFragment extends Fragment {
    EditText headerText;
    EditText description;
    Button setDateButton;
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
        Calendar today = Calendar.getInstance();
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getContext(),
                                "Дата установлена", Toast.LENGTH_SHORT).show();

                        textViewSetDate.setText("Год: " + year + " " + "Месяц: "
                                + (monthOfYear + 1) + " " + "День: " + dayOfMonth);
                    }
                });

        setDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setDateButton.setText(new StringBuilder()
                        // Месяц отсчитывается с 0, поэтому добавляем 1
                        .append(datePicker.getDayOfMonth()).append(".")
                        .append(datePicker.getMonth() + 1).append(".")
                        .append(datePicker.getYear()));
            }
        });

        setDateButton.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                setCurrentDateOnView();

                return true;
            }
        });

    }

    private void setCurrentDateOnView() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        textViewSetDate.setText(new StringBuilder()
                // Месяц отсчитывается с 0, поэтому добавляем 1
                .append(day).append(".").append(month + 1).append(".")
                .append(year));

        // Устанавливаем текущую дату для DatePicker
        datePicker.init(year, month, day, null);
    }
    }



