package com.example.QArmy.db;

public interface AggregateListener {
    void onSuccess(long count);
    void onFailure(Exception e);
}
