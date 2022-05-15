package com.example.foodorder.ui;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite")
public class FavouriteEntity {

    @PrimaryKey
    @NonNull
    public
    String id;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCost_for_one() {
        return cost_for_one;
    }

    public void setCost_for_one(String cost_for_one) {
        this.cost_for_one = cost_for_one;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @ColumnInfo(name = "name") String name;
    @ColumnInfo(name = "rating") String rating;
    @ColumnInfo(name = "cost_for_one")  String cost_for_one;
    @ColumnInfo(name = "image_url") String image_url;

    public FavouriteEntity(String id, String name, String rating, String cost_for_one, String image_url) {
        this.id=id;
        this.name=name;
        this.rating=rating;
        this.cost_for_one=cost_for_one;
        this.image_url=image_url;
    }
}

