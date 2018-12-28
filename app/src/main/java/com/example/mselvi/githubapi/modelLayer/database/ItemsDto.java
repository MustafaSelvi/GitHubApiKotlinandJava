package com.example.mselvi.githubapi.modelLayer.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ItemsDto {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private  int repoId;

    private String repoTitle;

    private String description;

    private String userName;

    private String createdAt;

    private String language;

    private String htmlUrl;

    private Integer stars;

    public ItemsDto(String repoTitle, String description, String userName, String createdAt, String language, String htmlUrl, Integer stars, Integer repoId) {
        this.repoTitle = repoTitle;
        this.description = description;
        this.userName = userName;
        this.createdAt = createdAt;
        this.language = language;
        this.htmlUrl = htmlUrl;
        this.stars = stars;
        this.repoId = repoId;

    }

    public String getRepoTitle() {
        return repoTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLanguage() {
        return language;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Integer getStars() {
        return stars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }
}
