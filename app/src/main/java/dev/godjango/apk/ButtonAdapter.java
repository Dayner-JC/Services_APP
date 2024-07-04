package dev.godjango.apk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @noinspection ALL*/
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
    private final Map<String, Integer[]> visibilityMap = new HashMap<>();

    public ButtonAdapter(List<String> buttonLabels, CardAdapter brandingAdapter, CardAdapter servicesAdapter, CardAdapter interfacesAdapter, RecyclerView brandingRecyclerView, RecyclerView servicesRecyclerView, RecyclerView interfacesRecyclerView, View fragmentView) {
        this.buttonLabels = buttonLabels;
        this.brandingAdapter = brandingAdapter;
        this.servicesAdapter = servicesAdapter;
        this.interfacesAdapter = interfacesAdapter;
        this.brandingRecyclerView = brandingRecyclerView;
        this.servicesRecyclerView = servicesRecyclerView;
        this.interfacesRecyclerView = interfacesRecyclerView;
        this.fragmentView = fragmentView;

        visibilityMap.put("All", new Integer[]{View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE});
        visibilityMap.put("Branding", new Integer[]{View.VISIBLE, View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE});
        visibilityMap.put("Services", new Integer[]{View.GONE, View.VISIBLE, View.GONE, View.GONE, View.VISIBLE, View.GONE});
        visibilityMap.put("Interface", new Integer[]{View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.VISIBLE});
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
            String category = ButtonAdapter.this.buttonLabels.get(ButtonAdapter.this.selectedPosition);
            ButtonAdapter.this.filterServices(category);
        });
    }

    public void filterServices(String categoria) {
        this.brandingAdapter.filterByCategory(categoria);
        this.servicesAdapter.filterByCategory(categoria);
        this.interfacesAdapter.filterByCategory(categoria);

        TextView brandingTitle = this.fragmentView.findViewById(R.id.branding_title);
        TextView servicesTitle = this.fragmentView.findViewById(R.id.Services_title);
        TextView interfacesTitle = this.fragmentView.findViewById(R.id.Interfaces_title);

        Integer[] visibilities = visibilityMap.get(categoria);
        if (visibilities != null) {
            brandingTitle.setVisibility(visibilities[0]);
            servicesTitle.setVisibility(visibilities[1]);
            interfacesTitle.setVisibility(visibilities[2]);
            this.brandingRecyclerView.setVisibility(visibilities[3]);
            this.servicesRecyclerView.setVisibility(visibilities[4]);
            this.interfacesRecyclerView.setVisibility(visibilities[5]);
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
