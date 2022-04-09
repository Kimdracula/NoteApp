package com.my.notes.observer

import com.my.notes.data.CardData

interface EventListener {
    fun updateData(cardData: CardData?)
}