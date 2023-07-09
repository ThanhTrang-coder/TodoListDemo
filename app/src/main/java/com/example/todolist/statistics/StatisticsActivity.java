package com.example.todolist.statistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.source.TasksRepository;
import com.example.todolist.data.source.local.TasksLocalDataSource;
import com.example.todolist.tasks.TasksActivity;
import com.example.todolist.tasks.TasksPresenter;
import com.example.todolist.util.ActivityUtils;
import com.google.android.material.navigation.NavigationView;

public class StatisticsActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private StatisticsPresenter statisticsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.statistics_title));
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(navigationView != null) {
            setUpDrawerContent(navigationView);
        }

        StatisticFragment statisticFragment = (StatisticFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(statisticFragment == null) {
            statisticFragment = new StatisticFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), statisticFragment, R.id.content_frame);
        }

        TasksRepository repository = TasksRepository.getInstance(TasksLocalDataSource.getInstance(this));
        statisticsPresenter = new StatisticsPresenter(repository, statisticFragment);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                Intent intent =
                                        new Intent(getApplicationContext(), TasksActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.statistics_navigation_menu_item:
                                //Do nothing, we're already on that screen
                                break;
                            default:
                                break;
                        }
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}