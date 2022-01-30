package com.homework.homework_6.ui;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.homework.homework_6.MainActivity;

import java.util.Calendar;
import java.util.Date;

public class CreateDatePicker {
    DatePickerDialog datePickerDialog;
    Calendar cal = Calendar.getInstance();
    NoteFragment noteFragment;

   public void showDatePickerDialog(Context context) {
        datePickerDialog = new DatePickerDialog(context,
                android.R.style.Theme_Material_Dialog_NoActionBar, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        cal.get(Calendar.HOUR_OF_DAY);
       datePickerDialog.show();

    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
            noteFragment.textViewDate.setText(day + "." + month + "."+ year);
        }
    };


  java.util.Date getDateFromDatePicker() {

        cal.set(Calendar.YEAR, this.datePickerDialog.getDatePicker().getYear());
        cal.set(Calendar.MONTH, this.datePickerDialog.getDatePicker().getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePickerDialog.getDatePicker().getDayOfMonth());
        return cal.getTime();
    }

}
