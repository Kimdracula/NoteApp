package com.homework.homework_6.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.homework.homework_6.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecycleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecycleListFragment extends Fragment {

MaterialButton addNoteButton;

    public RecycleListFragment() {
        // Required empty public constructor
    }


    public static RecycleListFragment newInstance(String param1, String param2) {
        RecycleListFragment fragment = new RecycleListFragment();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycle_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_list);
        DataSource data = new DataSourceImp(getResources()).init();
        initRecyclerView(recyclerView, data);


        addNoteButton = view.findViewById(R.id.buttonAddNote);
        addNoteButton.setOnClickListener(view1 -> {

            AddFragment addFragment = new AddFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container,addFragment);
            transactionCommit(fragmentTransaction);

        });

    }

    private void initRecyclerView(RecyclerView recyclerView, DataSource data) {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        final RecycleAdapter adapter = new RecycleAdapter(data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL);
itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
recyclerView.addItemDecoration(itemDecoration);
    }

    private void transactionCommit(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
    }
