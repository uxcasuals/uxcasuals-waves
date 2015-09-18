package com.uxcasuals.waves.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.uxcasuals.waves.events.MediaPlayerInitEvent;
import com.uxcasuals.waves.events.MediaPlayerToggleEvent;
import com.uxcasuals.waves.events.PlayerControlsChangeEvent;
import com.uxcasuals.waves.events.StationChangeEvent;
import com.uxcasuals.waves.models.Station;
import com.uxcasuals.waves.utils.EventBus;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private static final String TAG = MusicService.class.getName();
    private static final String WIFI_TAG = "WIFI_LOCK";
    private static final Object NULL = null;

    public static final int MEDIA_PLAYER_INIT = 0;
    public static boolean MEDIA_PLAYER_IN_PAUSE_STATE = false;

    private MediaPlayer mMediaPlayer = null;
    private WifiManager.WifiLock wifiLock;
    private Station station;

    class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Subscribe
    public void initMediaPlayer(MediaPlayerInitEvent mediaPlayerInitEvent) {
        station = mediaPlayerInitEvent.getStation();
        if(mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, MusicService.WIFI_TAG);
            wifiLock.acquire();

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnErrorListener(this);
        }
    }

    @Subscribe
    public void startMediaPlayer(StationChangeEvent stationChangeEvent) {
        if(stationChangeEvent != null) {
            station = stationChangeEvent.getStation();
        }
        if(mMediaPlayer != null) {
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(station.getUrl());
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseMediaPlayer() {
        mMediaPlayer.pause();
        MEDIA_PLAYER_IN_PAUSE_STATE = true;
        EventBus.getInstance().post(new PlayerControlsChangeEvent(PlayerControlsChangeEvent.SHOW_PLAY_ICON, station));
    }

    public void resumeMediaPlayer() {
        mMediaPlayer.start();
        MEDIA_PLAYER_IN_PAUSE_STATE = false;
        EventBus.getInstance().post(new PlayerControlsChangeEvent(PlayerControlsChangeEvent.SHOW_PAUSE_ICON, station));
    }

    public void stopMediaPlayer() {
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            wifiLock.release();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Subscribe
    public void toggleMediaPlayer(MediaPlayerToggleEvent mediaPlayerToggleEvent) {
        if(mMediaPlayer != null) {
            if(mMediaPlayer.isPlaying()) {
                pauseMediaPlayer();
            } else {
                if(MEDIA_PLAYER_IN_PAUSE_STATE) {
                    resumeMediaPlayer();
                } else {
                    startMediaPlayer((StationChangeEvent) NULL);
                }
            }
        }
    }

    final Messenger messenger = new Messenger(new MessageHandler());

    @Override
    public void onPrepared(MediaPlayer mMediaPlayer) {
        Toast.makeText(getApplicationContext(), "Playing " + station.getName(), Toast.LENGTH_SHORT).show();
        resumeMediaPlayer();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(getApplicationContext(), "Problem with streaming. Try other stations!!", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        EventBus.getInstance().register(this);
        return messenger.getBinder();
    }
}
