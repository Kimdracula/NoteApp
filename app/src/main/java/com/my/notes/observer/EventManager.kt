package com.my.notes.observer

import com.my.notes.data.CardData

class EventManager {
    private val listeners: MutableList<EventListener>
    fun subscribe(watcher: EventListener) {
        listeners.add(watcher)
    }

    private fun unsubscribe(watcher: EventListener) {
        listeners.remove(watcher)
    }

    fun notify(cardData: CardData?) {
        for (watcher in listeners) {
            watcher.updateData(cardData)
            unsubscribe(watcher)
        }
    }

    init {
        listeners = ArrayList()
    }
}