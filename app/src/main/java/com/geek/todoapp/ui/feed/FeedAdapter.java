package com.geek.todoapp.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.todoapp.databinding.ItemNewsBinding;
import com.geek.todoapp.models.Post;
import com.geek.todoapp.models.Task;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private ArrayList<Post> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void addItem(Post post) {
        this.list.add(post);
        notifyDataSetChanged();
    }

    public void addItems(List<Post> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Post getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @NotNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeedAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(position));
        holder.itemView.setOnLongClickListener(v -> {
            onItemClickListener.onLongClick(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;

        public ViewHolder(ItemNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(Post post) {
            String dateFormat = new SimpleDateFormat("HH:mm dd MMMM yyyy 'г'").format(post.getCreatedAt().toDate());
            binding.tvEmail.setText("От: " + post.getEmail());
            binding.tvTitle.setText(post.getTitle());
            binding.tvText.setText(post.getText());
            binding.tvViewCount.setText(String.valueOf(post.getViewCount()));
            binding.tvCreatedAt.setText(dateFormat);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }
}
