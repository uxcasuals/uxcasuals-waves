package com.uxcasuals.waves.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.uxcasuals.waves.asynctasks.BitmapLoader;

/**
 * Created by ivy4804 on 9/18/2015.
 */
public class AlbumArtCache {

    private static final String TAG = AlbumArtCache.class.getName();

    private static final int MAX_ALBUM_ART_CACHE_SIZE = 12*1024*1024;

    private static final AlbumArtCache sInstance = new AlbumArtCache();

    private final LruCache<String, Bitmap> mCache;

    public static AlbumArtCache getInstance() {
        return sInstance;
    }

    private AlbumArtCache() {
        // Holds no more than MAX_ALBUM_ART_CACHE_SIZE bytes, bounded by maxmemory/4 and
        // Integer.MAX_VALUE:
        int maxSize = Math.min(MAX_ALBUM_ART_CACHE_SIZE,
                (int) (Math.min(Integer.MAX_VALUE, Runtime.getRuntime().maxMemory()/4)));
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void fetchBitmap(String SERVER_URL, ImageView imageView) {
        if(mCache.get(SERVER_URL) != null) {
//            Log.v(TAG, "Fetching from cache");
            imageView.setImageBitmap(mCache.get(SERVER_URL));
        } else {
//            Log.v(TAG, "Fetching from internet");
            new BitmapLoader(imageView, SERVER_URL, mCache).execute();
        }
    }
}
