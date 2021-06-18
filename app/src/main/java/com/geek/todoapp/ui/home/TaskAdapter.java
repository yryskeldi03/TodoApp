package com.geek.todoapp.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.todoapp.databinding.ItemsBinding;
import com.geek.todoapp.models.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> list = new ArrayList<>();

    public void addItem(Task task){
        this.list.add(0,task);
        notifyDataSetChanged();
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TaskAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemsBinding binding;

        public ViewHolder(@NonNull @NotNull ItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(Task task) {
            binding.textTitle.setText(task.getTitle());
        }
    }
}
