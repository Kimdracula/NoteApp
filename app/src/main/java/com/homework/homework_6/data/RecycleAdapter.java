package com.homework.homework_6.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.homework_6.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.NoteViewHolder> {
    private DataSource notes;
    private OnItemClickListener itemClickListener;
    private int lastPosition = -1;
    private Fragment fragment;
    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }




    public RecycleAdapter(DataSource notes, Fragment fragment) {
        this.notes = notes;
        this.fragment = fragment;
    }



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item,parent,false);
        return new NoteViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
holder.title.setText(notes.getData(position).getHeader());
holder.description.setText(notes.getData(position).getDescription());
holder.imageView.setImageResource(notes.getData(position).getPicture());
holder.date.setText(new SimpleDateFormat("dd.MMM.yyyy").format(notes.getData(position).getDate()));

setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private ImageView imageView;
        private TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewHeader);
            description = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.textViewDate);
            registerContextMenu(itemView);
            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(view, getAdapterPosition());
                }
            });
           itemView.setOnLongClickListener(view -> {
               itemView.showContextMenu();
               menuPosition=getLayoutPosition();
               return true;
           });

        }

        private void registerContextMenu(View itemView) {
            if (fragment!=null){
                fragment.registerForContextMenu(itemView);}
        }

    }

}
