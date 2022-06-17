package com.project.domain;

public class SubscriptionInfo {
    private long id;
    private String name;
    private String description;
    private double sumPerMonth;
    private String imageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSumPerMonth() {
        return sumPerMonth;
    }

    public void setSumPerMonth(double sumPerMonth) {
        this.sumPerMonth = sumPerMonth;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "SubscriptionInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sumPerMonth=" + sumPerMonth +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
