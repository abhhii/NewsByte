package com.example.abhishek.newsbyte;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String GUARDIAN_URL =
            "https://content.guardianapis.com/search?api-key=cac13f46-c9fb-4427-982f-5fc30616dc14";
    private TextView mEmptyStateTextView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkIndicator = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkIndicator!=null && networkIndicator.isConnectedOrConnecting();

        ListView newsView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView)findViewById(R.id.empty_view);
        newsView.setEmptyView(mEmptyStateTextView);

        if(!isConnected)
            mEmptyStateTextView.setText(R.string.no_internet);
    }
}
