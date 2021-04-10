package com.example.roushan.railwayenquiry.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roushan.railwayenquiry.Models.SeatDetails;
import com.example.roushan.railwayenquiry.R;

import java.util.ArrayList;

/**
 * Created by Roushan on 06-05-2017.
 */

public class SeatDetailsAdapter extends ArrayAdapter<SeatDetails> {

    private ArrayList<SeatDetails> getSeatDetalisArrayList;
    private Context context;

    private class ViewHolder {
        private TextView setNumber;
        private TextView setDate;
        private TextView setStatus;
    }

    public SeatDetailsAdapter(Context context, ArrayList<SeatDetails> getSeatDetalisArrayList) {
        super(context, R.layout.seat_details, getSeatDetalisArrayList);
        this.context = context;
        this.getSeatDetalisArrayList = getSeatDetalisArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SeatDetailsAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.seat_details, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.setNumber = (TextView) convertView.findViewById(R.id.number);
            viewHolder.setDate = (TextView) convertView.findViewById(R.id.set_date);
            viewHolder.setStatus = (TextView) convertView.findViewById(R.id.set_status);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setNumber.setText(String.valueOf(getSeatDetalisArrayList.get(position).getSerialNumber()));
        viewHolder.setDate.setText(getSeatDetalisArrayList.get(position).getDate());
        viewHolder.setStatus.setText(getSeatDetalisArrayList.get(position).getStatus());

        return convertView;
    }
}
