package com.geek.todoapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
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
    private boolean isChanged = false;
    private ArrayList<Task> list = new ArrayList<>();
    private boolean sorted = false;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
        list = (ArrayList<Task>) App.getAppDataBase().taskDao().getAll();
        adapter.addItems(list);
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
            isChanged = false;
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
                isChanged = true;
                HomeFragment.this.position = position;
                Task task = adapter.getItem(position);
                openFragment(task);
                Toast.makeText(requireContext(), "Text:" + task.getTitle() + " Position:" + (position + 1), Toast.LENGTH_SHORT).show();
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
            if (isChanged) adapter.changeItems(task, position);
            else adapter.addItem(task);
        });
    }

    private void loadDataSorted() {
        App.getAppDataBase().taskDao().getAllSorted().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            loadDataSorted();
        }
        return super.onOptionsItemSelected(item);
    }


    private void openFragment(Task task) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        navController.navigate(R.id.formFragment, bundle);
    }
}