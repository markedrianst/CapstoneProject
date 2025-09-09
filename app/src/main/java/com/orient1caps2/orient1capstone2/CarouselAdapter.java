package com.orient1caps2.orient1capstone2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private static final int LOOP_FACTOR = 1000;
    private final List<Integer> imageList;
    private final List<String> textList;

    public CarouselAdapter(List<Integer> imageList, List<String> textList) {
        this.imageList = imageList;
        this.textList = textList;
    }


    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        int realPosition = position % imageList.size();
        holder.imageView.setImageResource(imageList.get(realPosition));
        holder.textView.setText(textList.get(realPosition));
    }

    @Override
    public int getItemCount() {
        return imageList.size() * LOOP_FACTOR;
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        CarouselViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carouselImage);
            textView = itemView.findViewById(R.id.carouselText);
        }
    }
}
