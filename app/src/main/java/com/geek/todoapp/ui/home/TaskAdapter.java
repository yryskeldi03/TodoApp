package com.geek.todoapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.todoapp.databinding.ItemsBinding;
import com.geek.todoapp.models.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void addItem(Task task) {
        this.list.add(0, task);
        notifyDataSetChanged();
    }

    public void changeItems(Task task, int pos) {
        list.remove(pos);
        list.add(pos, task);
        notifyItemChanged(pos);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TaskAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
        holder.itemView.setOnLongClickListener(v -> {
            onItemClickListener.onItemLongClick(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Task getItem(int position) {
        return list.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }
}
