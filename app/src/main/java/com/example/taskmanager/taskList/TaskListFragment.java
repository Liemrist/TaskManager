package com.example.taskmanager.taskList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.TaskParcelable;
import com.example.taskmanager.database.Task;
import com.example.taskmanager.database.TaskViewModel;
import com.example.taskmanager.dummy.DummyContent;
import com.example.taskmanager.main.MainFragmentDirections;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TaskListFragment extends Fragment implements OnListFragmentInteractionListener, PopupMenu.OnMenuItemClickListener {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    // TODO: rename.
    private Task dummyItem;
    private TaskViewModel mWordViewModel;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskListFragment newInstance(int columnCount) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // TODO: rename to TaskListAdapter.
        final MyTaskRecyclerViewAdapter adapter = new MyTaskRecyclerViewAdapter(DummyContent.ITEMS, this);
        recyclerView.setAdapter(adapter);

        mWordViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        // TODO: put observer creation here in onActivityCreated (override) method.
        mWordViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the words in the adapter.
                adapter.setTasks(tasks);
            }
        });

        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListFragmentInteraction(Task task) {
        navigateToEditTask(task);
    }

    @Override
    public void onListMenuInteraction(Task task, View view) {
        // FIXME: Should I use Context menu for this purpose?
        PopupMenu popup = new PopupMenu(getContext(), view);
        dummyItem = task;
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.list_item_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_task:
                navigateToEditTask(dummyItem);
                return true;
            case R.id.delete_task:
                Snackbar.make(getView(), dummyItem.getTitle(), Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.add_task_to_favorites:
                Snackbar.make(getView(), dummyItem.toString(), Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    private void navigateToEditTask(Task item) {
        TaskParcelable task = new TaskParcelable(item.getTitle(), item.getDescription(), false);
        MainFragmentDirections.ActionEditTask actionEditTask = MainFragmentDirections.actionEditTask(task);
        NavHostFragment.findNavController(this).navigate(actionEditTask);
    }
}
/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(Task task);
    void onListMenuInteraction(Task task, View view);
}