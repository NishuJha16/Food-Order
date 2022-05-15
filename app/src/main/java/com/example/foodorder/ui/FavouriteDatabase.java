package com.example.foodorder.ui;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavouriteEntity.class},version = 1)
public abstract class FavouriteDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();
}
