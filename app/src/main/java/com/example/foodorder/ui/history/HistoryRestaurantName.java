package com.example.foodorder.ui.history;

public class HistoryRestaurantName {

    String order_id,restaurant_name,total_cost,order_placed_at;

    public HistoryRestaurantName(String order_id, String restaurant_name, String total_cost, String order_placed_at) {
        this.order_id = order_id;
        this.restaurant_name = restaurant_name;
        this.total_cost = total_cost;
        this.order_placed_at = order_placed_at;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getOrder_placed_at() {
        return order_placed_at;
    }

    public void setOrder_placed_at(String order_placed_at) {
        this.order_placed_at = order_placed_at;
    }
}
