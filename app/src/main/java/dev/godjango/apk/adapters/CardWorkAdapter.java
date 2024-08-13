package dev.godjango.apk.adapters;

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

import dev.godjango.apk.R;
import dev.godjango.apk.models.WorkItemData;

/** @noinspection deprecation, deprecation */
public class CardWorkAdapter extends RecyclerView.Adapter<CardWorkAdapter.WorkViewHolder> {
    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private final List<WorkItemData> workItemDataList;

    public interface OnItemClickListener {
        void onItemClick(WorkItemData workItemData);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(WorkItemData workItemData, int position);
    }

    public CardWorkAdapter(List<WorkItemData> workItemDataList, OnItemClickListener listener, OnDeleteClickListener deleteListener) {
        this.workItemDataList = workItemDataList;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_work_item, parent, false);
        return new WorkViewHolder(view, listener, deleteListener, workItemDataList);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(WorkViewHolder holder, int position) {
        WorkItemData workItemData = this.workItemDataList.get(position);
        holder.dateTextView.setText(workItemData.getDate());
        holder.titleTextView.setText(workItemData.getTitle());
        holder.subtitleTextView.setText(workItemData.getCategory());
        holder.progressTextView.setText(workItemData.getProgress() + "%");
        holder.iconImageView.setImageResource(workItemData.getIconResId());
        holder.progressText.setText(workItemData.getProgressText());
        holder.deleteButton.setColorFilter(workItemData.getButton());
        holder.deleteButton.setColorFilter(Color.parseColor("#1D1F3E"));
        holder.progressBar.setProgress(workItemData.getProgress());
        if (workItemData.getProgress() == 100) {
            changeColors(holder);
        } else {
            resetColors(holder);
        }
    }

    @Override
    public int getItemCount() {
        return this.workItemDataList.size();
    }

    public void removeItem(int position) {
        workItemDataList.remove(position);
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
        List<WorkItemData> workItemDataList;

        public WorkViewHolder(View itemView, OnItemClickListener listener, OnDeleteClickListener deleteListener, List<WorkItemData> workItemDataList) {
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
            this.workItemDataList = workItemDataList;

            itemView.setOnClickListener(v -> listener.onItemClick(workItemDataList.get(getAdapterPosition())));

            deleteButton.setOnClickListener(v -> deleteListener.onDeleteClick(workItemDataList.get(getAdapterPosition()), getAdapterPosition()));
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


