package com.example.foodorder.ui;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insertCart( CartEntity cartEntity);

    @Delete
    void deleteCart(CartEntity cartEntity);

    @Query("SELECT * FROM cart")
    List<CartEntity> getAllCart();

    @Query("DELETE FROM cart")
    void deleteAll();

    @Query("SELECT food_id FROM cart")
    List<String> getAllFoodId();

    @Query("SELECT * FROM cart WHERE food_id = :id")
    CartEntity getCartById(String id);
}
