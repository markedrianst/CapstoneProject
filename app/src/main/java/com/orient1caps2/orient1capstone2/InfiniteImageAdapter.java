package com.orient1caps2.orient1capstone2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InfiniteImageAdapter extends RecyclerView.Adapter<InfiniteImageAdapter.ViewHolder> {

    private final List<Integer> images; // drawable resource ids
    private final Context context;

    public InfiniteImageAdapter(Context context, List<Integer> images) {
        this.context = context;
        this.images = images;
    }

    // Use a very large number to simulate infinite
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private int actualPosition(int position) {
        if (images.isEmpty()) return 0;
        return position % images.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int realPos = actualPosition(position);
        holder.imageView.setImageResource(images.get(realPos));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
        }
    }
}
