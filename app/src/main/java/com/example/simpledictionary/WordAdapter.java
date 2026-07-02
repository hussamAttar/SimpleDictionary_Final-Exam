package com.example.simpledictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private final ArrayList<WordItem> wordList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    public WordAdapter(ArrayList<WordItem> wordList, OnItemClickListener listener) {
        this.wordList = wordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        WordItem currentItem = wordList.get(position);
        holder.tvWord.setText(currentItem.getWord());
        holder.tvMeaning.setText(currentItem.getMeaning());
    }

    @Override
    public int getItemCount() { return wordList.size(); }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWord, tvMeaning;

        @SuppressWarnings("deprecation")
        public WordViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
            tvMeaning = itemView.findViewById(R.id.tvMeaning);

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(getAdapterPosition());
            });

            itemView.setOnLongClickListener(v -> {
                if (listener != null) listener.onItemLongClick(getAdapterPosition());
                return true;
            });
        }
    }
}