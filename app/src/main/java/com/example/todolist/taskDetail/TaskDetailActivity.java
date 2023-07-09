package com.example.todolist.taskDetail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.todolist.R;
import com.example.todolist.data.source.TasksRepository;
import com.example.todolist.data.source.local.TasksLocalDataSource;
import com.example.todolist.tasks.TasksPresenter;
import com.example.todolist.util.ActivityUtils;

public class TaskDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TASK_ID = "TASK_ID";
    private TaskDetailPresenter taskDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested taskId
        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (taskDetailFragment == null) {
            taskDetailFragment = taskDetailFragment.newInstance(taskId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), taskDetailFragment, R.id.content_frame);
        }

        TasksRepository repository = TasksRepository.getInstance(TasksLocalDataSource.getInstance(this));
        taskDetailPresenter = new TaskDetailPresenter(taskId, repository, taskDetailFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}