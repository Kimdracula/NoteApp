package com.homework.homework_6.ui;

import android.content.res.Resources;

import com.homework.homework_6.data.CardData;

import java.util.ArrayList;

public class DataSourceImp implements DataSource {
    private ArrayList <CardData> notes;
    private Resources resources;

    public DataSourceImp(Resources resources) {
        this.resources = resources;
        notes = new ArrayList<>();
    }

public DataSourceImp init(){

for (int i =0; i<30; i++){
   notes.add(new CardData("Заметка "+i, "Описание "+1 ));
}
return this;
}


    @Override
    public CardData getData(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }
}
