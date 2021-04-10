package com.example.roushan.railwayenquiry.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roushan.railwayenquiry.Models.TrainRoute;
import com.example.roushan.railwayenquiry.R;

import java.util.ArrayList;

/**
 * Created by Roushan on 03-05-2017.
 */

public class SetTrainRouteAdapter extends ArrayAdapter<TrainRoute> {

    private ArrayList<TrainRoute> getRouteArrayList;
    private Context context;

    private class ViewHolder {
        TextView trainNo;
        TextView stationName;
        TextView schArrival;
        TextView schDeparture;
        TextView haltTime;
        TextView day;
        TextView distance;
    }

    public SetTrainRouteAdapter(Context context, ArrayList<TrainRoute> getRouteArrayList) {
        super(context, R.layout.train_route, getRouteArrayList);
        this.context = context;
        this.getRouteArrayList = getRouteArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SetTrainRouteAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.train_route, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.trainNo = (TextView) convertView.findViewById(R.id.number);
            viewHolder.stationName = (TextView) convertView.findViewById(R.id.stationName);
            viewHolder.schArrival = (TextView) convertView.findViewById(R.id.scheduledArrival);
            viewHolder.schDeparture = (TextView) convertView.findViewById(R.id.scheduledDeparture);
            viewHolder.haltTime = (TextView) convertView.findViewById(R.id.halt_time);
            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.trainNo.setText(String.valueOf(getRouteArrayList.get(position).getNumber()));
        viewHolder.stationName.setText(getRouteArrayList.get(position).getStationName());

        if(getRouteArrayList.get(position).getScheduledArrival().equalsIgnoreCase("Source")) {
            viewHolder.schArrival.setText("SRC");
        } else {
            viewHolder.schArrival.setText(getRouteArrayList.get(position).getScheduledArrival());
        }

        if(getRouteArrayList.get(position).getScheduledDeparture().equalsIgnoreCase("Destination")) {
            viewHolder.schDeparture.setText("END");
        } else {
            viewHolder.schDeparture.setText(getRouteArrayList.get(position).getScheduledDeparture());
        }

        viewHolder.haltTime.setText(String.valueOf(getRouteArrayList.get(position).getHalt() + ":00"));
        viewHolder.day.setText(String.valueOf(getRouteArrayList.get(position).getDay()));
        viewHolder.distance.setText(String.valueOf(getRouteArrayList.get(position).getDistance()));

        return convertView;
    }
}
