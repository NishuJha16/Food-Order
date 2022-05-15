package com.example.foodorder.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.CartAdapter;
import com.example.foodorder.R;
import com.example.foodorder.ui.CartEntity;
import com.example.foodorder.ui.RestaurantAdapter;

import java.util.List;

public class HistoryFoodNameAdapter extends RecyclerView.Adapter<HistoryFoodNameAdapter.ViewHolder> {
    private Context context;
    private List<HistoryFood> food_list;

    public HistoryFoodNameAdapter(Context context, List<HistoryFood> food_list) {
        this.context = context;
        this.food_list = food_list;
    }

    @NonNull
    @Override
    public HistoryFoodNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_single_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryFoodNameAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(food_list.get(position));
        final HistoryFood historyFood=food_list.get(position);
        holder.txtItemName.setText(historyFood.getName());
        holder.txtItemPrice.setText("Rs."+historyFood.getCost());
    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtItemName;
        TextView txtItemPrice;
        ImageButton remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName=itemView.findViewById(R.id.txtItemName);
            txtItemPrice=itemView.findViewById(R.id.txtItemPrice);
            remove=itemView.findViewById(R.id.remove);
        }
    }
}

