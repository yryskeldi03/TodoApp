package com.geek.todoapp.forms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.geek.todoapp.App;
import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentFormBinding;
import com.geek.todoapp.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    private Task task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        task = (Task) requireArguments().getSerializable("task");
        if (task != null)
            binding.editText.setText(task.getTitle());

        task = (Task) requireArguments().getSerializable("dashTask");
        if (task != null)
            binding.editText.setText(task.getTitle());

        binding.btnSave.setOnClickListener(v -> save());

    }

    private void save() {
        String text = binding.editText.getText().toString();
        Bundle bundle = new Bundle();
        if (task == null) {
            task = new Task(text);
            task.setCreatedAt(System.currentTimeMillis());
            App.getAppDataBase().taskDao().insert(task);
            saveToFirestore(task);
        } else {
            task.setTitle(text);
            App.getAppDataBase().taskDao().update(task);
            updateItemFirestore(task);
        }
        bundle.putSerializable("text", task);
        getParentFragmentManager().setFragmentResult("form", bundle);

    }

    private void updateItemFirestore(Task task){
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(task.getDocId())
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        close();
                    }
                });
    }

    private void saveToFirestore(Task task) {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .add(task)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(com.google.android.gms.tasks.Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                    close();
                } else
                    Toast.makeText(requireContext(), "Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}