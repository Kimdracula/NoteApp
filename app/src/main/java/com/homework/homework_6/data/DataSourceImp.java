package com.homework.homework_6.data;

import java.util.ArrayList;

public class DataSourceImp implements DataSource {
    private ArrayList <CardData> notes;



    public DataSourceImp init(){
    notes = new ArrayList<>();

for (int i =0; i<30; i++){
   notes.add(new CardData("Заметка "+i, "Описание "+i));
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

    @Override
    public void changeData(int position, CardData cardData) {
        notes.set(position, cardData);
    }

    @Override
    public CardData deleteData(int position) {
        return notes.remove(position);
    }
}
