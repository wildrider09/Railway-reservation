package com.example.roushan.railwayenquiry.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roushan.railwayenquiry.Models.PassengerDetails;
import com.example.roushan.railwayenquiry.R;

import java.util.ArrayList;

/**
 * Created by Roushan on 27-04-2017.
 */

public class PassengerDetailsAdapter extends ArrayAdapter<PassengerDetails> {

    private ArrayList<PassengerDetails> passengerDetails;
    private Context context;

    private class ViewHolder {
        TextView passengerNo;
        TextView coachPosition;
        TextView bookingStatus;
        TextView currentStatus;
    }

    public PassengerDetailsAdapter(Context context, ArrayList<PassengerDetails> passengerDetails) {
        super(context, R.layout.passenger_details, passengerDetails);
        this.passengerDetails = passengerDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.passenger_details, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.passengerNo   = (TextView) convertView.findViewById(R.id.passengerNo);
            viewHolder.coachPosition = (TextView) convertView.findViewById(R.id.coachPosition);
            viewHolder.bookingStatus = (TextView) convertView.findViewById(R.id.bookingStatus);
            viewHolder.currentStatus = (TextView) convertView.findViewById(R.id.currentStatus);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.passengerNo.setText(String.valueOf(passengerDetails.get(position).getPassengerNo()));
        viewHolder.coachPosition.setText(String.valueOf(passengerDetails.get(position).getCoachPosition()));
        viewHolder.bookingStatus.setText(passengerDetails.get(position).getBookingStatus());
        viewHolder.currentStatus.setText(passengerDetails.get(position).getCurrrentStatus());

        return convertView;
    }
}
