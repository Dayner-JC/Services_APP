package com.example.prueba;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/** @noinspection deprecation, deprecation */
public class CardWorkAdapter extends RecyclerView.Adapter<CardWorkAdapter.WorkViewHolder> {
    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private final List<WorkItem> workItemList;

    public interface OnItemClickListener {
        void onItemClick(WorkItem workItem);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(WorkItem workItem, int position);
    }

    public CardWorkAdapter(List<WorkItem> workItemList, OnItemClickListener listener, OnDeleteClickListener deleteListener) {
        this.workItemList = workItemList;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_work_item, parent, false);
        return new WorkViewHolder(view, listener, deleteListener, workItemList);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(WorkViewHolder holder, int position) {
        WorkItem workItem = this.workItemList.get(position);
        holder.dateTextView.setText(workItem.getDate());
        holder.titleTextView.setText(workItem.getTitle());
        holder.subtitleTextView.setText(workItem.getSubtitle());
        holder.progressTextView.setText(workItem.getProgress() + "%");
        holder.iconImageView.setImageResource(workItem.getIconResId());
        holder.progressText.setText(workItem.getProgressText());
        holder.deleteButton.setColorFilter(workItem.getButton());
        holder.deleteButton.setColorFilter(Color.parseColor("#1D1F3E"));
        holder.progressBar.setProgress(workItem.getProgress());
        if (workItem.getProgress() == 100) {
            changeColors(holder);
        } else {
            resetColors(holder);
        }
    }

    @Override
    public int getItemCount() {
        return this.workItemList.size();
    }

    public void removeItem(int position) {
        workItemList.remove(position);
        notifyItemRemoved(position);
    }

    public static class WorkViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView dateTextView;
        public ImageButton deleteButton;
        ImageView iconImageView;
        OnItemClickListener listener;
        OnDeleteClickListener deleteListener;
        ContentLoadingProgressBar progressBar;
        TextView progressText;
        TextView progressTextView;
        TextView subtitleTextView;
        TextView titleTextView;
        List<WorkItem> workItemList;

        public WorkViewHolder(View itemView, OnItemClickListener listener, OnDeleteClickListener deleteListener, List<WorkItem> workItemList) {
            super(itemView);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            this.dateTextView = itemView.findViewById(R.id.date);
            this.titleTextView = itemView.findViewById(R.id.title);
            this.subtitleTextView = itemView.findViewById(R.id.subtitle);
            this.progressBar = itemView.findViewById(R.id.seekBar);
            this.progressTextView = itemView.findViewById(R.id.progressText);
            this.iconImageView = itemView.findViewById(R.id.icon);
            this.progressText = itemView.findViewById(R.id.textProgress);
            this.cardView = (CardView) itemView;
            this.listener = listener;
            this.deleteListener = deleteListener;
            this.workItemList = workItemList;

            itemView.setOnClickListener(v -> listener.onItemClick(workItemList.get(getAdapterPosition())));

            deleteButton.setOnClickListener(v -> deleteListener.onDeleteClick(workItemList.get(getAdapterPosition()), getAdapterPosition()));
        }
    }

    private void changeColors(WorkViewHolder holder) {
        holder.dateTextView.setTextColor(-1);
        holder.titleTextView.setTextColor(-1);
        holder.subtitleTextView.setTextColor(-1);
        holder.progressTextView.setTextColor(-1);
        holder.iconImageView.setColorFilter(-1);
        holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#1D1F3E")));
        holder.progressText.setTextColor(-1);
        holder.deleteButton.setColorFilter(-1);
        holder.cardView.setCardBackgroundColor(Color.parseColor("#1D1F3E"));
        LayerDrawable layerDrawable = (LayerDrawable) holder.progressBar.getProgressDrawable();
        Drawable backgroundDrawable = layerDrawable.findDrawableByLayerId(android.R.id.background);
        if (backgroundDrawable instanceof GradientDrawable) {
            ((GradientDrawable) backgroundDrawable).setColor(-1);
        }
    }

    private void resetColors(WorkViewHolder holder) {
        holder.dateTextView.setTextColor(Color.parseColor("#1D1F3E"));
        holder.titleTextView.setTextColor(Color.parseColor("#1D1F3E"));
        holder.subtitleTextView.setTextColor(Color.parseColor("#1D1F3E"));
        holder.progressTextView.setTextColor(Color.parseColor("#1D1F3E"));
        holder.iconImageView.setColorFilter(Color.parseColor("#1D1F3E"));
        holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#E6E6E6")));
        holder.progressText.setTextColor(Color.parseColor("#1D1F3E"));
        holder.deleteButton.setColorFilter(Color.parseColor("#1D1F3E"));
        holder.cardView.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
        LayerDrawable layerDrawable = (LayerDrawable) holder.progressBar.getProgressDrawable();
        Drawable backgroundDrawable = layerDrawable.findDrawableByLayerId(android.R.id.background);
        if (backgroundDrawable instanceof GradientDrawable) {
            ((GradientDrawable) backgroundDrawable).setColor(Color.parseColor("#C5C5C5"));
        }
    }
}


