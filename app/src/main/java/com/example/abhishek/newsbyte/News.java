package com.example.abhishek.newsbyte;

public class News {
    private String mSection;
    private String mDate;
    private String mTitle;
    private String mUrl;
    private String mAuthor;

    public News(String section, String author, String date, String title, String url){
        mSection = section;
        mDate = date;
        mTitle = title;
        mUrl = url;
        mAuthor = author;
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

    public String getAuthor() {return mAuthor;}
}
