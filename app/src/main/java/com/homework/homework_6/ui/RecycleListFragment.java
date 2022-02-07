package com.homework.homework_6.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.homework.homework_6.MainActivity;
import com.homework.homework_6.R;
import com.homework.homework_6.data.CardData;
import com.homework.homework_6.data.DataSource;
import com.homework.homework_6.data.DataSourceImp;
import com.homework.homework_6.data.Login;
import com.homework.homework_6.data.RecycleAdapter;
import com.homework.homework_6.observer.EventListener;
import com.homework.homework_6.observer.EventManager;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RecycleListFragment extends Fragment implements Login {
    private RecycleAdapter adapter;
    private  DataSource dataSource;
    private EventManager eventManager;
   static final String KEY = "key";
    SharedPreferences sharedPref = null;

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

if (dataSource.size() ==0) {
    String savedNote = sharedPref.getString(KEY, null);
    if (savedNote == null) {
        Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
    } else {
        try {
            Type type = new TypeToken<ArrayList<CardData>>() {
            }.getType();
            dataSource.addAll(new GsonBuilder().create().fromJson(savedNote, type));
        } catch (JsonSyntaxException e) {
            Toast.makeText(getContext(), "Ошибка трансформации", Toast.LENGTH_SHORT).show();
        }

    }
}
            }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        eventManager = activity.getEventManager();
        sharedPref = activity.getSharedPref();
    }

    @Override
    public void onDetach() {
        eventManager = null;
        super.onDetach();
    }

    @Override
    public void onStop() {
        String jsonNotes = new GsonBuilder().create().toJson(dataSource);
        sharedPref.edit().putString(KEY, jsonNotes).apply();
        super.onStop();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch(item.getItemId()) {
            case R.id.context_change:
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,NoteFragment
                        .newInstance(dataSource.getData(position)));
                transactionCommit(fragmentTransaction);
                eventManager.subscribe(cardData -> {
                    dataSource.changeData(position, cardData);
                    adapter.notifyItemChanged(position);
                });
                return true;
            case R.id.context_delete:
                dataSource.deleteData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }

        return super.onContextItemSelected(item);

    }

    private void initViews(@NonNull View view) {
        MaterialButton addNoteButton = view.findViewById(R.id.buttonAddNote);
        addNoteButton.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,NoteFragment.newInstance());
            transactionCommit(fragmentTransaction);
            eventManager.subscribe(new EventListener() {
                @Override
                public void updateData(CardData cardData) {
                    dataSource.addData(cardData);
                    adapter.notifyItemInserted(dataSource.size()-1);

                }
            });
        });
    }


    private void initRecyclerView(RecyclerView recyclerView, DataSource data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleAdapter(data,this);
        recyclerView.setAdapter(adapter);
         adapter.setItemClickListener((view, position) -> {
             FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,NoteFragment
                     .newInstance(dataSource.getData(position)));
             transactionCommit(fragmentTransaction);
                 eventManager.subscribe(cardData -> {
                 dataSource.changeData(position, cardData);
                 adapter.notifyItemChanged(position);
             });
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
        MaterialTextView textViewDate = view.findViewById(R.id.textViewDateRL);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd-EEEE-yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        textViewDate.setText(date);
    }



    }
