package com.example.abhishek.newsbyte;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String GUARDIAN_URL =
            "https://content.guardianapis.com/search?api-key=cac13f46-c9fb-4427-982f-5fc30616dc14";
    public static final String LOG_TAG = MainActivity.class.getName();
    private TextView mEmptyStateTextView;
    private NewsAdapter mAdapter;
    private static final int LOADER_ID = 0;
    private LoaderManager loaderManager=null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkIndicator = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkIndicator != null && networkIndicator.isConnectedOrConnecting();

        ListView newsView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsView.setEmptyView(mEmptyStateTextView);

        if (!isConnected)
            mEmptyStateTextView.setText(R.string.no_internet);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsView.setAdapter(mAdapter);

        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news1 = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(news1.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handleUpdateRequest();
                swipe.setRefreshing(false);
            }
        });
    }

    public void handleUpdateRequest(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkIndicator = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkIndicator != null && networkIndicator.isConnectedOrConnecting();

        ListView newsView = (ListView) findViewById(R.id.list);
        newsView.setEmptyView(mEmptyStateTextView);

        if (!isConnected)
            mEmptyStateTextView.setText(R.string.no_internet);

        loaderManager.restartLoader(LOADER_ID,null, this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        SharedPreferences shredPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minNews = shredPrefs.getString(
                    getString(R.string.settings_min_news_key),
                    getString(R.string.settings_min_news_default));

        String orderBy = shredPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(GUARDIAN_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format","json");
        uriBuilder.appendQueryParameter("page-size",minNews);
        uriBuilder.appendQueryParameter("order-by",orderBy);
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_news);
        mAdapter.clear();
        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
