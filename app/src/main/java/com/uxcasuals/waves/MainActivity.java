package com.uxcasuals.waves;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.uxcasuals.waves.adapters.StationsAdapter;
import com.uxcasuals.waves.asynctasks.StationsNetworkLoader;
import com.uxcasuals.waves.events.DataAvailableEvent;
import com.uxcasuals.waves.events.MediaPlayerInitEvent;
import com.uxcasuals.waves.events.MediaPlayerToggleEvent;
import com.uxcasuals.waves.fragments.HomePageFragment;
import com.uxcasuals.waves.fragments.InitialLoadingFragment;
import com.uxcasuals.waves.services.MusicService;
import com.uxcasuals.waves.utils.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private Toolbar toolbar;
    private StationsAdapter stationsAdapter;
    Messenger mMessenger = null;
    boolean mBound = false;

    public Toolbar getToolbar() {
        return toolbar;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mBound = true;
            Log.v(TAG, "Connection Successfull..");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            mBound = false;
            Log.v(TAG, "Connection Closed..");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getFragmentManager().beginTransaction()
                .replace(R.id.placeholder_fragment, new InitialLoadingFragment())
                .commit();

        new StationsNetworkLoader().execute();
        stationsAdapter = new StationsAdapter();
    }

    @Subscribe
    public void loadHomePageFragment(DataAvailableEvent dataAvailableEvent) {
        HomePageFragment homePageFragment = new HomePageFragment();
        homePageFragment.setStationsAdapter(stationsAdapter);
        EventBus.getInstance().post(new MediaPlayerInitEvent(dataAvailableEvent.getStations().get(0)));
        Log.v(TAG, "loadingHomeFragment");
        getFragmentManager().beginTransaction()
                .replace(R.id.placeholder_fragment, homePageFragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getInstance().unregister(this);
        if(mBound) {
            unbindService(connection);
            mBound = false;
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
//        if (slidingUpPanelLayout != null &&
//                (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
//                    || slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
//            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//        } else {
//            super.onBackPressed();
//        }
    }

    public void toggleMediaPlayer(View view) {
        EventBus.getInstance().post(new MediaPlayerToggleEvent());
    }
}
