package com.example.vegantranslations.view.pojo;

public class AlternativePojoAsRowItem {
    private String title;
    private String description;
    private String imageUrl;

    public AlternativePojoAsRowItem(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
