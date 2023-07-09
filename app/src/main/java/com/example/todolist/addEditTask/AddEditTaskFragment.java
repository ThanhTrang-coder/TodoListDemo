package com.example.todolist.addEditTask;

import static androidx.core.util.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.source.TasksDataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AddEditTaskFragment extends Fragment implements AddEditTaskContract.View {
    private AddEditTaskContract.Presenter presenter;
    public static final String EDIT_TASK_ID = "EDIT_TASK_ID";
    private EditText tvTitle, tvDesc;
    private String editedTaskId;

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_task, container, false);
        tvTitle = root.findViewById(R.id.add_task_title);
        tvDesc = root.findViewById(R.id.add_task_description);

        setTaskIdIfAny();

        setHasOptionsMenu(true);
        setRetainInstance(true);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNewTask()) {
                    presenter.createTask(
                            tvTitle.getText().toString(),
                            tvDesc.getText().toString()
                    );
                } else {
                    presenter.updateTask(
                            tvTitle.getText().toString(),
                            tvDesc.getText().toString()
                    );
                }
            }
        });

        return root;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(AddEditTaskContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showEmptyTaskError() {
        Snackbar.make(tvTitle, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTasksList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        tvDesc.setText(description);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void setTaskIdIfAny() {
        if(getArguments() != null && getArguments().containsKey(EDIT_TASK_ID)) {
            editedTaskId = getArguments().getString(EDIT_TASK_ID);
        }
    }

    private boolean isNewTask() {
        return editedTaskId == null;
    }
}