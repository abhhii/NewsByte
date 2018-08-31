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
        MainActivity.ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);;
            holder = new MainActivity.ViewHolder();
            holder.dateTextView = (TextView) convertView.findViewById(R.id.date_view);
            holder.authorTextView = (TextView)convertView.findViewById(R.id.author_view);
            holder.sectionTextView = (TextView) convertView.findViewById(R.id.section_name);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title_view);
            convertView.setTag(holder);
        }
        else {
            holder = (MainActivity.ViewHolder) convertView.getTag();
        }

        final News currentNews = getItem(position);

        String datetime = currentNews.getDate();
        String dislayDate = datetime.substring(0,10) + " " + datetime.substring(11,19);
        holder.dateTextView.setText(dislayDate);

        String title = currentNews.getTitle();
        holder.titleTextView.setText(title);

        String section = currentNews.getSection();
        holder.sectionTextView.setText(section);

        String author = currentNews.getAuthor();
        holder.authorTextView.setText(author);

        return convertView;
    }
}
