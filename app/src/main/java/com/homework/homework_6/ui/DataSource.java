package com.homework.homework_6.ui;

import com.homework.homework_6.data.CardData;

public interface DataSource {

        CardData getData(int position);
        int size();
    }

