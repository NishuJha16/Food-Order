package com.example.foodorder;

public class RestaurantMenuItems {
    String id,name,cost_for_one,restaurant_id,sno;

    public RestaurantMenuItems(String id, String name, String cost_for_one, String restaurant_id,String sno) {
        this.id = id;
        this.name = name;
        this.cost_for_one = cost_for_one;
        this.restaurant_id = restaurant_id;
        this.sno=sno;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
