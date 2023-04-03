/*
 * UserController
 *
 * Version: 1.0
 *
 * Date: 2023-04-02
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.UI.profile;

import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.User;

import java.util.List;

/**
 * Maintain the state of the current app user.
 * @version 1.0
 * @author Kai Luedemann
 */
public class UserController {
    private final SharedPrefsController prefsController;
    private final Database db;

    /**
     * Construct the controller.
     * @param prefs The controller to access shared preferences
     * @param db The database to upload user to
     */
    public UserController(SharedPrefsController prefs, Database db) {
        this.prefsController = prefs;
        this.db = db;
    }

    /**
     * Create a new user and store locally and remotely.
     * @param user The user to store
     * @param listener The listener to callback when complete
     */
    public void add(User user, RegistrationListener listener) {
        if (user.getName().contains("/")) {
            listener.onError(new RuntimeException("Invalid username"));
            return;
        }
        db.getUser(user, new QueryListener<User>() {
            @Override
            public void onSuccess(List<User> data) {
                if (data.size() > 0) {
                    listener.onExists();
                } else {
                    update(user);
                    listener.onAdded(user);
                }
            }

            @Override
            public void onFailure(Exception e) {
                listener.onError(e);
            }
        });
    }

    /**
     * Update the user in the database.
     * @param user The new user to update with.
     */
    public void update(User user) {
        db.addUser(user, task -> {
            if (task.isSuccessful()) {
                prefsController.saveUser(user);
            }
        });
    }

    /**
     * Load user from shared preferences.
     * @return The user.
     */
    public User load() {
        return prefsController.loadUser();
    }
}
