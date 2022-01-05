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
import com.homework.homework_6.Data;
import com.homework.homework_6.Login;
import com.homework.homework_6.R;
import com.homework.homework_6.ui.NoteDialogFragment;

public class NoteFragment extends Fragment implements Login {
    Data data;
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
        deleteNoteButton = view.findViewById(R.id.buttonDeleteNote);
        Bundle bundle = getArguments();
        if (bundle != null) {
          //  constants = bundle.getParcelable(login);
            textViewHeader.setText(data.getHeader());
            initButtonDelete();
}
        }

    private void initButtonDelete() {
        deleteNoteButton.setOnClickListener(view -> new NoteDialogFragment().show(getChildFragmentManager(), "DialogDeleteNote"));
    }
}

