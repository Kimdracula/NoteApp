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
    FragmentList fragmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentlist,container,false);
    }

    final String[] catNames = new String[]{"Заметка 1", "Заметка 2", "Заметка 3",
            "Заметка 4", "Заметка 5", "Заметка 6", "Заметка 7", "Заметка 8", "Заметка 9",
            "Заметка 10"};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentList = new FragmentList();
        addFragment = new AddFragment();
        noteFragment = new NoteFragment();
        constants = new Constants();
        mainActivity = new MainActivity();
        ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, catNames);
        setListAdapter(adapter);

      addNoteButton = view.findViewById(R.id.buttonAddNote);
      addNoteButton.setOnClickListener(view1 -> {


          FragmentTransaction fragmentTransaction = getFragmentTransaction();
          fragmentTransaction.replace(R.id.fragment_container,addFragment);
          transactionCommit(fragmentTransaction);

      });
    }

    private void transactionCommit(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @NonNull
    private FragmentTransaction getFragmentTransaction() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        return fragmentManager.beginTransaction();
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle result = new Bundle();
        constants.setHeader(getListView().getItemAtPosition(position).toString());

        result.putParcelable(login,constants);
        noteFragment.setArguments(result);

        FragmentTransaction fragmentTransaction = getFragmentTransaction();

        if (!checkLandOrient()){
            fragmentTransaction.replace(R.id.fragment_container,noteFragment);
        }
        else{
            getChildFragmentManager().beginTransaction().replace(R.id.note_container,noteFragment).commit();
        }
        transactionCommit(fragmentTransaction);
    }

    public Boolean checkLandOrient(){
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
