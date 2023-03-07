package com.example.QArmy.db;

import com.example.QArmy.model.Entity;

import java.util.List;

public interface QueryListener<T extends Entity> {
    void onSuccess(List<T> data);
    void onFailure(Exception e);
}
