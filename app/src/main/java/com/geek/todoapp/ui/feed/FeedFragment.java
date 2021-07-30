package com.geek.todoapp.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentFeedBinding;
import com.geek.todoapp.databinding.FragmentFormBinding;
import com.geek.todoapp.models.Post;
import com.geek.todoapp.models.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private FragmentFeedBinding binding;
    private Post post;
    private FeedAdapter adapter;
    public int viewCount = 0;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FeedAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(v -> {
            openFragment();
        });
        initList();
        setFragmentListener();
        loadRealTimeData();
    }



    private void initList() {
        binding.recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Post post = adapter.getItem(position);
                openFullPost(post);
            }

            @Override
            public void onLongClick(int position) {
                Post post = adapter.getItem(position);
                FirebaseFirestore.getInstance()
                        .collection("posts")
                        .document(post.getDocId())
                        .delete();
                adapter.removeItem(position);
            }
        });
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("post_ff", getViewLifecycleOwner(), (requestKey, result) -> {
            post = (Post) result.getSerializable("post");
            adapter.addItem(post);
        });
    }

    private void loadRealTimeData() {
        FirebaseFirestore.getInstance()
                .collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                        List<Post> list = new ArrayList<>();
                        for (DocumentSnapshot snapshot : value) {
                            Post post = snapshot.toObject(Post.class);
                            post.setDocId(snapshot.getId());
                            list.add(post);
                        }
                        adapter.addItems(list);
                    }
                });
    }

    private void openFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.formFragmentFeed);
    }

    private void openFullPost(Post post) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("clickPost", post);
        navController.navigate(R.id.fullPostFragment, bundle);
    }
}