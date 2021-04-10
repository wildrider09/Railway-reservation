package com.example.roushan.railwayenquiry.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roushan.railwayenquiry.Models.Trains;
import com.example.roushan.railwayenquiry.R;

import java.util.ArrayList;

/**
 * Created by Roushan on 08-05-2017.
 */

public class TrainsBtnStnAdapter extends ArrayAdapter<Trains> {

    private ArrayList<Trains> getTrainsArrayList;
    private Context context;

    private class ViewHolder {
        TextView trainNoName;
        TextView srcDepartureTime;
        TextView destinationArrivalTime;
        TextView fromStationName;
        TextView toDestinationName;
    }

    public TrainsBtnStnAdapter(Context context, ArrayList<Trains> getTrainsArrayList) {
        super(context, R.layout.trains, getTrainsArrayList);
        this.context = context;
        this.getTrainsArrayList = getTrainsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final TrainsBtnStnAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trains, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.trainNoName = (TextView) convertView.findViewById(R.id.train_no_name);
            viewHolder.srcDepartureTime = (TextView) convertView.findViewById(R.id.station_name_time);
            viewHolder.destinationArrivalTime = (TextView) convertView.findViewById(R.id.destination_name_time);
            viewHolder.fromStationName = (TextView) convertView.findViewById(R.id.station_name);
            viewHolder.toDestinationName = (TextView) convertView.findViewById(R.id.destination_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.trainNoName.setText(getTrainsArrayList.get(position).getNumber() + " - " + getTrainsArrayList.get(position).getName());
        viewHolder.srcDepartureTime.setText(getTrainsArrayList.get(position).getSrcDeparture());
        viewHolder.destinationArrivalTime.setText(getTrainsArrayList.get(position).getDestinationArrival());
        viewHolder.fromStationName.setText(getTrainsArrayList.get(position).getFromStationName());
        viewHolder.toDestinationName.setText(getTrainsArrayList.get(position).getToStationName());

        return convertView;
    }
}
