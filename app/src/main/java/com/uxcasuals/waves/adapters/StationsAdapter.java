package com.uxcasuals.waves.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uxcasuals.waves.R;
import com.uxcasuals.waves.models.Station;

import java.util.List;

/**
 * Created by Dhakchianandan on 12/09/15.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {

    private List<Station> stations;

    public StationsAdapter(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public StationsAdapter.ViewHolder onCreateViewHolder(ViewGroup application, int i) {
        CardView stationView = (CardView) LayoutInflater.from(application.getContext())
                .inflate(R.layout.station_view, application, false);
        return new ViewHolder(stationView);
    }

    @Override
    public void onBindViewHolder(StationsAdapter.ViewHolder viewHolder, int position) {
        CardView stationView = viewHolder.staionView;
        TextView stationNameView = (TextView) stationView.findViewById(R.id.station_name);
        ImageView stationImageView = (ImageView) stationView.findViewById(R.id.station_logo);

        Station station = stations.get(position);
        stationNameView.setText(station.getName());
        stationImageView.setImageResource(R.drawable.ic_radiocity);

    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView staionView;
        public ViewHolder(CardView view) {
            super(view);
            staionView = view;
        }
    }
}
