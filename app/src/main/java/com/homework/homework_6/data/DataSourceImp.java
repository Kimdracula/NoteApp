package com.homework.homework_6.data;

import com.homework.homework_6.R;

import java.util.ArrayList;
import java.util.Calendar;

public class DataSourceImp implements DataSource {
    private ArrayList <CardData> notes;
    public DataSourceImp init(){
    notes = new ArrayList<>();
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
    public void addData(CardData cardData) {
        notes.add(cardData);
    }

    @Override
    public CardData deleteData(int position) {
        return notes.remove(position);
    }

    @Override
    public ArrayList<CardData> list() {
        return notes;
    }
}
