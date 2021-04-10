package com.example.roushan.railwayenquiry.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.roushan.railwayenquiry.Activities.LiveTrainStatusActivity;
import com.example.roushan.railwayenquiry.Adapters.LiveTrainStatusAdapter;
import com.example.roushan.railwayenquiry.MainActivity;
import com.example.roushan.railwayenquiry.Models.TrainStatus;
import com.example.roushan.railwayenquiry.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowTrainStatusFragment extends Fragment {


    private TextView trainNumber;
    private TextView showError;
    private TextView lastLocationTextView;
    private TextView trainPosition;
    private ArrayAdapter<TrainStatus> adapter;
    private ListView liveStatusListview;
    private ProgressBar trainStatusProgressBar;
    private View view;

    private ArrayList<TrainStatus> routeDetailsArrayList = new ArrayList<>();

    private int[] ids = {R.id.trainNumber_textview, R.id.lastLocation_textview, R.id.headlines};
    public ShowTrainStatusFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_train_status, container, false);

        trainNumber = (TextView) view.findViewById(R.id.trainNumber);
        trainPosition = (TextView) view.findViewById(R.id.lastLocation);
        trainStatusProgressBar = (ProgressBar) view.findViewById(R.id.trainStatus_progressBar);
        showError = (TextView) view.findViewById(R.id.trainNumber_textview);
        lastLocationTextView = (TextView) view.findViewById(R.id.lastLocation_textview);

        new FetchTrainStatus().execute(getString(R.string.railway_URL) + "live/train/" + LiveTrainStatusActivity.getTrainNumber + "/doj/"
                                       + LiveTrainStatusActivity.jsonDate + "/apikey/" + getString(R.string.API_KEY));

        for (int i : ids) {
            View getId = view.findViewById(i);
            getId.setVisibility(View.INVISIBLE);
        }

        liveStatusListview = (ListView) view.findViewById(R.id.trainStatus_listview);
        adapter = new LiveTrainStatusAdapter(getContext(), routeDetailsArrayList);
        liveStatusListview.setDivider(null);
        liveStatusListview.setDividerHeight(0);
        liveStatusListview.setAdapter(adapter);

        return view;
    }

    private class FetchTrainStatus extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trainStatusProgressBar.setVisibility(View.VISIBLE);
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
            adapter.notifyDataSetChanged();
            trainStatusProgressBar.setVisibility(View.INVISIBLE);

            if(!s.isEmpty()) {
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    int responseCode = jsonObject.getInt("response_code");
                    MainActivity.showError("Train Status Response Code : " + String.valueOf(responseCode));

                    if(responseCode == 510) {
                        showError.setText("Train not scheduled to run on the given date.");
                        showError.setTextColor(Color.RED);
                        lastLocationTextView.setVisibility(View.INVISIBLE);
                        trainPosition.setVisibility(View.INVISIBLE);
                        trainNumber.setVisibility(View.INVISIBLE);
                    }

                    if(responseCode == 204) {
                        showError.setText("Empty response from server.");
                        showError.setTextColor(Color.RED);
                        lastLocationTextView.setVisibility(View.INVISIBLE);
                        trainPosition.setVisibility(View.INVISIBLE);
                        trainNumber.setVisibility(View.INVISIBLE);
                    }

                    String position = jsonObject.getString("position");
                    String getTrainNumber = jsonObject.getString("train_number");

                    trainNumber.setText(getTrainNumber);
                    trainPosition.setText(position);

                    JSONArray routeArray = jsonObject.getJSONArray("route");
                    for(int i =0; i<routeArray.length(); i++) {
                        JSONObject routeObject = routeArray.getJSONObject(i);
                        routeDetailsArrayList.add(new TrainStatus(routeObject.getInt("no"), routeObject.getJSONObject("station_").
                                getString("name"), routeObject.getString("scharr"), routeObject.getString("schdep"),
                                routeObject.getString("actarr"), routeObject.getString("actdep"), routeObject.getInt("distance")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
