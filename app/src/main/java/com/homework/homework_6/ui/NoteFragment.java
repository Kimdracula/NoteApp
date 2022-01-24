package com.homework.homework_6.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.homework.homework_6.MainActivity;
import com.homework.homework_6.data.CardData;
import com.homework.homework_6.data.DataSource;
import com.homework.homework_6.data.DataSourceImp;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.R;
import com.homework.homework_6.observer.EventManager;

public class NoteFragment extends Fragment implements Login {
    MaterialButton deleteNoteButton;
    TextInputEditText textHeader;
    TextInputEditText textDescription;
    CardData cardData;
    DataSource dataSource;
    int position;
    EventManager eventManager;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(login);}
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt(login);
        }
          populateViews();
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
        return  new CardData(title,description);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventManager.notify(cardData);
    }

    private void populateViews() {

        textHeader.setText(cardData.getHeader());
        textDescription.setText(cardData.getDescription());
    }

    private void initTextViews(View view) {
        textHeader = view.findViewById(R.id.editTextHeader);
        textDescription = view.findViewById(R.id.editTextDescription);
    }


    private void initButtonDelete(View view) {
        deleteNoteButton = view.findViewById(R.id.buttonDeleteNote);
        deleteNoteButton.setOnClickListener(view1 -> new NoteDialogFragment().show(getChildFragmentManager(), "DialogDeleteNote"));
    }
}

