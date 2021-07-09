package com.geek.todoapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
    private List<Task> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = App.getAppDataBase().taskDao().getAll();
        initAdapter(list);
    }

    private void initAdapter(List<Task> list) {
        adapter = new TaskAdapter();
        this.list = (list);
        adapter.addItems(this.list);
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
        adapter = new TaskAdapter();
        adapter.updateItems(App.getAppDataBase().taskDao().getAllSorted());
    }


    private void openFragment(Task task) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        navController.navigate(R.id.formFragment, bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: ");
    }
}