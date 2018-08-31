package com.example.abhishek.newsbyte;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);;
        }
        final News currentNews = getItem(position);

        String datetime = currentNews.getDate();
        String dislayDate = datetime.substring(0,10) + " " + datetime.substring(11,19);
        TextView dateView = (TextView) currentView.findViewById(R.id.date_view);
        dateView.setText(dislayDate);

        String title = currentNews.getTitle();
        TextView titleView = (TextView) currentView.findViewById(R.id.title_view);
        titleView.setText(title);

        String section = currentNews.getSection();
        TextView sectionView = (TextView) currentView.findViewById(R.id.section_name);
        sectionView.setText(section);

        String author = currentNews.getAuthor();
        TextView authorView = (TextView)currentView.findViewById(R.id.author_view);
        authorView.setText(author);

        return currentView;
    }
}
