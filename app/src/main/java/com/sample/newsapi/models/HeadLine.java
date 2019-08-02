package com.sample.newsapi.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "headline_table")
public class HeadLine implements Serializable {

    @PrimaryKey
    @NonNull
    String title;

    String name;
    String description;
    String contentUrl;

    String imageUrl;

    public HeadLine(@NonNull String name, String title, String description, String contentUrl, String imageUrl) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.contentUrl = contentUrl;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
