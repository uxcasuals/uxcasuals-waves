package com.uxcasuals.waves;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.squareup.otto.Subscribe;
import com.uxcasuals.waves.adapters.StationsAdapter;
import com.uxcasuals.waves.asynctasks.StationsNetworkLoader;
import com.uxcasuals.waves.events.DataAvailableEvent;
import com.uxcasuals.waves.fragments.HomePageFragment;
import com.uxcasuals.waves.fragments.InitialLoadingFragment;
import com.uxcasuals.waves.utils.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private Toolbar toolbar;
    private StationsAdapter stationsAdapter;

    public Toolbar getToolbar() {
        return toolbar;
    }


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
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getInstance().unregister(this);
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
}
