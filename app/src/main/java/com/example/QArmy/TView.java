/*
 * TView
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

/**
 * Generic interface for a view.
 *
 * Note: This code comes directly from the MVC and Android Part 1 slides on
 * CMPUT 301 eClass by Abram Hindle with slides from Ken Wong.
 *
 * @param <M> the model class
 * @see TModel
 */
public interface TView<M> {

    /**
     * Update the view when the model state changes.
     * @param model The model whose state has changed
     */
    void update(M model);
}
