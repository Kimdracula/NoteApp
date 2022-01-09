package com.homework.homework_6.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.homework.homework_6.data.CardData;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.R;
import com.homework.homework_6.data.RecycleAdapter;

public class NoteFragment extends Fragment implements Login {
    MaterialButton deleteNoteButton;
    TextInputEditText textHeader;
    TextInputEditText textDescription;
    int position;

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt(login);
        }
          populateViews();
        }


    private void populateViews() {
        DataSource dataSource = new DataSourceImp().init();
        textHeader.setText(dataSource.getData(position).getHeader());
       textDescription.setText(dataSource.getData(position).getDescription());
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

