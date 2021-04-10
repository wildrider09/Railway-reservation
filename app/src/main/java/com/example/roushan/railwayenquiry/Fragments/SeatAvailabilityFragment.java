package com.example.roushan.railwayenquiry.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.roushan.railwayenquiry.Activities.SeatAvailabilityActivity;
import com.example.roushan.railwayenquiry.Adapters.SeatDetailsAdapter;
import com.example.roushan.railwayenquiry.Models.SeatDetails;
import com.example.roushan.railwayenquiry.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SeatAvailabilityFragment extends DialogFragment {

    private TextView trainNameNumber;
    private TextView sourceStation;
    private TextView destinationStation;
    private TextView quota;
    private TextView status;
    private TextView error;
    private ProgressBar progressBar;
    private View view;

    private ArrayList<SeatDetails> seatDetailsArrayList = new ArrayList<>();
    private ArrayAdapter<SeatDetails> adapter;
    private ListView seatDetailsListView;
    private int seatCounter = 1;

    private int[] ids = {R.id.train_name_number_textview, R.id.source_station_textview, R.id.destination_station_textview,
                         R.id.quota_textview, R.id.headlines};

    public SeatAvailabilityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_seat_availability, container, false);

        trainNameNumber = (TextView) view.findViewById(R.id.train_name_number);
        sourceStation   = (TextView) view.findViewById(R.id.source_station);
        destinationStation = (TextView) view.findViewById(R.id.destination_station);
        quota = (TextView) view.findViewById(R.id.quota);
        status = (TextView) view.findViewById(R.id.status);
        error = (TextView) view.findViewById(R.id.error_textview);
        progressBar = (ProgressBar) view.findViewById(R.id.seat_availability_progressBar);

        for(int i : ids) {
            View getId = view.findViewById(i);
            getId.setVisibility(View.INVISIBLE);
        }

        new FetchSeatStatus().execute(getString(R.string.railway_URL)+ "check_seat/train/" + SeatAvailabilityActivity.getTrainNo +
                        "/source/" + SeatAvailabilityActivity.getFromStationCode + "/dest/" + SeatAvailabilityActivity.getToStationCode
                        + "/date/" + SeatAvailabilityActivity.getDate + "/class/" + SeatAvailabilityActivity.getClass + "/quota/" +
                        SeatAvailabilityActivity.getQuota + "/apikey/" + getString(R.string.API_KEY));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        seatDetailsListView = (ListView) getView().findViewById(R.id.seat_status_listview);
        adapter = new SeatDetailsAdapter(getContext(), seatDetailsArrayList);
        seatDetailsListView.setDivider(null);
        seatDetailsListView.setDividerHeight(0);
        seatDetailsListView.setAdapter(adapter);
    }

    private class FetchSeatStatus extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String urlName = params[0];
            String apiResponse = "";
            try {
                URL url = new URL(urlName);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    apiResponse += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return apiResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for (int i : ids) {
                View getId = view.findViewById(i);
                getId.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();

            if(!s.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int responseCode = jsonObject.getInt("response_code");
                    String getError = jsonObject.getString("error");

                    if(responseCode == 204) {
                        error.setVisibility(View.VISIBLE);
                        error.setText(getError);
                        error.setTextColor(Color.RED);
                    }
                    String trainName = jsonObject.getString("train_name");
                    trainNameNumber.setText(trainName);

                    JSONObject fromJsonObject = jsonObject.getJSONObject("from");
                    String fromStationName = fromJsonObject.getString("name");
                    String fromStationCode = fromJsonObject.getString("code");
                    sourceStation.setText(fromStationName + "/" + fromStationCode);

                    JSONObject toJsonObject = jsonObject.getJSONObject("to");
                    String toStationName = toJsonObject.getString("name");
                    String toStationCode = toJsonObject.getString("code");
                    destinationStation.setText(toStationName + "/" + toStationCode);

                    JSONObject classObject = jsonObject.getJSONObject("class");
                    String classCode = classObject.getString("class_code");
                    status.setText("Class - " + classCode);

                    JSONObject quotaObject = jsonObject.getJSONObject("quota");
                    String quotaName = quotaObject.getString("quota_name");
                    String quotaCode = quotaObject.getString("quota_code");
                    quota.setText(quotaName + "/" + quotaCode);

                    JSONArray availabilityArray = jsonObject.getJSONArray("availability");
                    for(int i=0; i<availabilityArray.length(); i++) {
                        JSONObject getAvailabilityObject = availabilityArray.getJSONObject(i);
                        seatDetailsArrayList.add(new SeatDetails(seatCounter, getAvailabilityObject.getString("date"),
                                getAvailabilityObject.getString("status")));
                        seatCounter++;
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
