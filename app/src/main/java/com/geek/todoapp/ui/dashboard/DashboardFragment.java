package com.geek.todoapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentDashboardBinding;
import com.geek.todoapp.models.Task;
import com.geek.todoapp.ui.home.TaskAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private TaskAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
    }

    private void initList() {
        binding.recyclerDash.setAdapter(adapter);
        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = adapter.getItem(position);
                openFragment(task);
                adapter.updateItem(task, position);
            }

            @Override
            public void onItemLongClick(int position) {
                Task task = adapter.getItem(position);
                FirebaseFirestore.getInstance()
                        .collection("tasks")
                        .document(task.getDocId())
                        .delete();
                adapter.removeItem(position);
            }
        });
        loadRealTimeData();
    }

    private void loadRealTimeData() {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .orderBy("title")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                        List<Task> list = new ArrayList<>();
                        for (DocumentSnapshot snapshot : value) {
                            Task task = snapshot.toObject(Task.class);
                            task.setDocId(snapshot.getId());
                            list.add(task);
                        }
                        adapter.addItems(list);
                    }
                });
    }

    private void loadData() {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .orderBy("title")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        List<Task> list = new ArrayList<>();
                        for (DocumentSnapshot snapshot : snapshots) {
                            Task task = snapshot.toObject(Task.class);
                            task.setDocId(snapshot.getId());
                            list.add(task);
                        }
                        //List<Task> list = snapshots.toObjects(Task.class);
                        adapter.addItems(list);
                    }
                });
    }
    private void openFragment(Task task) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dashTask", task);
        navController.navigate(R.id.formFragment , bundle);
    }
}