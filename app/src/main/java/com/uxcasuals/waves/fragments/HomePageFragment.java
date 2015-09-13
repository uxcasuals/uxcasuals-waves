package com.uxcasuals.waves.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.uxcasuals.waves.MainActivity;
import com.uxcasuals.waves.R;
import com.uxcasuals.waves.adapters.StationsAdapter;
import com.uxcasuals.waves.utils.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {

    private static final String TAG = HomePageFragment.class.getName();

    public StationsAdapter getStationsAdapter() {
        return stationsAdapter;
    }

    public void setStationsAdapter(StationsAdapter stationsAdapter) {
        this.stationsAdapter = stationsAdapter;
    }

    private StationsAdapter stationsAdapter;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    public HomePageFragment() {
        // Required empty public constructor
        Log.v(TAG, "HomePageFragment initialize()");
//        stationsAdapter = new StationsAdapter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        RecyclerView stationsView = (RecyclerView)view.findViewById(R.id.stations_view);
        RecyclerView.LayoutManager layout = new GridLayoutManager(getActivity(), 2);

        stationsView.setLayoutManager(layout);
        stationsView.setAdapter(stationsAdapter);

        PlayerCollapsedFragment playerCollapsedFragment = new PlayerCollapsedFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.player_controls_holder, playerCollapsedFragment)
                .commit();

        slidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelCollapsed(View view) {
                Log.v("TAG", "OnPanelCollapsed");
                Log.v("TAG", "OnPanelExpanded");
                PlayerCollapsedFragment playerCollapsedFragment = new PlayerCollapsedFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.player_controls_holder, playerCollapsedFragment)
                        .commit();
            }

            @Override
            public void onPanelExpanded(View view) {
                Log.v("TAG", "OnPanelExpanded");
                PlayerExpandedFragment playerExpandedFragment = new PlayerExpandedFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.player_controls_holder, playerExpandedFragment)
                        .commit();
            }

            @Override
            public void onPanelAnchored(View view) {
                Log.v("TAG", "OnPanelAnchored");
            }

            @Override
            public void onPanelHidden(View view) {
                Log.v("TAG", "onPanelHidden");
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getInstance().register(this);
        Log.v(TAG, "HomePageFragment start()");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getInstance().register(this);
    }
}
