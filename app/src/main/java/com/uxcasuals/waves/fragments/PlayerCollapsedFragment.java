package com.uxcasuals.waves.fragments;


import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.uxcasuals.waves.R;
import com.uxcasuals.waves.events.PlayerControlsChangeEvent;
import com.uxcasuals.waves.events.StationChangeEvent;
import com.uxcasuals.waves.utils.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerCollapsedFragment extends Fragment {

    ImageView stationLogo;
    TextView stationName;
    ImageButton playPauseBtn;

    public PlayerCollapsedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_collapsed, container, false);
        stationLogo = (ImageView) view.findViewById(R.id.playing_station_logo);
        stationName = (TextView) view.findViewById(R.id.playing_station_name);
        playPauseBtn = (ImageButton)view.findViewById(R.id.playing_station_state_icons);
        return view;
    }

    @Subscribe
    public void changePlayerControls(PlayerControlsChangeEvent playerControlsChangeEvent) {
        if(playerControlsChangeEvent.state == PlayerControlsChangeEvent.SHOW_PLAY_ICON) {
            playPauseBtn.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);

            Resources resources = getResources();
            String name = String.format(resources.getString(R.string.favourite_station_playing),
                    resources.getString(R.string.listen), playerControlsChangeEvent.getStation().getName());
            stationName.setText(name);

        } else if (playerControlsChangeEvent.state == PlayerControlsChangeEvent.SHOW_PAUSE_ICON) {
            playPauseBtn.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);

            Resources resources = getResources();
            String name = String.format(resources.getString(R.string.favourite_station_playing),
                    resources.getString(R.string.listening), playerControlsChangeEvent.getStation().getName());
            stationName.setText(name);
        }
    }

    @Subscribe
    public void changeStationDetails(StationChangeEvent stationChangeEvent) {
        Resources resources = getResources();
        String name = String.format(resources.getString(R.string.favourite_station_playing),
                resources.getString(R.string.listening), stationChangeEvent.getStation().getName());
        stationName.setText(name);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getInstance().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getInstance().unregister(this);
        super.onStop();
    }
}
