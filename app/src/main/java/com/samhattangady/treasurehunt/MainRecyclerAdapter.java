package com.samhattangady.treasurehunt;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for RecyclerViews in MainActivity
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ContentHolder> {

    public interface OnItemClickListener {
        void onItemClick(Hunt hunt);
    }

    private List<Hunt> mHunts;
    private OnItemClickListener listener;

    public class ContentHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public ContentHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_text);
            imageView = (ImageView) view.findViewById(R.id.item_image);
        }

        public void bind(final Hunt hunt, final OnItemClickListener listener) {
            textView.setText(hunt.getHuntName());
            Picasso.with(imageView.getContext())
                    .load(hunt.getImageLink())
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(hunt);
                }
            });
        }
    }

    public MainRecyclerAdapter(List<Hunt> hunts, OnItemClickListener listener) {
        this.mHunts = hunts;
        this.listener = listener;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_page_recycler_view, parent, false);
        return new ContentHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(mHunts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return  mHunts.size();
    }

}

