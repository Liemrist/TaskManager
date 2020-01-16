package com.example.taskmanager.task;

import android.content.Context;

import com.example.taskmanager.TaskParcelable;

public class TaskPresenter {

    private Context context;
    private TaskView view;

    public TaskPresenter(Context context, TaskView view) {
        this.context = context;
        this.view = view;
    }

    // TODO: handle Save click properly.
    public void createTask(String title, String description, Boolean isFavorite) {
//        SharedPreferences sharedPref = context.getSharedPreferences(
//                context.getString(R.string.preference_file_key),
//                Context.MODE_PRIVATE
//        );
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("asd", title);
//        editor.putString("asd", description);
//        editor.putBoolean("asd", isFavorite);
//        editor.commit();

        view.clearFields();
        view.showSnackbar("Task created");
    }

    public void updateTask(TaskParcelable task, String title, String description) {
        // TODO: handle task update.
    }

    public interface TaskView{
        void showSnackbar(String text);
        void clearFields();
    }
}
