package com.example.foodorder.ui;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorder.R;
import com.example.foodorder.SharedPrefManager;

public class profile extends Fragment {

    private ProfileViewModel mViewModel;
    TextView username;
    TextView mobilenumber;
    TextView email;
    TextView address;
    Activity referenceActivity;
    View parentHolder;
    ImageView profilepic;
    public static profile newInstance() {
        return new profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        referenceActivity=getActivity();
        parentHolder=inflater.inflate(R.layout.profile_fragment, container, false);
        username=parentHolder.findViewById(R.id.username);
        profilepic=parentHolder.findViewById(R.id.profilepic);
        profilepic.setImageResource(R.drawable.ic_user);
        mobilenumber=parentHolder.findViewById(R.id.mobilenumber);
        email=parentHolder.findViewById(R.id.email);
        address=parentHolder.findViewById(R.id.address);
        username.setText((CharSequence) getContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyusername",null));
        mobilenumber.setText((CharSequence) getContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keymobile",null));
        email.setText((CharSequence) getContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyemail",null));
        address.setText((CharSequence) getContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyaddress",null));
        return parentHolder;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel

    }

}