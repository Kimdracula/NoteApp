package com.homework.homework_6.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.homework.homework_6.R;
import com.homework.homework_6.data.CardData;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.data.RecycleAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecycleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecycleListFragment extends Fragment implements Login {
CardData cardData;
MaterialButton addNoteButton;
int menuPosition;

    public static RecycleListFragment newInstance() {
        return new RecycleListFragment();
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
        DataSource dataSource = new DataSourceImp().init();
        initRecyclerView(recyclerView, dataSource);
        initItemAnimator(recyclerView);
        initDecorator(recyclerView);


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

        adapter.setItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NoteFragment noteFragment = new NoteFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(login,position);
               noteFragment.setArguments(bundle);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,noteFragment);
                transactionCommit(fragmentTransaction);
            }
        });

    }
    private void initItemAnimator(RecyclerView recyclerView) {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator(); itemAnimator.setAddDuration( 1000 ); itemAnimator.setRemoveDuration( 1000 );
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void initDecorator(RecyclerView recyclerView) {
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
