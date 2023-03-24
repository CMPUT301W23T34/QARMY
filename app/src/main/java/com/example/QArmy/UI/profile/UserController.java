package com.example.QArmy.UI.profile;

import androidx.annotation.NonNull;

import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class UserController {
    private final SharedPrefsController prefsController;
    private final Database db;

    public UserController(SharedPrefsController prefs, Database db) {
        this.prefsController = prefs;
        this.db = db;
    }

    public void add(User user, RegistrationListener listener) {
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

    public void update(User user) {
        db.addUser(user, task -> {
            if (task.isSuccessful()) {
                prefsController.saveUser(user);
            }
        });
    }

    public User load() {
        return prefsController.loadUser();
    }
}
