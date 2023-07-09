package com.example.todolist.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.source.TasksRepository;
import com.example.todolist.data.source.local.TasksLocalDataSource;
import com.example.todolist.statistics.StatisticsActivity;
import com.example.todolist.util.ActivityUtils;
import com.google.android.material.navigation.NavigationView;

public class TasksActivity extends AppCompatActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private TasksPresenter tasksPresenter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(tasksFragment == null) {
            tasksFragment = new TasksFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), tasksFragment, R.id.content_frame);
        }

        TasksRepository repository = TasksRepository.getInstance(TasksLocalDataSource.getInstance(this));
        tasksPresenter = new TasksPresenter(repository, tasksFragment);

        if(savedInstanceState != null) {
            TasksFilterType currentFiltering = (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            tasksPresenter.setFiltering(currentFiltering);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, tasksPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                // Open the navigation drawer when the home icon is selected from the toolbar.
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.list_navigation_menu_item:

                            break;
                        case R.id.statistics_navigation_menu_item:
                            Intent intent = new Intent(TasksActivity.this, StatisticsActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    return true;
                }
        );
    }
}