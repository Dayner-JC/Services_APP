package dev.godjango.apk;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CardReferencesAdapter extends RecyclerView.Adapter<CardReferencesAdapter.CardViewHolder> {
    private final List<CardReferencesData> dates;
    private final Activity activity;
    private final String category;
    private final String price;
    private final String time;
    private final int imageId;

    public CardReferencesAdapter(Activity activity,List<CardReferencesData> dates, String category, String price, String time, int imageId) {
        this.dates = dates;
        this.category = category;
        this.price = price;
        this.time = time;
        this.activity = activity;
        this.imageId = imageId;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView label;
        public ImageView image;
        public TextView title;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.title =  itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.image);
            this.label = itemView.findViewById(R.id.etiqueta);
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_refer_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardReferencesData cardReferencesData = this.dates.get(position);
        holder.title.setText(cardReferencesData.getName());
        holder.image.setImageResource(cardReferencesData.getImage());
        if (cardReferencesData.getEsPopular()) {
            holder.label.setVisibility(View.VISIBLE);
        } else {
            holder.label.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            CardReferencesData refer = dates.get(position);
            Intent intent = new Intent(activity, CardDetail.class);
            intent.putExtra("Title", refer.getName());
            intent.putExtra("Category", category);
            intent.putExtra("Image", imageId);
            intent.putExtra("Price", price);
            intent.putExtra("Time", time);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    @Override
    public int getItemCount() {
        return this.dates.size();
    }
    }
