package com.example.taskmanager.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.taskmanager.R;
import com.example.taskmanager.taskList.TaskListFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends Fragment {
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private Unbinder unbinder;
    private TasksTabAdapter adapter;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TasksTabAdapter(
                getChildFragmentManager(),
                FragmentPagerAdapter.POSITION_UNCHANGED
        );

        // TODO: differentiate the data. (via params and newInstance)
        adapter.addFragment(new TaskListFragment(), getString(R.string.all));
        adapter.addFragment(new TaskListFragment(), getString(R.string.favorite));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addTaskFab)
    public void addTask(View view) {
        String pageTitle = adapter.getPageTitle(tabLayout.getSelectedTabPosition()).toString();
        boolean isFavorite = pageTitle.equals(getString(R.string.favorite));

        MainFragmentDirections.ActionAddTask actionAddTask = MainFragmentDirections.actionAddTask(null);
        actionAddTask.setIsFavorite(isFavorite);

        Navigation.findNavController(view).navigate(actionAddTask);
    }
}
