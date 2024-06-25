package com.example.prueba;

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


public class CardReferAdapter extends RecyclerView.Adapter<CardReferAdapter.CardViewHolder> {
    private final List<CardReferData> datos;
    private final Activity activity;
    private final String categoria;
    private final String precio;
    private final String tiempo;
    private final int imageId;

    public CardReferAdapter(List<CardReferData> datos, Activity activity, String categoria, String precio, String tiempo, int imageId) {
        this.datos = datos;
        this.activity = activity;
        this.categoria = categoria;
        this.precio = precio;
        this.tiempo = tiempo;
        this.imageId = imageId;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView etiqueta;
        public ImageView image;
        public TextView title;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.title =  itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.image);
            this.etiqueta = itemView.findViewById(R.id.etiqueta);
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
        CardReferData cardReferData = this.datos.get(position);
        holder.title.setText(cardReferData.getName());
        holder.image.setImageResource(cardReferData.getImage());
        if (cardReferData.getEsPopular()) {
            holder.etiqueta.setVisibility(View.VISIBLE);
        } else {
            holder.etiqueta.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            CardReferData referencia = datos.get(position);
            Intent intent = new Intent(activity, CardDetail.class);
            intent.putExtra("Titulo", referencia.getName());
            intent.putExtra("Categoria", categoria);
            intent.putExtra("Image", imageId);
            intent.putExtra("Precio", precio);
            intent.putExtra("Tiempo", tiempo);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    @Override
    public int getItemCount() {
        return this.datos.size();
    }
    }
