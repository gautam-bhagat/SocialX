package com.gautam.socialx.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.gautam.socialx.R;
import com.gautam.socialx.databinding.ActivityNewsBinding;
import com.gautam.socialx.model.Articles;

public class NewsActivity extends AppCompatActivity {

    Articles articles;
    ActivityNewsBinding news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news);
        news = DataBindingUtil.setContentView(this,R.layout.activity_news);

        articles = (Articles) getIntent().getSerializableExtra("article");

        initUI();
    }

    private void initUI() {
        news.newsHeadline.setText(articles.getTitle());
        if(articles.getUrlToImage() != null)
            Glide.with(getApplicationContext()).load(articles.getUrlToImage()).into(news.newsImage);
        news.newsAuthor.setText(articles.getAuthor());
        news.newsTime.setText(articles.getPublishedAt());
        news.newsDetail.setText(articles.getDescription());
        news.newsContent.setText(articles.getContent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}