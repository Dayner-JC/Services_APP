package com.example.prueba;

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
    private final List<CardServices> servicios;
    private final List<CardServices> serviciosFiltrados;

    public interface OnItemClickListener {
        void onItemClick(CardServices cardServices);
    }

    public CardAdapter(List<CardServices> servicios, OnItemClickListener listener) {
        this.servicios = servicios;
        this.serviciosFiltrados = new ArrayList<>(servicios);
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtrarPorCategoria(String categoria) {
        this.serviciosFiltrados.clear();
        if (categoria.equals("All")) {
            this.serviciosFiltrados.addAll(this.servicios);
        } else {
            for (CardServices servicio : this.servicios) {
                if (servicio.getCategoria().equals(categoria)) {
                    this.serviciosFiltrados.add(servicio);
                }
            }
        }
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
        CardServices servicio = this.serviciosFiltrados.get(position);
        holder.image.setImageResource(servicio.getImagen());
        holder.titulo.setText(servicio.getTitulo());
        holder.categoria.setText(servicio.getCategoria());
        holder.precio.setText(servicio.getPrecio());
        holder.tiempo.setText(servicio.getTiempo());
    }

    @Override
    public int getItemCount() {
        return this.serviciosFiltrados.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView categoria;
        ImageView image;
        private final OnItemClickListener listener;
        TextView precio;
        TextView tiempo;
        TextView titulo;

        public CardViewHolder(View itemView, OnItemClickListener listener1) {
            super(itemView);
            this.image = itemView.findViewById(R.id.card_image);
            this.titulo = itemView.findViewById(R.id.titulo);
            this.categoria = itemView.findViewById(R.id.categoria);
            this.precio = itemView.findViewById(R.id.precio);
            this.tiempo = itemView.findViewById(R.id.tiempo);
            this.listener = listener1;
            itemView.setOnClickListener(CardViewHolder.this::m96x8c38c225);
        }

        public void m96x8c38c225(View v) {
            //noinspection deprecation
            int position = getAdapterPosition();
            if (position != -1 && this.listener != null) {
                this.listener.onItemClick(CardAdapter.this.serviciosFiltrados.get(position));
            }
        }
    }
}
