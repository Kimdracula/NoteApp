package com.homework.homework_6.ui;

import android.content.Context;
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
    DatePicker datePicker;
    TextView textViewSetDate;
CardData cardData;
EventManager eventManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_fragment,container,false);
    }

    public static AddFragment newInstance(CardData cardData) {
        AddFragment addFragment= new AddFragment();
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
            cardData = getArguments().getParcelable(login);}
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
        String title = this.headerText.getText().toString();
        String description = this.description.getText().toString();
        return  new CardData(title,description);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventManager.notify(cardData);
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



