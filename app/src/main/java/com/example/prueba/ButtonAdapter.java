package com.example.prueba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {
    private final CardAdapter brandingAdapter;
    private final RecyclerView brandingRecyclerView;
    private final List<String> buttonLabels;
    private final View fragmentView;
    private final CardAdapter interfacesAdapter;
    private final RecyclerView interfacesRecyclerView;
    private int selectedPosition = 0;
    private final CardAdapter servicesAdapter;
    private final RecyclerView servicesRecyclerView;

    public ButtonAdapter(List<String> buttonLabels, CardAdapter brandingAdapter, CardAdapter servicesAdapter, CardAdapter interfacesAdapter, RecyclerView brandingRecyclerView, RecyclerView servicesRecyclerView, RecyclerView interfacesRecyclerView, View fragmentView) {
        this.buttonLabels = buttonLabels;
        this.brandingAdapter = brandingAdapter;
        this.servicesAdapter = servicesAdapter;
        this.interfacesAdapter = interfacesAdapter;
        this.brandingRecyclerView = brandingRecyclerView;
        this.servicesRecyclerView = servicesRecyclerView;
        this.interfacesRecyclerView = interfacesRecyclerView;
        this.fragmentView = fragmentView;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item, parent, false);
        return new ButtonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ButtonViewHolder holder, int position) {
        String label = this.buttonLabels.get(position);
        holder.button.setText(label);
        holder.button.setSelected(position == this.selectedPosition);
        ViewGroup.LayoutParams params = holder.button.getLayoutParams();
        if ("All".equals(label)) {
            params.width = (int) (holder.button.getResources().getDisplayMetrics().density * 65.0f);
        } else {
            params.width = -2;
        }
        holder.button.setLayoutParams(params);
        holder.button.setOnClickListener(v -> {
            int previousSelectedPosition = ButtonAdapter.this.selectedPosition;
            ButtonAdapter.this.selectedPosition = holder.getAdapterPosition();
            ButtonAdapter.this.notifyItemChanged(previousSelectedPosition);
            ButtonAdapter.this.notifyItemChanged(ButtonAdapter.this.selectedPosition);
            String categoria = ButtonAdapter.this.buttonLabels.get(ButtonAdapter.this.selectedPosition);
            ButtonAdapter.this.filtrarServicios(categoria);
        });
    }

    public void filtrarServicios(String categoria) {
        char c;
        this.brandingAdapter.filtrarPorCategoria(categoria);
        this.servicesAdapter.filtrarPorCategoria(categoria);
        this.interfacesAdapter.filtrarPorCategoria(categoria);
        TextView brandingTitle = this.fragmentView.findViewById(R.id.branding_title);
        TextView servicesTitle = this.fragmentView.findViewById(R.id.Services_title);
        TextView interfacesTitle = this.fragmentView.findViewById(R.id.Interfaces_title);
        switch (categoria.hashCode()) {
            case -1788375783:
                if (categoria.equals("Interface")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 65921:
                if (categoria.equals("All")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 39268123:
                if (categoria.equals("Branding")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1443853438:
                if (categoria.equals("Services")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                brandingTitle.setVisibility(View.VISIBLE);
                servicesTitle.setVisibility(View.VISIBLE);
                interfacesTitle.setVisibility(View.VISIBLE);
                this.brandingRecyclerView.setVisibility(View.VISIBLE);
                this.servicesRecyclerView.setVisibility(View.VISIBLE);
                this.interfacesRecyclerView.setVisibility(View.VISIBLE);
                return;
            case 1:
                brandingTitle.setVisibility(View.VISIBLE);
                servicesTitle.setVisibility(View.GONE);
                interfacesTitle.setVisibility(View.GONE);
                this.brandingRecyclerView.setVisibility(View.VISIBLE);
                this.servicesRecyclerView.setVisibility(View.GONE);
                this.interfacesRecyclerView.setVisibility(View.GONE);
                return;
            case 2:
                brandingTitle.setVisibility(View.GONE);
                servicesTitle.setVisibility(View.VISIBLE);
                interfacesTitle.setVisibility(View.GONE);
                this.brandingRecyclerView.setVisibility(View.GONE);
                this.servicesRecyclerView.setVisibility(View.VISIBLE);
                this.interfacesRecyclerView.setVisibility(View.GONE);
                return;
            case 3:
                brandingTitle.setVisibility(View.GONE);
                servicesTitle.setVisibility(View.GONE);
                interfacesTitle.setVisibility(View.VISIBLE);
                this.brandingRecyclerView.setVisibility(View.GONE);
                this.servicesRecyclerView.setVisibility(View.GONE);
                this.interfacesRecyclerView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return this.buttonLabels.size();
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        public Button button;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            this.button = itemView.findViewById(R.id.button);
        }
    }
}
