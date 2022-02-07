package com.homework.homework_6.data;

import java.util.ArrayList;

public interface DataSource {

        CardData getData(int position);
        int size();
        void changeData(int position, CardData cardData);
        void addData(CardData cardData);
        CardData deleteData(int position);
        void addAll(ArrayList<CardData>arrayList);


    }

