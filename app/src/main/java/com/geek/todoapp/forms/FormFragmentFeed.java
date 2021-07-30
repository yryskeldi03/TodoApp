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

import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentFormBinding;
import com.geek.todoapp.databinding.FragmentFormFeedBinding;
import com.geek.todoapp.models.Post;
import com.geek.todoapp.models.Task;
import com.geek.todoapp.ui.feed.FeedFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class FormFragmentFeed extends Fragment {

    private FragmentFormFeedBinding binding;
    private Post post;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFormFeedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSave.setOnClickListener(v -> saveInfo());
    }

    private void saveInfo() {
        Bundle bundle = new Bundle();
        if (binding.etText.getText().toString().equals("") || binding.etTitle.getText().toString().equals("")) {
            binding.etTitle.setError("Заполните все поля!");
            binding.etText.setError("Заполните все поля!");
        } else {
            post = new Post(binding.etTitle.getText().toString().trim()
                    , binding.etText.getText().toString().trim()
                    , FirebaseAuth.getInstance().getCurrentUser().getEmail());
            post.setCreatedAt(Timestamp.now());
            saveToFirestore(post);
        }
        bundle.putSerializable("post",post);
        getParentFragmentManager().setFragmentResult("post_ff", bundle);
    }

    private void saveToFirestore(Post post) {
        FirebaseFirestore.getInstance()
                .collection("posts")
                .add(post)
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