package com.geek.todoapp.ui.onboard;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geek.todoapp.Prefs;
import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentBoardBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class BoardFragment extends Fragment {
    private FragmentBoardBinding binding;
    private BoardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BoardAdapter();
        adapter.setOnNextClick(() -> close());

        binding.viewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.dots, binding.viewPager, true, (tab, position) -> { }).attach();

        binding.btnSkip.setOnClickListener(v -> close());

    }

    private void close() {
        Prefs prefs = new Prefs(getActivity());
        prefs.saveBoardState();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}