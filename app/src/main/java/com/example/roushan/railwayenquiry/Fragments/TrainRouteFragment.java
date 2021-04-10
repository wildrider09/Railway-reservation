package com.example.roushan.railwayenquiry.Fragments;

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

import com.example.roushan.railwayenquiry.Activities.TrainRouteActivity;
import com.example.roushan.railwayenquiry.Adapters.SetTrainRouteAdapter;
import com.example.roushan.railwayenquiry.Models.TrainRoute;
import com.example.roushan.railwayenquiry.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainRouteFragment extends Fragment {

    private TextView trainNameNumber;
    private TextView sunday;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private ProgressBar trainRouteProgressBar;
    private View view;

    private String[] runningDays = new String[7];
    private ArrayList<TrainRoute> routeArrayList = new ArrayList<>();
    private ListView trainRouteListView;
    private ArrayAdapter<TrainRoute> adapter;

    private int counter = 0;

    private int[] ids = {R.id.trainNumberName_textview, R.id.trainNumberName, R.id.runsOnLayout, R.id.headlines};

    public TrainRouteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_train_route, container, false);

        trainNameNumber = (TextView) view.findViewById(R.id.trainNumberName);
        sunday          = (TextView) view.findViewById(R.id.sunday_textview);
        monday          = (TextView) view.findViewById(R.id.monday_textview);
        tuesday         = (TextView) view.findViewById(R.id.tuesday_textview);
        wednesday       = (TextView) view.findViewById(R.id.wednesday_textview);
        thursday        = (TextView) view.findViewById(R.id.thursday_textview);
        friday          = (TextView) view.findViewById(R.id.friday_textview);
        saturday        = (TextView) view.findViewById(R.id.saturday_textview);

        trainRouteProgressBar = (ProgressBar) view.findViewById(R.id.trainRoute_progressBar);

        for (int i : ids) {
            View getId = view.findViewById(i);
            getId.setVisibility(View.INVISIBLE);
        }

        new FetchTrainRoute().execute(getString(R.string.railway_URL) + "route/train/" + TrainRouteActivity.getTrainNumber +
                                      "/apikey/" + getString(R.string.API_KEY));

        trainRouteListView = (ListView) view.findViewById(R.id.set_trainRoute_listview);
        adapter = new SetTrainRouteAdapter(getContext(), routeArrayList);
        trainRouteListView.setDivider(null);
        trainRouteListView.setDividerHeight(0);
        trainRouteListView.setAdapter(adapter);

        return view;
    }

    private class FetchTrainRoute extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trainRouteProgressBar.setVisibility(View.VISIBLE);
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

            adapter.notifyDataSetChanged();
            trainRouteProgressBar.setVisibility(View.INVISIBLE);

            for (int i : ids) {
                View getId = view.findViewById(i);
                getId.setVisibility(View.VISIBLE);
            }

            if(!s.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject trainObject = jsonObject.getJSONObject("train");

                    String getTrainNumber = trainObject.getString("number");
                    String getTrainName = trainObject.getString("name");

                    trainNameNumber.setText(getTrainName+"/"+getTrainNumber);

                    JSONArray routeArray = jsonObject.getJSONArray("route");
                    for(int i=0; i<routeArray.length(); i++) {
                        JSONObject routeObject = routeArray.getJSONObject(i);
                        routeArrayList.add(new TrainRoute(routeObject.getInt("no"), routeObject.getInt("distance"),
                                routeObject.getInt("day"), routeObject.getInt("halt"), routeObject.getString("fullname"),
                                routeObject.getString("scharr"), routeObject.getString("schdep")));
                    }

                    JSONArray daysArray = trainObject.getJSONArray("days");
                    for(int i=0; i<daysArray.length(); i++) {
                        JSONObject getRunningDay = daysArray.getJSONObject(i);
                        runningDays[counter] = getRunningDay.getString("runs");
                        counter++;
                    }

                    if(runningDays[0].equalsIgnoreCase("Y")) {
                        sunday.setText("SUN");
                    } else {
                        sunday.setText("----");
                    }

                    if(runningDays[1].equalsIgnoreCase("Y")) {
                        monday.setText("MON");
                    } else {
                        monday.setText("----");
                    }

                    if(runningDays[2].equalsIgnoreCase("Y")) {
                        tuesday.setText("TUE");
                    } else {
                        tuesday.setText("----");
                    }

                    if(runningDays[3].equalsIgnoreCase("Y")) {
                        wednesday.setText("WED");
                    } else {
                        wednesday.setText("----");
                    }

                    if(runningDays[4].equalsIgnoreCase("Y")) {
                        thursday.setText("THU");
                    } else {
                        thursday.setText("----");
                    }

                    if(runningDays[5].equalsIgnoreCase("Y")) {
                        friday.setText("FRI");
                    } else {
                        friday.setText("----");
                    }

                    if(runningDays[6].equalsIgnoreCase("Y")) {
                        saturday.setText("SAT");
                    } else {
                        saturday.setText("----");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
