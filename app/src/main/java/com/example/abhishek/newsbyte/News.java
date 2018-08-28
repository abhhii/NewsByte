package com.example.abhishek.newsbyte;

public class News {
    private String mSection;
    private long mDate;
    private String mTitle;
    private String mUrl;

    public News(String section, long date, String title, String url){
        mSection = section;
        mDate = date;
        mTitle = title;
        mUrl = url;
    }

    public long getDate() {
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
