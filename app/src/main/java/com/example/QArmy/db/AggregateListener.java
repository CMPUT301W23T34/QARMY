/*
 * AggregateListener
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

/**
 * Listen for the result of an aggregate count query.
 * @author Kai Luedemann
 * @version 1.0
 */
public interface AggregateListener {

    /**
     * Callback for successful query
     * @param count The count returned from the query
     */
    void onSuccess(long count);

    /**
     * Callback for unsuccessful query
     * @param e The exception produced by the query
     */
    void onFailure(Exception e);
}
