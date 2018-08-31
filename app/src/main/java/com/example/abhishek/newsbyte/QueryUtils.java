package com.example.abhishek.newsbyte;

import android.text.TextUtils;
import android.util.Log;
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
            Log.e(LOG_TAG, "Problem making http request",e);
        }
        catch (Exception e){
            Log.e(LOG_TAG, "Problem making http request",e);
        }
        return extractFeatureFromJson(jsonResponse);
    }



    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building url",e);
        }catch (Exception e){
            Log.e(LOG_TAG, "An unknown error occured!",e);
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
            JSONObject base1JsonObject = baseJsonObject.getJSONObject("response");
            JSONArray jsonArray = base1JsonObject.getJSONArray("results");
            for (int i =0; i< jsonArray.length();i++){
                JSONObject currentNews = jsonArray.getJSONObject(i);
                String title = currentNews.getString("webTitle");
                String section = currentNews.getString("sectionName");
                String date = currentNews.getString("webPublicationDate");
                String url = currentNews.getString("webUrl");
                JSONArray tagsArray = currentNews.getJSONArray("tags");
                JSONObject tagObject = tagsArray.optJSONObject(0);
                String author = "";
                if(tagObject!=null)
                    author = tagObject.getString("webTitle");

                News news1 = new News(section,author,date,title,url);
                news.add(news1);
            }
        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing news results",e);
        }
        return news;
    }
}
