/*
 * Entity
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 * - Google Developers, 2023-03-03,
 * https://firebase.google.com/docs/firestore/query-data/index-overview#index_types, Firebase
 */

package com.example.QArmy.model;

/**
 * Abstract class for entities that exist in the database.
 * @author Kai Luedemann
 * @version 1.0
 */
public abstract class Entity {

    public static final String ID_FIELD = "__name__";

    public abstract String getID();
}
