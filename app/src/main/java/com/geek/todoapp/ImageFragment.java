package com.geek.todoapp;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.geek.todoapp.databinding.FragmentImageBinding;
import com.geek.todoapp.models.Task;
import com.geek.todoapp.ui.home.HomeFragment;
import com.geek.todoapp.ui.profile.ProfileFragment;

import org.jetbrains.annotations.NotNull;

public class ImageFragment extends Fragment {
    private FragmentImageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(requireContext()).load(requireArguments().getString("image")).into(binding.forImgFromProfile);
    }
}