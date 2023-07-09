package com.example.todolist.addEditTask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.source.TasksRepository;
import com.example.todolist.data.source.local.TasksLocalDataSource;
import com.example.todolist.tasks.TasksPresenter;
import com.example.todolist.util.ActivityUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditTaskActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_TASK = 1;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_frame);

        fab = (FloatingActionButton) findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddEditTaskActivity.this, "fab clicked", Toast.LENGTH_SHORT).show();
            }
        });

        String taskId = null;
        if (addEditTaskFragment == null) {
            addEditTaskFragment = new AddEditTaskFragment();

            if (getIntent().hasExtra(AddEditTaskFragment.EDIT_TASK_ID)) {
                taskId = getIntent().getStringExtra(AddEditTaskFragment.EDIT_TASK_ID);
                actionBar.setTitle(R.string.edit_task);
                Bundle bundle = new Bundle();
                bundle.putString(AddEditTaskFragment.EDIT_TASK_ID, taskId);
                addEditTaskFragment.setArguments(bundle);
            } else {
                actionBar.setTitle(R.string.add_task);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditTaskFragment, R.id.content_frame);
        }

        TasksRepository repository = TasksRepository.getInstance(TasksLocalDataSource.getInstance(this));
        new AddEditTaskPresenter(taskId, repository, addEditTaskFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}