package com.my.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.my.notes.MainActivity;
import com.my.notes.R;
import com.my.notes.data.DataSource;
import com.my.notes.data.DataSourceImp;
import com.my.notes.data.RecycleAdapter;
import com.my.notes.observer.EventManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecycleListFragment extends Fragment {
    private RecycleAdapter adapter;
    private  DataSource dataSource;
    private EventManager eventManager;
    String collectionPath = "NOTES";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new RecycleAdapter(this);
        dataSource = new DataSourceImp().init(cardsData -> adapter.notifyDataSetChanged());
        return inflater.inflate(R.layout.recycle_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_list);
        initItemAnimator(recyclerView);
        initDecorator(recyclerView);
        initDate(view);
        adapter.setDataSource(dataSource);
        initRecyclerView(recyclerView);
        initViews(view);
}



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        eventManager = activity.getEventManager();
    }

    @Override
    public void onDetach() {
        eventManager = null;
        super.onDetach();
    }

    @Override
    public void onStop() {
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
                Bundle bundle=new Bundle();
                bundle.putInt("position_to_delete",position);
                NoteDialogFragment noteDialogFragment = new NoteDialogFragment();
                noteDialogFragment.setArguments(bundle);

                FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
                firebaseFirestore.collection(collectionPath).document(dataSource.getData(position).getId()).delete()
                        .addOnSuccessListener(unused -> Log.d("Success TAG", "Its ok with reading Firebase ")
                        ).addOnFailureListener(e -> Log.d("Error TAG", "get failed with reading Firebase -"+e));
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
            eventManager.subscribe(cardData -> {
                dataSource.addData(cardData);
                adapter.notifyItemInserted(dataSource.size()-1);

            });
        });
    }


    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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