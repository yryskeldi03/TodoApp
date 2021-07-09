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
import com.geek.todoapp.models.Task;

import org.jetbrains.annotations.NotNull;

public class ImageFragment extends Fragment {
    ImageView imageView;
    String str = Manifest.permission.READ_EXTERNAL_STORAGE;
    private ActivityResultLauncher<String> resultLauncher;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result){
                assert getArguments() != null;
                Glide.with(requireContext())
                        .load(Uri.parse(getArguments()
                                .getString("image")))
                        .into(imageView);
            }else resultLauncher.launch(str);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.for_img_from_profile);

        resultLauncher.launch(str);
        if (getArguments() != null) {
            Log.e("TAG", "onViewCreated:" + getArguments().getString("image"));


          //   imageView.setImageURI(Uri.parse(getArguments().getString("image")));

        }
    }
}