package com.example.QArmy.db;

import com.example.QArmy.model.Model;

import java.util.List;

public interface DBListener<T extends Model> {
    void onSuccess();
    void onFailure(Exception e);
    void onListQuery(List<T> data);
    void onQuery(T data);
    void getCount(long data);
}
