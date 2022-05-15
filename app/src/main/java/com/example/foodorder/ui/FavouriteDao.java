package com.example.foodorder.ui;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Insert
    void insertRestaurant( FavouriteEntity favouriteEntity);

    @Delete
    void deleteRestaurant(FavouriteEntity favouriteEntity);

    @Query("SELECT * FROM favourite")
    List<FavouriteEntity> getAllRestaurant();

    @Query("SELECT * FROM favourite WHERE id = :id")
    FavouriteEntity getRestaurantById(String id);
}
