package com.example.foodorder.ui;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class CartEntity {

    @NonNull
    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(@NonNull String food_id) {
        this.food_id = food_id;
    }

    @PrimaryKey
    @NonNull
    String food_id;

    @ColumnInfo(name = "name") String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost_for_one() {
        return cost_for_one;
    }

    public void setCost_for_one(String cost_for_one) {
        this.cost_for_one = cost_for_one;
    }

    public CartEntity(@NonNull String food_id, String name, String cost_for_one) {
        this.food_id = food_id;
        this.name = name;
        this.cost_for_one = cost_for_one;
    }

    @ColumnInfo(name = "cost_for_one") String cost_for_one;

}
