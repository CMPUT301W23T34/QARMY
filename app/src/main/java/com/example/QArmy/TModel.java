/*
 * TModel
 *
 * Version: 1.0
 *
 * Date: 2023-03-08
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 * - Abram Hindle, MVC and Android, https://eclass.srv.ualberta.ca/pluginfile.php/9087890/mod_resource/content/1/03-MVC-Android-PartI.pdf, eClass
 * - Abram Hindle, FillerCreepForAndroid, https://github.com/abramhindle/FillerCreepForAndroid, GitHub
 */

package com.example.QArmy;

import java.util.ArrayList;

/**
 * Generic class for a model.
 *
 * Note: This code comes directly from the MVC and Android Part 1 slides on
 * CMPUT 301 eClass by Abram Hindle with slides from Ken Wong.
 *
 * @param <V> The view class that observes this model
 */
public class TModel<V extends TView> {
    private final ArrayList<V> views;

    /**
     * Initialize the model with an empty list of observers.
     */
    public TModel() {
        views = new ArrayList<>();
    }

    /**
     * Add a view to the list of observers.
     * @param view The view to observe the model
     */
    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    /**
     * Remove a view from the list of observers.
     * @param view The view to remove
     */
    public void deleteView(V view) {
        views.remove(view);
    }

    /**
     * Notify views when the model state changes by calling update()
     */
    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }
}
