package com.uxcasuals.waves;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.uxcasuals.waves.adapters.StationsAdapter;
import com.uxcasuals.waves.models.Station;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SlidingUpPanelLayout slidingUpPanelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView stationsView = (RecyclerView)findViewById(R.id.stations_view);
        RecyclerView.LayoutManager layout = new GridLayoutManager(this, 2);

        List<Station> stations = new ArrayList<Station>();
        stations.add(new Station("Radio City", "http://google.com", ""));
        stations.add(new Station("Radio City", "http://google.com", ""));
        stations.add(new Station("Radio City", "http://google.com", ""));
        stations.add(new Station("Radio City", "http://google.com", ""));
        stations.add(new Station("Radio City", "http://google.com", ""));
        stations.add(new Station("Radio City", "http://google.com", ""));
        stations.add(new Station("Radio City", "http://google.com", ""));
        stations.add(new Station("Radio City", "http://google.com", ""));
        StationsAdapter stationsAdapter = new StationsAdapter(stations);

        stationsView.setLayoutManager(layout);
        stationsView.setAdapter(stationsAdapter);

        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
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
    public void onBackPressed() {
        if (slidingUpPanelLayout != null &&
                (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                    || slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
