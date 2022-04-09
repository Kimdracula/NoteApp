package com.my.notes.data

interface DataSource {
    fun init(cardDataResponse: CardDataResponse): DataSource
    fun getData(position: Int): CardData
    fun size(): Int
    fun changeData(position: Int, cardData: CardData)
    fun addData(cardData: CardData)
    fun deleteData(position: Int): CardData
}