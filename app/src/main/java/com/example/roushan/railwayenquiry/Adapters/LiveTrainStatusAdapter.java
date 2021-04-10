package com.example.roushan.railwayenquiry.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roushan.railwayenquiry.Models.TrainStatus;
import com.example.roushan.railwayenquiry.R;

import java.util.ArrayList;

/**
 * Created by Roushan on 29-04-2017.
 */

public class LiveTrainStatusAdapter extends ArrayAdapter<TrainStatus> {

    private ArrayList<TrainStatus> getTrainDetailsArrayList;
    private Context context;

    private class ViewHolder {
        TextView number;
        TextView stationName;
        TextView sch_arrival;
        TextView sch_departure;
        TextView actualArrival;
        TextView actualDeparture;
        TextView distance;
    }

    public LiveTrainStatusAdapter(Context context, ArrayList<TrainStatus> getTrainDetailsArrayList) {
        super(context, R.layout.train_status, getTrainDetailsArrayList);
        this.context = context;
        this.getTrainDetailsArrayList = getTrainDetailsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LiveTrainStatusAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.train_status, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.number          = (TextView) convertView.findViewById(R.id.number);
            viewHolder.stationName     = (TextView) convertView.findViewById(R.id.stationName);
            viewHolder.sch_arrival     = (TextView) convertView.findViewById(R.id.scheduledArrival);
            viewHolder.sch_departure   = (TextView) convertView.findViewById(R.id.scheduledDeparture);
            viewHolder.actualArrival   = (TextView) convertView.findViewById(R.id.actualArrival);
            viewHolder.actualDeparture = (TextView) convertView.findViewById(R.id.actualDeparture);
            viewHolder.distance        = (TextView) convertView.findViewById(R.id.distance);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.number.setText(String.valueOf(getTrainDetailsArrayList.get(position).getStationNumber()));
        viewHolder.stationName.setText(getTrainDetailsArrayList.get(position).getStationName());
        if(getTrainDetailsArrayList.get(position).getScheduledArrival().equalsIgnoreCase("Source")) {
            viewHolder.sch_arrival.setText("SRC");
        } else {
            viewHolder.sch_arrival.setText(getTrainDetailsArrayList.get(position).getScheduledArrival());
        }
        if(getTrainDetailsArrayList.get(position).getScheduledDeparture().equalsIgnoreCase("Destination")) {
            viewHolder.sch_departure.setText("END");
        } else {
            viewHolder.sch_departure.setText(getTrainDetailsArrayList.get(position).getScheduledDeparture());
        }
        if(getTrainDetailsArrayList.get(position).getActualArrival().equalsIgnoreCase("00:00")) {
            viewHolder.actualArrival.setText("SRC");
        } else {
            viewHolder.actualArrival.setText(getTrainDetailsArrayList.get(position).getActualArrival());
        }
        if(getTrainDetailsArrayList.get(position).getActualDeparture().equalsIgnoreCase("00:00")) {
            viewHolder.actualDeparture.setText("END");
        } else {
            viewHolder.actualDeparture.setText(getTrainDetailsArrayList.get(position).getActualDeparture());
        }
        viewHolder.distance.setText(String.valueOf(getTrainDetailsArrayList.get(position).getDistance()));

        return convertView;
    }
}
