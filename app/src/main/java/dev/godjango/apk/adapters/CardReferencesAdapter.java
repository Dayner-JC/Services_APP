package dev.godjango.apk.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.godjango.apk.ui.activities.CardDetailActivity;
import dev.godjango.apk.models.CardServicesData;
import dev.godjango.apk.R;

public class CardReferencesAdapter extends RecyclerView.Adapter<CardReferencesAdapter.CardViewHolder> {
    private final List<CardServicesData> cards;
    private final Activity activity;
    private final List<CardServicesData> originalCards;

    public CardReferencesAdapter(Activity activity, List<CardServicesData> cards, List<CardServicesData> originalCards) {
        this.cards = cards;
        this.activity = activity;
        this.originalCards = new ArrayList<>(originalCards);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView label;
        public ImageView image;
        public TextView title;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
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
        CardServicesData card = this.cards.get(position);
        holder.title.setText(card.getTitle());
        holder.image.setImageResource(card.getImage());
        holder.label.setVisibility(card.getEsPopular() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            CardServicesData clickedCard = cards.get(position);

            List<CardServicesData> newFilteredCards = new ArrayList<>(originalCards);
            List<CardServicesData> cardsToSend = new ArrayList<>(newFilteredCards);
            newFilteredCards.removeIf(c -> c.getTitle().equals(clickedCard.getTitle()));

            Intent intent = new Intent(activity, CardDetailActivity.class);
            intent.putExtra("Title", clickedCard.getTitle());
            intent.putExtra("Category", clickedCard.getCategory());
            intent.putExtra("Image", clickedCard.getImage());
            intent.putExtra("Price", clickedCard.getPrice());
            intent.putExtra("Time", clickedCard.getTime());
            intent.putParcelableArrayListExtra("CardsOfSameCategory", new ArrayList<>(cardsToSend));
            intent.putExtra("ClickedCard", clickedCard);

            activity.startActivity(intent);
            activity.finish();

        });
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }
}
