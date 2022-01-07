package com.homework.homework_6.ui;

import android.content.res.Resources;

import com.homework.homework_6.Data;

import java.util.ArrayList;
import java.util.List;

public class DataSourceImp implements DataSource {
    private ArrayList <Data> notes;
    private Resources resources;

    public DataSourceImp(Resources resources) {
        this.resources = resources;
        notes = new ArrayList<>();
    }

public DataSourceImp init(){

for (int i =0; i<30; i++){
   notes.add(new Data("Заметка "+i, "Описание "+1 ));
}
return this;
}


    @Override
    public Data getData(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }
}
