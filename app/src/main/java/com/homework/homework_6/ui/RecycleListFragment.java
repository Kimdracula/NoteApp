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

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.homework.homework_6.R;
import com.homework.homework_6.data.DataSource;
import com.homework.homework_6.data.DataSourceImp;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.data.RecycleAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecycleListFragment extends Fragment implements Login {
MaterialButton addNoteButton;
MaterialTextView textViewDate;
    RecycleAdapter adapter;
    DataSource dataSource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new DataSourceImp().init();
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
        initRecyclerView(recyclerView, dataSource);
        initItemAnimator(recyclerView);
        initDecorator(recyclerView);
        initDate(view);
        initViews(view);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.context_change:
                // Do some stuff
                return true;
            case R.id.context_delete:
                dataSource.deleteData(adapter.getMenuPosition());
                adapter.notifyItemRemoved(adapter.getMenuPosition());
                return true;
        }

        return super.onContextItemSelected(item);

    }

    private void initViews(@NonNull View view) {
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
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleAdapter(data,this);
        recyclerView.setAdapter(adapter);
         adapter.setItemClickListener((view, position) -> {
            NoteFragment noteFragment = new NoteFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(login,position);
           noteFragment.setArguments(bundle);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,noteFragment);
            transactionCommit(fragmentTransaction);
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

    private void initDate(View view) {
        textViewDate = view.findViewById(R.id.textViewDateRL);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd-EEEE-yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        textViewDate.setText(date);
    }



    }
