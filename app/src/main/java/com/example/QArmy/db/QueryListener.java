package com.example.QArmy.db;

import com.example.QArmy.model.Model;

import java.util.List;

public interface QueryListener<T extends Model> {
    void onSuccess(List<T> data);
    void onFailure(Exception e);
}
