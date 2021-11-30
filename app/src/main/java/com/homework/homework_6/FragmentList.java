package com.homework.homework_6;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import com.google.android.material.button.MaterialButton;

public class FragmentList extends ListFragment implements Login {
    MaterialButton addNoteButton;
    AddFragment addFragment;
    NoteFragment noteFragment;
    Constants constants;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentlist,container,false);
    }

    // определяем массив типа String
    final String[] catNames = new String[]{"Заметка 1", "Заметка 2", "Заметка 3",
            "Заметка 4", "Заметка 5", "Заметка 6", "Заметка 7", "Заметка 8", "Заметка 9",
            "Заметка 10"};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addFragment = new AddFragment();
        noteFragment = new NoteFragment();
        constants = new Constants();
        mainActivity = new MainActivity();
        ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, catNames);
        setListAdapter(adapter);

      addNoteButton = view.findViewById(R.id.buttonAddNote);
      addNoteButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,addFragment);

              fragmentTransaction.addToBackStack("");
              fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
          }
      });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle result = new Bundle();
        constants.setHeader(getListView().getItemAtPosition(position).toString());

        result.putParcelable(login,constants);
        noteFragment.setArguments(result);



        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!checkLandOrient()){
            fragmentTransaction.replace(R.id.fragment_container,noteFragment);
        }
        else{
            fragmentTransaction.replace(R.id.fragment_note_container,noteFragment);
        }
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    public Boolean checkLandOrient(){
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
