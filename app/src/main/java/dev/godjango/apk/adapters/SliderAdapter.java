package dev.godjango.apk.adapters;

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

import dev.godjango.apk.R;
import dev.godjango.apk.models.SliderItemData;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{
    private final List<SliderItemData> sliderItemData;
    private final ViewPager2 viewPager2;

    public SliderAdapter(List<SliderItemData> sliderItemData, ViewPager2 viewPager2){
        this.sliderItemData = sliderItemData;
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
        holder.setImage(sliderItemData.get(position));
        if(position == sliderItemData.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItemData.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder{
       private final RoundedImageView imageView;

       SliderViewHolder(@NonNull View itemView){
           super(itemView);
           imageView = itemView.findViewById(R.id.imageSlide);
       }

       void setImage(SliderItemData sliderItemData){
           imageView.setImageResource(sliderItemData.getImage());
       }
   }

    private final Runnable runnable = new Runnable() {

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            List<SliderItemData> newList = new ArrayList<>(sliderItemData);
            sliderItemData.addAll(newList);
            notifyDataSetChanged();
        }
    };
}

