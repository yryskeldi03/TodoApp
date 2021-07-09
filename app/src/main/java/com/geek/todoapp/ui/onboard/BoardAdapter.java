package com.geek.todoapp.ui.onboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.todoapp.R;
import com.geek.todoapp.databinding.FragmentBoardBinding;
import com.geek.todoapp.databinding.ItemsBinding;
import com.geek.todoapp.databinding.PagerBoardBinding;
import com.geek.todoapp.ui.home.TaskAdapter;

import org.jetbrains.annotations.NotNull;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[]{"SIMPLE ABROAD CALLS", "FREE WONEP TO WONEP", "NO HIDDEN CHARGES OR FEES"};
    private String[] descriptions = new String[]{"Wonep converts international calls to local calls without WiFi or data", "If the person you're calling also has Wonep the call will be entirely free", "We have a very small charge for non-Wonep calls to mobiles or landlines"};
    private int[] imageViews = new int[]{R.raw.anim, R.raw.anim, R.raw.anim};
    private OnNextClick onNextClick;

    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BoardAdapter.ViewHolder(PagerBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(BoardAdapter.ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setOnNextClick(OnNextClick onNextClick) {
        this.onNextClick = onNextClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private PagerBoardBinding binding;

        public ViewHolder(@NotNull PagerBoardBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(int position) {
            binding.textTitle.setText(titles[position]);
            binding.textDescription.setText(descriptions[position]);
            binding.imageView.setAnimation(imageViews[position]);
            binding.btnNext.setOnClickListener(v -> onNextClick.onBtnNextClickListener());
            if (position == 2) binding.btnNext.setVisibility(View.VISIBLE);
            else binding.btnNext.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnNextClick {
        void onBtnNextClickListener();
    }
}
