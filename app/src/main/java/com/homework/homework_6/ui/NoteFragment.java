package com.homework.homework_6.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.homework.homework_6.data.CardData;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.R;

public class NoteFragment extends Fragment implements Login {
    MaterialButton deleteNoteButton;
    CardData cardData;
    TextView textViewHeader;


    public static NoteFragment newInstance(CardData cardData) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(login, cardData);
        noteFragment.setArguments(args);
        return noteFragment;
    }

    public static NoteFragment newInstance() {
        return new NoteFragment();
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
        if (cardData != null) {
            cardData = getArguments().getParcelable(login);
            populateViews();



        }
}

    private void populateViews() {
        textViewHeader.setText(cardData.getDescription());
    }

    private void initTextViews(View view) {
        textViewHeader = view.findViewById(R.id.noteFragmentHeader);
    }


    private void initButtonDelete(View view) {
        deleteNoteButton = view.findViewById(R.id.buttonDeleteNote);
        deleteNoteButton.setOnClickListener(view1 -> new NoteDialogFragment().show(getChildFragmentManager(), "DialogDeleteNote"));
    }
}

