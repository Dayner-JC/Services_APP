package com.example.prueba;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{
    private final List<SliderItem> sliderItems;
    private final ViewPager2 viewPager2;

    public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager2){
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if(position == sliderItems.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder{
       private final RoundedImageView imageView;

       SliderViewHolder(@NonNull View itemView){
           super(itemView);
           imageView = itemView.findViewById(R.id.imageSlide);
       }

       void setImage(SliderItem sliderItem){
           imageView.setImageResource(sliderItem.getImage());
       }
   }

    private final Runnable runnable = new Runnable() {

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            List<SliderItem> newList = new ArrayList<>(sliderItems);  // Clonar la lista original
            sliderItems.addAll(newList);  // Agregar todos los elementos clonados de nuevo a sliderItems
            notifyDataSetChanged();  // Notificar al adapter que los datos han cambiado
        }
    };
}

