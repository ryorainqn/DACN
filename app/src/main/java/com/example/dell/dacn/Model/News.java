package com.example.dell.dacn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DELL on 11/10/17.
 */

public class News implements Serializable {
    @SerializedName("new_id")
    @Expose
    private  String new_Id;
    @SerializedName("title")
    @Expose
    private  String title;
    @SerializedName("content")
    @Expose
    private String description;
    @SerializedName("create_at")
    private String create_At;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("url_news")
    @Expose
    private String url_news;

    public News(String new_Id, String title, String description, String create_At,
                String author, String video, String image, String genre) {
        this.new_Id = new_Id;
        this.title = title;
        this.description = description;
        this.create_At = create_At;
        this.author = author;
        this.video = video;
        this.image = image;
        this.genre = genre;
    }

    public String getNew_Id() {
        return new_Id;
    }

    public void setNew_Id(String new_Id) {
        this.new_Id = new_Id;
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

    public String getCreate_At() {
        return create_At;
    }

    public void setCreate_At(String create_At) {
        this.create_At = create_At;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUrl_news() {
        return url_news;
    }

    public void setUrl_news(String url_news) {
        this.url_news = url_news;
    }
}
