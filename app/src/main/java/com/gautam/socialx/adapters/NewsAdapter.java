package com.gautam.socialx.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gautam.socialx.R;
import com.gautam.socialx.api.SelectListener;
import com.gautam.socialx.model.Articles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>
{
    List<Articles> dataNewsModelList;
    Context context;
    SelectListener selectListener;

    public NewsAdapter(Context context, List<Articles> dataNewsModelList, SelectListener selectListener) {
        this.context=context;
        this.dataNewsModelList = dataNewsModelList;
         this.selectListener = selectListener;
//        Log.d("TAG", "NewsAdapter: "+dataNewsModelList.get(0).getTitle());
    }

    public void updateNewsAdapterData(List<Articles> dataNewsModelList){
        this.dataNewsModelList = dataNewsModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_single_row,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Articles current_pos_data = dataNewsModelList.get(position);

        holder.title.setText(current_pos_data.getTitle());
        holder.description.setText(current_pos_data.getDescription());
        if(current_pos_data.getUrlToImage() != null){
            Glide.with(context).load(current_pos_data.getUrlToImage()).into(holder.news_img);
        }
        holder.time_source.setText(getHoursDiff(current_pos_data.getPublishedAt())+" "+current_pos_data.getSource().getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectListener.OnNewsClicked(current_pos_data);
            }
        });
    }

    private String getHoursDiff(String publishedAt)
    {
        publishedAt = (publishedAt.substring(0, 19)).replace("T"," ");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(publishedAt);
            d2 = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();

        long diffHours = diff / (60 * 60 * 1000);

        System.out.println("Time in hours: " + diffHours + " hours.");

        if(diffHours>58){
            return "Published at "+publishedAt+" by";
        }else{
            return (diffHours+" hours ago by");
        }
    }

    @Override
    public int getItemCount() {
        if(dataNewsModelList!=null)
            return dataNewsModelList.size();
        else
            return 0;
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView title,time_source,description;
        CardView cardView;
        ImageView news_img;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_news);
            cardView = itemView.findViewById(R.id.cardView);
            time_source = itemView.findViewById(R.id.time_source);
            description = itemView.findViewById(R.id.description_news);
            news_img = itemView.findViewById(R.id.news_img);
        }
    }

}