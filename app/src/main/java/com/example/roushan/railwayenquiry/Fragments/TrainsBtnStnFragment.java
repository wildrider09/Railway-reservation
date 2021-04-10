package com.example.roushan.railwayenquiry.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.roushan.railwayenquiry.Activities.TrainBtnStationsActivity;
import com.example.roushan.railwayenquiry.Adapters.TrainsBtnStnAdapter;
import com.example.roushan.railwayenquiry.Models.Trains;
import com.example.roushan.railwayenquiry.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainsBtnStnFragment extends Fragment {

    private ArrayList<Trains> getTrainsArrayList = new ArrayList<>();
    private ArrayAdapter<Trains> adapter;
    private ProgressBar progressBar;

    public TrainsBtnStnFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trains_btn_stn, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.trains_progressBar);

        new FetchTrains().execute(getString(R.string.railway_URL) + "between/source/" + TrainBtnStationsActivity.getFromCode +
                                 "/dest/" + TrainBtnStationsActivity.getToCode + "/date/" + TrainBtnStationsActivity.getDate +
                                  "/apikey/" + getString(R.string.API_KEY));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView trainsListView = (ListView) getView().findViewById(R.id.trains_listview);
        adapter = new TrainsBtnStnAdapter(getContext(), getTrainsArrayList);
        trainsListView.setAdapter(adapter);
    }

    private class FetchTrains extends AsyncTask<String, Void, String> {

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

            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();

            if(!s.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray trainArray = jsonObject.getJSONArray("train");
                    for (int i=0; i<trainArray.length(); i++) {
                        JSONObject getTrainObject = trainArray.getJSONObject(i);
                        getTrainsArrayList.add(new Trains(getTrainObject.getString("name"), getTrainObject.getString("number"),
                                getTrainObject.getString("src_departure_time"), getTrainObject.getString("dest_arrival_time"),
                                getTrainObject.getJSONObject("from").getString("name"),
                                getTrainObject.getJSONObject("to").getString("name")));
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
