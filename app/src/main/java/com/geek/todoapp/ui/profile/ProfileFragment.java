package com.geek.todoapp.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geek.todoapp.Prefs;
import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentProfileBinding;
import com.geek.todoapp.models.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    public static final int PICK_IMAGE = 1;
    private Prefs prefs;
    private Uri selected_Image_Uri;
    private boolean isClickImage= false;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(requireContext());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnOpenGallery.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        binding.etPhone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(binding.etPhone.getText().toString()));
                startActivity(intent);
                return true;
            }
        });

        binding.containerForImage.setOnClickListener(v -> {
            openFragment();
        });

        //Delete image
        binding.containerForImage.setOnLongClickListener(v -> {
            prefs.deleteImg();
            binding.containerForImage.setImageBitmap(null);
            return true;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selected_Image_Uri = data.getData();
            prefs.putImage(String.valueOf(selected_Image_Uri));
            binding.containerForImage.setImageURI(selected_Image_Uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //set image uri form prefs
        if (prefs.getImage() != null) {
            selected_Image_Uri = Uri.parse(prefs.getImage());
        }
        if (prefs.getUserData() != null){
            setDataFromPrefs(prefs.getUserData());
        }
        //set image in view
        Glide.with(requireContext()).load(selected_Image_Uri).circleCrop().into(binding.containerForImage);
    }

    private void setDataFromPrefs(String userData) {
        List<String> list = new ArrayList<>(Arrays.asList(userData.split(",")));
        binding.etUsername.setText(list.get(0));
        binding.etEmail.setText(list.get(1));
        binding.etPhone.setText(list.get(2));
        binding.etDateOfBirth.setText(list.get(3));
        binding.etAddress.setText(list.get(4));
    }

    @Override
    public void onStop() {
        super.onStop();
        String userData = checkEditText(binding.etUsername.getText().toString())+ ",";
        userData += checkEditText(binding.etEmail.getText().toString()) + ",";
        userData += checkEditText(binding.etPhone.getText().toString())+ ",";
        userData += checkEditText(binding.etDateOfBirth.getText().toString())+ ",";
        userData += checkEditText(binding.etAddress.getText().toString())+ ",";
        prefs.putUserData(userData);
    }

    private String checkEditText(String etUsername) {
        if (!etUsername.equals("")){
            return etUsername;
        }else
            return " ";
    }
    private void openFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putString("image", selected_Image_Uri.toString());

   //     getParentFragmentManager().setFragmentResult("prof", bundle);
        navController.navigate(R.id.imageFragment, bundle);
    }
}