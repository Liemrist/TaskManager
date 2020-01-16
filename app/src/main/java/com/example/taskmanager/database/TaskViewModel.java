package com.example.taskmanager.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;

    private LiveData<List<Task>> tasks;

    public TaskViewModel (Application application) {
        super(application);
        repository = new TaskRepository(application);
        tasks = repository.getAllTasks();
    }

    public LiveData<List<Task>> getTasks() { return tasks; }

    public void insert(Task task) { repository.insert(task); }
}