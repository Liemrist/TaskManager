package com.example.taskmanager.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskmanager.R;
import com.example.taskmanager.TaskParcelable;
import com.example.taskmanager.database.Task;
import com.example.taskmanager.database.TaskViewModel;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TaskFragment extends Fragment implements TaskPresenter.TaskView {

    @BindView(R.id.editTextTitle) EditText editTextTitle;
    @BindView(R.id.editTextDescription) EditText editTextDescription;

    private TaskPresenter presenter;
    private Unbinder unbinder;
    private TaskParcelable task;
    private TaskViewModel mWordViewModel;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new TaskPresenter(getContext(), this);
        mWordViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        unbinder = ButterKnife.bind(this, view);

        task = TaskFragmentArgs.fromBundle(getArguments()).getTask();
        if (task != null) {
            editTextTitle.setText(task.getTitle());
            editTextDescription.setText(task.getDescription());
        }

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.new_task_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.save_menu_item) {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();

            if (title.isEmpty() || description.isEmpty()) {
                showSnackbar(getString(R.string.enter_values));
            } else {
                if (task != null) {
                    presenter.updateTask(task, title, description);
                } else {
                    boolean isFromFavoriteTab = TaskFragmentArgs.fromBundle(getArguments()).getIsFavorite();
                    presenter.createTask(title, description, isFromFavoriteTab);
                    Task word = new Task("asd", "qwe");
                    mWordViewModel.insert(word);
                }
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSnackbar(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void clearFields() {
        editTextTitle.setText("");
        editTextDescription.setText("");
    }
}
