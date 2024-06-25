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


public class CardWorkAdapter extends RecyclerView.Adapter<CardWorkAdapter.WorkViewHolder> {
    static WorkItem workItem;
    private final OnItemClickListener listener;
    private final List<WorkItem> workItemList;

    public interface OnItemClickListener {
        void onItemClick(WorkItem workItem);
    }

    public CardWorkAdapter(List<WorkItem> workItemList, OnItemClickListener listener) {
        this.workItemList = workItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_work_item, parent, false);
        return new WorkViewHolder(view, this.listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(WorkViewHolder holder, int position) {
        WorkItem workItem2 = this.workItemList.get(position);
        holder.dateTextView.setText(workItem2.getDate());
        holder.titleTextView.setText(workItem2.getTitle());
        holder.subtitleTextView.setText(workItem2.getSubtitle());
        holder.progressTextView.setText(workItem2.getProgress() + "%");
        holder.iconImageView.setImageResource(workItem2.getIconResId());
        holder.progressText.setText(workItem2.getProgressText());
        holder.deleteButton.setColorFilter(workItem2.getButton());
        holder.deleteButton.setColorFilter(Color.parseColor("#1D1F3E"));
        holder.progressBar.setProgress(workItem2.getProgress());
        if (workItem2.getProgress() == 100) {
            changeColors(holder);
        } else {
            resetColors(holder);
        }
    }

    @Override
    public int getItemCount() {
        return this.workItemList.size();
    }

    public static class WorkViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView dateTextView;
        public ImageButton deleteButton;
        ImageView iconImageView;
        OnItemClickListener listener;
        ContentLoadingProgressBar progressBar;
        TextView progressText;
        TextView progressTextView;
        TextView subtitleTextView;
        TextView titleTextView;

        public WorkViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            this.dateTextView = itemView.findViewById(R.id.date);
            this.titleTextView = itemView.findViewById(R.id.title);
            this.subtitleTextView = itemView.findViewById(R.id.subtitle);
            this.progressBar =  itemView.findViewById(R.id.seekBar);
            this.progressTextView = itemView.findViewById(R.id.progressText);
            this.iconImageView = itemView.findViewById(R.id.icon);
            this.progressText =  itemView.findViewById(R.id.textProgress);
            this.cardView = (CardView) itemView;
            this.listener = listener;
            itemView.setOnClickListener(WorkViewHolder.this::m101x686671d7);
        }

        public void m101x686671d7(View v) {
            this.listener.onItemClick(CardWorkAdapter.workItem);
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
