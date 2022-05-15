package com.example.foodorder.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryRestaurantNameAdapter extends RecyclerView.Adapter<HistoryRestaurantNameAdapter.ViewHolder>  {
    private Context context;
    private List<HistoryRestaurantName> restaurant_list;
    private ArrayList<ArrayList<HistoryFood>> food_list;
    RecyclerView.LayoutManager layoutManager2;

    public HistoryRestaurantNameAdapter(Context context, List<HistoryRestaurantName> restaurant_list, ArrayList<ArrayList<HistoryFood>> food_list) {
        this.context = context;
        this.restaurant_list = restaurant_list;
        this.food_list=food_list;
    }

    @NonNull
    @Override
    public HistoryRestaurantNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_res_single_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRestaurantNameAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(restaurant_list.get(position));
        layoutManager2=new LinearLayoutManager(context);
        HistoryFoodNameAdapter historyFoodNameAdapter=new HistoryFoodNameAdapter(context,food_list.get(position));
        holder.listView2.setAdapter(historyFoodNameAdapter);
        holder.listView2.setHasFixedSize(true);
        holder.listView2.setLayoutManager(layoutManager2);
        final HistoryRestaurantName historyRestaurantName=restaurant_list.get(position);
        holder.txtItemName.setText(historyRestaurantName.getRestaurant_name());
        holder.txtDate.setText(historyRestaurantName.getOrder_placed_at());
    }

    @Override
    public int getItemCount() {
        return restaurant_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtItemName;
        TextView txtDate;
        RecyclerView listView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName=itemView.findViewById(R.id.txtItemName);
            txtDate=itemView.findViewById(R.id.txtDate);
            listView2=itemView.findViewById(R.id.listView2);
        }
    }
}
