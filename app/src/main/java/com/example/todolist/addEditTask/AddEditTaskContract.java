package com.example.todolist.addEditTask;

import com.example.todolist.base.BasePresenter;
import com.example.todolist.base.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {
        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void createTask(String title, String description);

        void updateTask(String title, String description);

        void populateTask();
    }
}
