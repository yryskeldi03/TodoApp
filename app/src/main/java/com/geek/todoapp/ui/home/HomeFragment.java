package com.geek.todoapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geek.todoapp.App;
import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentHomeBinding;
import com.geek.todoapp.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private TaskAdapter adapter;
    private FragmentHomeBinding binding;
    private Task task;
    private int position;
    private List<Task> list = new ArrayList<>();
    private int currentPos;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        list = App.getAppDataBase().taskDao().getAll();
        initAdapter(list);
    }

    private void initAdapter(List<Task> list) {
        adapter = new TaskAdapter();
        this.list = (list);
        adapter.addItems(this.list);
        adapter.notifyDataSetChanged();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            currentPos = -1;
            openFragment(null);
        });
        setFragmentListener();
        initList();

    }

    private void initList() {
        binding.recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currentPos = position;
                HomeFragment.this.position = position;
                Task task = adapter.getItem(position);
                openFragment(task);
            }

            @Override
            public void onItemLongClick(int position) {
                Task task = adapter.getItem(position);
                new AlertDialog.Builder(requireContext()).setTitle("Удаление").
                        setMessage("Удалить запись \"" + task.getTitle() + "\" ?")
                        .setNegativeButton("Нет", null)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.getAppDataBase().taskDao().delete(task);
                                //adapter.removeItem(position);
                                //adapter.notifyItemRemoved(position);
                            }
                        }).show();
            }
        });
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("form", getViewLifecycleOwner(), (requestKey, result) -> {
            task = (Task) result.getSerializable("text");
            if (currentPos == -1) adapter.addItem(task);
            else adapter.updateItem(task, position);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                initAdapterSort();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAdapterSort() {
        adapter.updateItems(App.getAppDataBase().taskDao().getAllSorted());
    }

    private void openFragment(Task task) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        navController.navigate(R.id.formFragment, bundle);
    }
}