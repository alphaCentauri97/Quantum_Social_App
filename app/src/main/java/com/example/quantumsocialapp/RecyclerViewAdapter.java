package com.example.quantumsocialapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quantumsocialapp.Model.ModelClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelClass> list;

    public RecyclerViewAdapter(Context context, ArrayList<ModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String dateStr = list.get(position).getPublishedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");//format for changing to hour ago
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(dateStr).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            holder.publisheddate.setText(ago+" ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvauthor.setText(list.get(position).getAuthor());
        holder.tvcontent.setText(list.get(position).getDescription());
        holder.mheading.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getUrlToImage()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Webview.class);
                intent.putExtra("url",list.get(position).getUrl());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mheading,publisheddate,tvcontent,tvauthor;
        CardView cardView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mheading = itemView.findViewById(R.id.tvheading);
            tvcontent = itemView.findViewById(R.id.tvcontent);
            tvauthor = itemView.findViewById(R.id.tvauthor);
            publisheddate = itemView.findViewById(R.id.publisheddate);
            imageView = itemView.findViewById(R.id.img);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
