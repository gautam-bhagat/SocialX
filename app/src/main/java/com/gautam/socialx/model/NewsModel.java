package com.gautam.socialx.model;

public class NewsModel {
    String title;
    String description;
    String publishedAt;
    String source_of_news;
    String news_img_url;

    public NewsModel(String title, String description, String publishedAt, String source_of_news, String news_img_url) {
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.source_of_news = source_of_news;
        this.news_img_url = news_img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource_of_news() {
        return source_of_news;
    }

    public void setSource_of_news(String source_of_news) {
        this.source_of_news = source_of_news;
    }

    public String getNews_img_url() {
        return news_img_url;
    }

    public void setNews_img_url(String news_img_url) {
        this.news_img_url = news_img_url;
    }
}
