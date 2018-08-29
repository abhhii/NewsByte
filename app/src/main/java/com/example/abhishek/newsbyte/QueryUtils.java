package com.example.abhishek.newsbyte;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.abhishek.newsbyte.MainActivity.LOG_TAG;

public final class QueryUtils {
    private QueryUtils(){
    }
    public static List<News> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse="";
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem mking http request",e);
        }
        List<News> newslist = extractFeatureFromJson(jsonResponse);
        return newslist;
    }



    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building url",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonReasponse = "";
        if(url == null)
            return jsonReasponse;
        HttpURLConnection urlConnection = null;
        InputStream ir = null;
        try{
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                ir = urlConnection.getInputStream();
                jsonReasponse = readFromStream(ir);
            }else{
                Log.e(LOG_TAG,"Error response code: "+urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving retrieving news data");
        }finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(ir != null)
                ir.close();
        }
        return jsonReasponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if( inputStream != null){
            InputStreamReader ir = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(ir);
            String line = br.readLine();
            while (line != null){
                output.append(line);
                line = br.readLine();
            }
        }
        return output.toString();
    }
    private static List<News> extractFeatureFromJson(String jsonResponse){
        if(TextUtils.isEmpty(jsonResponse))
            return null;
        List<News> news = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = baseJsonObject.getJSONArray("results");
            for (int i =0; i< jsonArray.length();i++){
                JSONObject currentNews = jsonArray.getJSONObject(i);
                String title = currentNews.getString("webTitle");
                String section = currentNews.getString("sectionName");
                String date = currentNews.getString("webPublicationDate");
                String url = currentNews.getString("webUrl");

                News news1 = new News(section,date,title,url);
                news.add(news1);
            }
        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing news results");
        }
        return news;
    }
}
