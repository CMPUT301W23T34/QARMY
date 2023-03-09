/*
 * QueryListener
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.db;

import com.example.QArmy.model.Entity;

import java.util.List;

/**
 * Provide callbacks when a query has been performed.
 * @param <T> The entity returned from the query
 * @author Kai Luedemann
 * @version 1.0
 */
public interface QueryListener<T extends Entity> {

    /**
     * Callback when query is successful
     * @param data The data returned from the query
     */
    void onSuccess(List<T> data);

    /**
     * Callback when query is unsuccessful
     * @param e The exception produced
     */
    void onFailure(Exception e);
}
