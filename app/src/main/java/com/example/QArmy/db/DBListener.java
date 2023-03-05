package com.example.QArmy.db;

import com.example.QArmy.Model;

import java.util.List;

public interface DBListener<T extends Model> {
    void onSuccess();
    void onFailure(Exception e);
    void onListQuery(List<T> data);
    void onQuery(T data);
}
