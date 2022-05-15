package com.example.foodorder.ui;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CartEntity.class},version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}
