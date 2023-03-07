package com.example.QArmy;

import java.util.ArrayList;

public class TModel<V extends TView> {
    private ArrayList<V> views;

    public TModel() {
        views = new ArrayList<>();
    }

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }
}
