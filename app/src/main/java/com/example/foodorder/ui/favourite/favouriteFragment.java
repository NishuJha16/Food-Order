package com.example.foodorder.ui.favourite;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodorder.R;
import com.example.foodorder.ui.FavouriteDatabase;
import com.example.foodorder.ui.FavouriteEntity;
import com.example.foodorder.ui.RestaurantAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class favouriteFragment extends Fragment {

    private favouriteViewModel favouriteViewModel;
    RecyclerView.Adapter mAdapter;
    RecyclerView listView;
    Context mContext;
    RelativeLayout progressLayout;
    RecyclerView.LayoutManager layoutManager;
    List<FavouriteEntity> dbFavourite;
    Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favouriteViewModel =
                ViewModelProviders.of(this).get(favouriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        progressLayout = root.findViewById(R.id.progressLayout);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressLayout.setVisibility(View.VISIBLE);
        listView=root.findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());

        try {
            dbFavourite= new RetriveFavourite(mContext).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getContext() != null) {
            progressLayout.setVisibility(View.GONE);
            mAdapter = new FavouriteAdapter(getContext(), dbFavourite);
            listView.setAdapter(mAdapter);
            listView.setLayoutManager(layoutManager);
        }

        return root;
    }
    public static class RetriveFavourite extends AsyncTask<Void,Void,List<FavouriteEntity>>{
        Context context;
        FavouriteDatabase db;
        public RetriveFavourite(Context context)
        {
            this.context=context;
        }

        @Override
        protected List<FavouriteEntity> doInBackground(Void... voids) {
            db = Room.databaseBuilder(context, FavouriteDatabase.class, "favourite-db").build();
            return db.favouriteDao().getAllRestaurant();
        }
    }
}