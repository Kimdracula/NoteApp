package com.homework.homework_6.observer;

import com.homework.homework_6.data.CardData;
import com.homework.homework_6.observer.EventListener;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<EventListener> listeners;

    public EventManager() {
        listeners = new ArrayList<>();
    }


    public void subscribe(EventListener watcher) {
        listeners.add(watcher);
    }

    public void unsubscribe(EventListener watcher) {
        listeners.remove(watcher);
    }

    public void notify(CardData cardData) {
        for (EventListener watcher : listeners) {
            watcher.updateData(cardData);
            unsubscribe(watcher);
        }
    }
}