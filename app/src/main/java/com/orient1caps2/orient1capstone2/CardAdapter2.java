package com.orient1caps2.orient1capstone2;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.ViewHolder> {

    private final List<CardItem> items;

    public CardAdapter2(List<CardItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!items.isEmpty()) {
            CardItem item = items.get(position % items.size());
            holder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.isEmpty() ? 0 : Integer.MAX_VALUE;
    }

    public static class CardItem {
        private final int imageRes;
        private final String title;
        private final String description;
        private final Spanned formattedDescription;

        public CardItem(int imageRes, String title, String description) {
            this.imageRes = imageRes;
            this.title = title;
            this.description = description;
            this.formattedDescription = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY);
        }

        public int getImageRes() {
            return imageRes;
        }

        public String getTitle() {
            return title;
        }

        public Spanned getFormattedDescription() {
            return formattedDescription;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView titleView;
        private final TextView descriptionView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            titleView = itemView.findViewById(R.id.cardTitle);
            descriptionView = itemView.findViewById(R.id.textView);
        }

        public void bind(CardItem item) {
            imageView.setImageResource(item.getImageRes());
            titleView.setText(item.getTitle());
            descriptionView.setText(item.getFormattedDescription());
        }
    }
}