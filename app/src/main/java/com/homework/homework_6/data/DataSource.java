package com.homework.homework_6.data;

public interface DataSource {

        CardData getData(int position);
        int size();
        void changeData(int position, CardData cardData);
        void addData(CardData cardData);
        CardData deleteData(int position);

    }

