package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private final OnItemClickListener listener;
    private final List<CardServices> services;
    private final List<CardServices> filteredServices;

    public interface OnItemClickListener {
        void onItemClick(CardServices cardServices);
    }

    public CardAdapter(List<CardServices> services, OnItemClickListener listener) {
        this.services = services;
        this.filteredServices = new ArrayList<>(services);
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterByCategory(String category) {
        this.filteredServices.clear();
        if (category.equals("All")) {
            this.filteredServices.addAll(this.services);
        } else {
            for (CardServices service : this.services) {
                if (service.getCategory().equals(category)) {
                    this.filteredServices.add(service);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateList(List<CardServices> newServicesList) {
        this.filteredServices.clear();
        this.filteredServices.addAll(newServicesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardServices service = this.filteredServices.get(position);
        holder.image.setImageResource(service.getImage());
        holder.title.setText(service.getTitle());
        holder.category.setText(service.getCategory());
        holder.price.setText(service.getPrice());
        holder.time.setText(service.getTime());

        if (service.getPrice().equals("To Quote")) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.price.getLayoutParams();
            int newMarginEnd = UnitConverter.dpToPx(holder.itemView.getContext(),19);
            params.setMarginEnd(newMarginEnd);
            holder.price.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return this.filteredServices.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        ImageView image;
        private final OnItemClickListener listener;
        TextView price;
        TextView time;
        TextView title;

        public CardViewHolder(View itemView, OnItemClickListener listener1) {
            super(itemView);
            this.image = itemView.findViewById(R.id.card_image);
            this.title = itemView.findViewById(R.id.titulo);
            this.category = itemView.findViewById(R.id.categoria);
            this.price = itemView.findViewById(R.id.precio);
            this.time = itemView.findViewById(R.id.tiempo);
            this.listener = listener1;
            itemView.setOnClickListener(CardViewHolder.this::onClick);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != -1 && this.listener != null) {
                this.listener.onItemClick(CardAdapter.this.filteredServices.get(position));
            }
        }
    }
}
