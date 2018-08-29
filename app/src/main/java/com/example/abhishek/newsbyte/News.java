package com.example.abhishek.newsbyte;

public class News {
    private String mSection;
    private String mDate;
    private String mTitle;
    private String mUrl;

    public News(String section, String date, String title, String url){
        mSection = section;
        mDate = date;
        mTitle = title;
        mUrl = url;
    }

    public String getDate() {
        return mDate;
    }

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
