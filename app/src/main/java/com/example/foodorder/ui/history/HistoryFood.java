package com.example.foodorder.ui.history;

public class HistoryFood {
    String food_item_id,name,cost;

    public String getFood_item_id() {
        return food_item_id;
    }

    public void setFood_item_id(String food_item_id) {
        this.food_item_id = food_item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public HistoryFood(String food_item_id, String name, String cost) {
        this.food_item_id = food_item_id;
        this.name = name;
        this.cost = cost;
    }
}
