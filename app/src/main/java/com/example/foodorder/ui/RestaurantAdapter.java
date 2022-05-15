package com.example.foodorder.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.fonts.FontVariationAxis;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import com.example.foodorder.HomeActivity;
import com.example.foodorder.R;
import com.example.foodorder.RestaurantMenuList;
import com.example.foodorder.Restaurants;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RestaurantAdapter  extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>  {
     Context context;
     public static List<Restaurants> all_restaurants;
     ArrayList<String> address=new ArrayList<String>();
    ArrayList<String> offers=new ArrayList<String>();

    public RestaurantAdapter(Context context, List all_restaurants) {
        this.context = context;
        this.all_restaurants = all_restaurants;
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(all_restaurants.get(position));
        address.add("Telco,Jamshedpur");
        address.add("Sakchi,Jamshedpur");
        address.add("Bistupur,Jamshedpur");
        address.add("Kadma,Jamshedpur");
        address.add("Sonari,Jamshedpur");
        address.add("Bagbera,Jamshedpur");
        address.add("Jugsalai,Jamshedpur");
        address.add("Burmamines,Jamshedpur");
        address.add("Golmuri,Jamshedpur");
        address.add("Birsanagar,Jamshedpur");
        address.add("Govindpur,Jamshedpur");
        address.add("Baridih,Jamshedpur");
        address.add("Nildih,Jamshedpur");
        address.add("Jemco,Jamshedpur");
        address.add("Adityapur,Jharkhand");
        address.add("Gamharia,Jharkhand");
        address.add("Asansol,West Bengal");

        offers.add("30% off use code OFF30");
        offers.add("20% off use code OFF20");
        offers.add("Free Delivery use code FREEDEL");
        offers.add("50% off use code OFF50");
        offers.add("Buy 2 get 1 free use code B21F");
        offers.add("30% off use code OFF30");
        offers.add("20% off use code OFF20");
        offers.add("Free Delivery use code FREEDEL");
        offers.add("50% off use code OFF50");
        offers.add("Buy 2 get 1 free use code B21F");
        offers.add("30% off use code OFF30");
        offers.add("20% off use code OFF20");
        offers.add("Free Delivery use code FREEDEL");
        offers.add("50% off use code OFF50");
        offers.add("Buy 2 get 1 free use code B21F");
        offers.add("50% off use code OFF50");
        offers.add("Free Delivery use code FREEDEL");

        final Restaurants res = all_restaurants.get(position);
        holder.name.setText(res.getName());
        holder.cost.setText(res.getCost_for_one() + "/person");
        holder.rating.setText(res.getRating());
        Picasso.get().load(res.getImage_url()).error(R.mipmap.ic_launcher).into(holder.image);

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RestaurantMenuList.class);
                intent.putExtra("res_id", res.getId());
                intent.putExtra("name", res.getName());
                intent.putExtra("address",address.get(Integer.parseInt(res.getId())));
                intent.putExtra("offers",offers.get(Integer.parseInt(res.getId())));

                context.startActivity(intent);
            }
        });
        final FavouriteEntity favouriteEntity = new FavouriteEntity(res.getId(), res.getName(), res.getRating(), res.getCost_for_one(), res.getImage_url());
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

                        if (result) {
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

    public  void setFilter(List<Restaurants> FilteredDataList)
    {
        all_restaurants=FilteredDataList;
        notifyDataSetChanged();
    }

   /* private Filter fRecords;


    @Override
    public Filter getFilter() {
        if (fRecords == null) {
            fRecords = new RecordFilter();
        }
        return fRecords;
    }


    private class RecordFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = all_restaurants;
                results.count = all_restaurants.size();
            } else {
                ArrayList<Restaurants> fRecords = new ArrayList<Restaurants>();
                for (Restaurants s : all_restaurants) {
                    if (s.getName().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                        fRecords.add(s);
                    }
                }
                results.values = fRecords;
                results.count = fRecords.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            all_restaurants = (ArrayList<Restaurants>) results.values;
            notifyDataSetChanged();

        }
    }  */

    public static class ViewHolder extends RecyclerView.ViewHolder{
       
        public TextView name;
        public TextView rating;
        public  TextView cost;
        public ImageView image;
        public RelativeLayout llContent;
        public ImageButton favourites;
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

   public static class DBAsyncTask extends AsyncTask<Void, Void, Boolean> {
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
                    FavouriteEntity restaurant= db.favouriteDao().getRestaurantById(favouriteEntity.id.toString());
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
