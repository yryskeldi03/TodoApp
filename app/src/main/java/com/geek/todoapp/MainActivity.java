package com.geek.todoapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.geek.todoapp.ui.home.HomeFragment;
import com.geek.todoapp.ui.home.TaskAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geek.todoapp.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private ArrayList<Integer> fragments = new ArrayList<>();
    private TaskAdapter adapter = new TaskAdapter();
    private HomeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = new HomeFragment();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        if (!Prefs.getInstance().isBoardShown()) navController.navigate(R.id.boardFragment);

        fragments.add(R.id.navigation_home);
        fragments.add(R.id.navigation_dashboard);
        fragments.add(R.id.navigation_notifications);
        fragments.add(R.id.profileFragment2);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if (fragments.contains(destination.getId())) {
                    navView.setVisibility(View.VISIBLE);
                } else
                    navView.setVisibility(View.GONE);

                if (destination.getId() == R.id.boardFragment) {
                    getSupportActionBar().hide();
                } else
                    getSupportActionBar().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.sort) {
//            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
//            adapter.addItems(App.getAppDataBase().taskDao().getAllSorted());
//        }
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            default:
                if (fragment !=  null)
                    fragment.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}