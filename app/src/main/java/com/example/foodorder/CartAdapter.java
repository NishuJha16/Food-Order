package com.example.foodorder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.ui.CartEntity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartEntity> cart_item;
    static int sum=0;

    public CartAdapter(Context context,List cart_item)
    {
        this.context=context;
        this.cart_item=cart_item;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_single_item,parent,false);
       ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(cart_item.get(position));
        final CartEntity res=cart_item.get(position);
        holder.txtItemName.setText(res.getName());
        holder.txtItemPrice.setText("Rs."+res.getCost_for_one());
        sum+=Integer.parseInt(res.getCost_for_one());
    }

    @Override
    public int getItemCount() {
        return cart_item.size();
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
