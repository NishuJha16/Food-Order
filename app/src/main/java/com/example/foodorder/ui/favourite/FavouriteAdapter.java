package com.example.foodorder.ui.favourite;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodorder.R;
import com.example.foodorder.RestaurantMenuList;
import com.example.foodorder.ui.FavouriteDatabase;
import com.example.foodorder.ui.FavouriteEntity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private Context context;
    private List<FavouriteEntity> all_restaurants;

    public FavouriteAdapter(Context context,List all_restaurants)
    {
        this.context=context;
        this.all_restaurants=all_restaurants;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_single_item,parent,false);
       ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavouriteAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(all_restaurants.get(position));
        final FavouriteEntity res=all_restaurants.get(position);
        holder.name.setText(res.getName());
        holder.cost.setText(res.getCost_for_one()+"/person");
        holder.rating.setText(res.getRating());
        Picasso.get().load(res.getImage_url()).error(R.mipmap.ic_launcher).into(holder.image);

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, RestaurantMenuList.class);
                intent.putExtra("res_id",res.getId());
                intent.putExtra("name",res.getName());
                context.startActivity(intent);
            }
        });
        final FavouriteEntity favouriteEntity=new FavouriteEntity(res.getId(),res.getName(),res.getRating(),res.getCost_for_one(),res.getImage_url());
        DBAsyncTask checkFav = (DBAsyncTask) new DBAsyncTask(context, favouriteEntity, 1).execute();
        boolean isFav = false;
        try {
            isFav = checkFav.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isFav) {
            holder.favourites.setImageResource(R.drawable.ic_favourites_yes);
        } else {
            holder.favourites.setImageResource(R.drawable.ic_favourites);
        }

        holder.favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!(new DBAsyncTask(context, favouriteEntity, 1).execute().get())) {

                        DBAsyncTask async = (DBAsyncTask) new DBAsyncTask(context, favouriteEntity, 2).execute();
                        Boolean result = async.get();
                        if (result) {
                            Toast.makeText(context, "Restaurant added to favourites", Toast.LENGTH_SHORT).show();
                            holder.favourites.setImageResource(R.drawable.ic_favourites_yes);
                        } else {
                            Toast.makeText(context, "Some error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        DBAsyncTask async = (DBAsyncTask) new DBAsyncTask(context, favouriteEntity, 3).execute();
                        Boolean result = async.get();

                        if (result){
                            Toast.makeText(context, "Restaurant removed from favourites", Toast.LENGTH_SHORT).show();
                            holder.favourites.setImageResource(R.drawable.ic_favourites);
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
        return all_restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView rating;
        TextView cost;
        ImageView image;
        RelativeLayout llContent;
        ImageButton favourites;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.txtRestName);
            rating=(TextView)itemView.findViewById(R.id.txtRestRating);
            cost=(TextView)itemView.findViewById(R.id.txtRestPrice);
            image=(ImageView)itemView.findViewById(R.id.imgRestImage);
            llContent=itemView.findViewById(R.id.llContent);
            favourites=itemView.findViewById(R.id.favourites);
        }
    }

    private static class DBAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Context context;
        FavouriteEntity favouriteEntity;
        int mode;
        FavouriteDatabase db;
        public DBAsyncTask(Context context, FavouriteEntity favouriteEntity, int mode)
        {
            this.context=context;
            this.favouriteEntity=favouriteEntity;
            this.mode=mode;

            db = Room.databaseBuilder(context, FavouriteDatabase.class, "favourite-db").build();
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
                    FavouriteEntity restaurant= db.favouriteDao().getRestaurantById(favouriteEntity.getId().toString());
                    db.close();
                    return restaurant != null;

                }

                case 2: {

                    // Save the book into DB as favourite
                    db.favouriteDao().insertRestaurant(favouriteEntity);
                    db.close();
                    return true;

                }

                case 3: {

                    // Remove the favourite book
                    db.favouriteDao().deleteRestaurant(favouriteEntity);
                    db.close();
                    return true;

                }
            }
            return false;
        }
    }
}
