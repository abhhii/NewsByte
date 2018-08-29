package com.example.abhishek.newsbyte;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        final News currentNews = getItem(position);

        String datetime = currentNews.getDate();
        String title = currentNews.getTitle();
        String section = currentNews.getSection();
       // String url = currentNews.getUrl();
        String dislayDate = datetime.substring(0,10) + " " + datetime.substring(11,19);

        TextView sectionView = (TextView) convertView.findViewById(R.id.section_name);
        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        TextView dateView = (TextView) convertView.findViewById(R.id.date_view);

        sectionView.setText(section);
        titleView.setText(title);
        dateView.setText(dislayDate);

        return currentView;
    }
}
