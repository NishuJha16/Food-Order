package com.example.foodorder;

public class CartDb {
    String res_id,name,cost;

    public CartDb(String res_id, String name, String cost) {
        this.res_id = res_id;
        this.name = name;
        this.cost = cost;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
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
}
