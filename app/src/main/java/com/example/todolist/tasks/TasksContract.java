package com.example.todolist.tasks;

import com.example.todolist.base.BasePresenter;
import com.example.todolist.base.BaseView;
import com.example.todolist.data.Task;

import java.util.List;

/**
 * This specifies the contract between the view and presenter
 */
public interface TasksContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showLoadingTasksError();

        void showNoTasks();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }
    interface Presenter extends BasePresenter {
        void result (int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetails(Task requestedTask);

        void completeTask(Task completedTask);

        void activateTask(Task activeTask);

        void clearCompletedTasks();

        void setFiltering(TasksFilterType requestType);

        TasksFilterType getFiltering();
    }
}
