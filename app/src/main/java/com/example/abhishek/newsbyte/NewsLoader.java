package com.example.abhishek.newsbyte;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>>{
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<News> loadInBackground() {
        if(mUrl==null)
        return null;

        List<News> results = QueryUtils.fetchNewsData(mUrl);
        return results;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
