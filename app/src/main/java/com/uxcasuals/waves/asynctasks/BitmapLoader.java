package com.uxcasuals.waves.asynctasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dhakchianandan on 14/09/15.
 */
public class BitmapLoader extends AsyncTask {

    private LruCache<String, Bitmap> mCache;
    ImageView imageView;
    String SERVER_URL;

    public BitmapLoader(ImageView imageView, String SERVER_URL, LruCache<String, Bitmap> mCache) {
        this.imageView = imageView;
        this.SERVER_URL = SERVER_URL;
        this.mCache = mCache;
    }

    public BitmapLoader(ImageView imageView, String SERVER_URL) {
        this.imageView = imageView;
        this.SERVER_URL = SERVER_URL;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int response = httpURLConnection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                mCache.put(SERVER_URL, bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Bitmap bitmap = (Bitmap) o;
        imageView.setImageBitmap(bitmap);
    }
}
