package com.example.foodorder;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView textviewName;
    TextView textviewPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        textviewName=(TextView)header.findViewById(R.id.textViewName);
        textviewPhone=(TextView)header.findViewById(R.id.textViewMobile);
        textviewName.setText(getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyusername",null));
        textviewPhone.setText("+91-"+getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keymobile",null));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_favourite,R.id.nav_history,R.id.nav_faq,R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        textviewName=(TextView)header.findViewById(R.id.textViewName);
        textviewPhone=(TextView)header.findViewById(R.id.textViewMobile);
        textviewName.setText(getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyusername",null));
        textviewPhone.setText("+91-"+getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keymobile",null));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_favourite, R.id.nav_history, R.id.nav_faq, R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        textviewName=(TextView)header.findViewById(R.id.textViewName);
        textviewPhone=(TextView)header.findViewById(R.id.textViewMobile);
        textviewName.setText(getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyusername",null));
        textviewPhone.setText("+91-"+getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keymobile",null));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_favourite,R.id.nav_history,R.id.nav_faq,R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        textviewName=(TextView)header.findViewById(R.id.textViewName);
        textviewPhone=(TextView)header.findViewById(R.id.textViewMobile);
        textviewName.setText(getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyusername",null));
        textviewPhone.setText("+91-"+getApplicationContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keymobile",null));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_favourite,R.id.nav_history,R.id.nav_faq,R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}