package com.homework.homework_6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class NoteFragment extends Fragment implements Login {
    Constants constants;
    MaterialButton deleteNoteButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewHeader = view.findViewById(R.id.noteFragmentHeader);
        constants = new Constants();
        deleteNoteButton = view.findViewById(R.id.buttonDeleteNote);
        Bundle bundle = getArguments();
        if (bundle != null) {
            constants = bundle.getParcelable(login);
            textViewHeader.setText(constants.getHeader());
            initButtonDelete();
}



        }

    private void initButtonDelete() {
        deleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NoteDialogFragment().show(getChildFragmentManager(), "DialogDeleteNote");
            }
        });
    }
}

