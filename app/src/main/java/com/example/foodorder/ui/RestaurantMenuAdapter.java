package com.example.foodorder.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodorder.CartAdapter;
import com.example.foodorder.CartDb;
import com.example.foodorder.R;
import com.example.foodorder.RestaurantMenuItems;
import com.example.foodorder.RestaurantMenuList;
import com.example.foodorder.Restaurants;
import com.example.foodorder.ui.favourite.FavouriteAdapter;
import com.example.foodorder.ui.home.HomeFragment;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder> {
    private Context context;
    private List<RestaurantMenuItems> all_item;
    public static int a=0;


    public RestaurantMenuAdapter(Context context, List all_item)
    {
        this.context=context;
        this.all_item=all_item;

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        DBAsyncTask DeleteItem = (DBAsyncTask) new DBAsyncTask(context, 4).execute();
        try {
            if(DeleteItem.get())
            {
                //Toast.makeText(context,"all deleted",Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RestaurantMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_single_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantMenuAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(all_item.get(position));
        final RestaurantMenuItems res=all_item.get(position);
        holder.Itemname.setText(res.getName());
        holder.cost.setText("Rs."+res.getCost_for_one());
        holder.sno.setText(res.getSno());
        final int[] c = {0};

        final CartEntity cartEntity=new CartEntity(res.getId(),res.getName(),res.getCost_for_one());
        DBAsyncTask checkCart = (DBAsyncTask) new DBAsyncTask(context, cartEntity, 1).execute();

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c[0] ==0) {
                    holder.addItem.setText("Remove");
                    holder.addItem.setBackgroundColor(Color.YELLOW);
                    //CartDb food_item = new CartDb(res.getRestaurant_id(), res.getName(), res.getCost_for_one());
                    c[0] =1;
                    a++;
                }
                else{
                    holder.addItem.setText("Add");
                    holder.addItem.setBackgroundColor(Color.parseColor("#F8685D"));
                    c[0] =0;
                    a--;
                }
                ((RestaurantMenuList)context).updateCart();
                try {
                    if (!(new DBAsyncTask(context, cartEntity, 1).execute().get())) {

                        DBAsyncTask async = (DBAsyncTask) new DBAsyncTask(context, cartEntity, 2).execute();
                        Boolean result = async.get();
                        if (result) {
                            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show();
                            holder.addItem.setText("Remove");
                            holder.addItem.setBackgroundColor(Color.YELLOW);
                            //CartDb food_item = new CartDb(res.getRestaurant_id(), res.getName(), res.getCost_for_one());

                        } else {
                            Toast.makeText(context, "Some error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                      DBAsyncTask async = (DBAsyncTask) new DBAsyncTask(context, cartEntity, 3).execute();
                        Boolean result = async.get();

                        if (result){
                            Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                            holder.addItem.setText("Add");
                            holder.addItem.setBackgroundColor(Color.parseColor("#F8685D"));

                        } else {
                            Toast.makeText(context, "Some error occurred!", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return all_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Itemname;
        TextView cost;
        TextView sno;
        Button addItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Itemname=(TextView)itemView.findViewById(R.id.txtItemName);
            cost=(TextView)itemView.findViewById(R.id.txtItemPrice);
            sno=(TextView)itemView.findViewById(R.id.sno);
            addItem=(Button)itemView.findViewById(R.id.addItem);
        }
    }

    private static class DBAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Context context;
        CartEntity cartEntity;
        int mode;
        CartDatabase db;
        public DBAsyncTask(Context context, CartEntity cartEntity, int mode)
        {
            this.context=context;
            this.cartEntity=cartEntity;
            this.mode=mode;

            db = Room.databaseBuilder(context, CartDatabase.class, "cart-db").build();
        }
        public DBAsyncTask(Context context, int mode)
        {
            this.context=context;
            this.mode=mode;

            db = Room.databaseBuilder(context, CartDatabase.class, "cart-db").build();
        }
        /*
        Mode 1 -> Check DB if the book is favourite or not
        Mode 2 -> Save the book into DB as favourite
        Mode 3 -> Remove the favourite book
        * */
        @Override
        protected Boolean doInBackground(Void... voids) {
            switch (mode) {

                case 1: {

                    // Check DB if the book is favourite or not
                    CartEntity cart= db.cartDao().getCartById(cartEntity.getFood_id().toString());
                    db.close();
                    return cart != null;

                }

                case 2: {

                    // Save the book into DB as favourite
                    db.cartDao().insertCart(cartEntity);
                    db.close();
                    return true;

                }

                case 3: {

                    // Remove the favourite book
                    db.cartDao().deleteCart(cartEntity);
                    db.close();
                    return true;

                }
                case 4: {

                    // Remove the favourite book
                    db.cartDao().deleteAll();
                    db.close();
                    return true;

                }
            }
            return false;
        }
    }
}
