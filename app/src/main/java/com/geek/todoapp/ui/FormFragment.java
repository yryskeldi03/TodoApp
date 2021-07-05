package com.geek.todoapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geek.todoapp.App;
import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentFormBinding;
import com.geek.todoapp.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Task task = (Task) requireArguments().getSerializable("task");
        if (task != null) binding.editText.setText(task.getTitle());
        binding.btnSave.setOnClickListener(v -> {
            save();
        });

    }

    private void save() {
        String text = binding.editText.getText().toString();
        Bundle bundle = new Bundle();
        Task task =  new Task(text);
        task.setCreatedAt(System.currentTimeMillis());
        bundle.putSerializable("text", task);
        getParentFragmentManager().setFragmentResult("form", bundle);

        App.getAppDataBase().taskDao().insert(task);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}