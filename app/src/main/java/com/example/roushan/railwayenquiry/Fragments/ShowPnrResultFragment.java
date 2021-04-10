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

import com.example.roushan.railwayenquiry.Activities.PnrStatusActivity;
import com.example.roushan.railwayenquiry.Adapters.PassengerDetailsAdapter;
import com.example.roushan.railwayenquiry.MainActivity;
import com.example.roushan.railwayenquiry.Models.PassengerDetails;
import com.example.roushan.railwayenquiry.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowPnrResultFragment extends Fragment {

    private TextView trainNameNo;
    private TextView pnrNo;
    private TextView doj;
    private TextView chartPrepared;
    private TextView className;
    private TextView totalPassengers;
    private TextView setBoardingPoint;
    private TextView setReservationUpto;
    private TextView response;
    private ProgressBar pnrProgressBar;
    private View view;

    private ListView passengersListView;
    private ArrayAdapter<PassengerDetails> adapter;

    private ArrayList<PassengerDetails> passengerDetails = new ArrayList<>();

    private int[] ids = {R.id.trainNameNo_textview, R.id.pnr_textview, R.id.doj_textview, R.id.chartPrepared_textview,
                         R.id.className_textview, R.id.totalPassengers_textview, R.id.boardingPoint_textview,
                         R.id.reservationUpto_textview};

    public ShowPnrResultFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_pnr_result, container, false);

        trainNameNo        = (TextView) view.findViewById(R.id.trainNameNo);
        pnrNo              = (TextView) view.findViewById(R.id.pnr);
        doj                = (TextView) view.findViewById(R.id.doj);
        chartPrepared      = (TextView) view.findViewById(R.id.chartPrepared);
        className          = (TextView) view.findViewById(R.id.className);
        totalPassengers    = (TextView) view.findViewById(R.id.totalPassengers);
        setBoardingPoint   = (TextView) view.findViewById(R.id.boardingPoint);
        setReservationUpto = (TextView) view.findViewById(R.id.reservationUpto);
        response           = (TextView) view.findViewById(R.id.response);
        pnrProgressBar     = (ProgressBar) view.findViewById(R.id.pnr_progressBar);

        for (int i : ids) {
            TextView getId = (TextView) view.findViewById(i);
            getId.setVisibility(View.INVISIBLE);
        }

        new FetchPnrResult().execute(getString(R.string.railway_URL) + "pnr_status/pnr/" + PnrStatusActivity.getPnrNumber + "/apikey/" +
                getString(R.string.API_KEY) + "/");

        passengersListView = (ListView) view.findViewById(R.id.passengerDetails_listview);
        adapter = new PassengerDetailsAdapter(getContext(), passengerDetails);
        passengersListView.setDivider(null);
        passengersListView.setDividerHeight(0);
        passengersListView.setAdapter(adapter);

        return view;
    }

    private class FetchPnrResult extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pnrProgressBar.setVisibility(View.VISIBLE);
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
            pnrProgressBar.setVisibility(View.INVISIBLE);

            for(int i : ids) {
                TextView getIds = (TextView) view.findViewById(i);
                getIds.setVisibility(View.VISIBLE);
            }

            if(!s.isEmpty()) {
                try{
                    JSONObject jsonObject = new JSONObject(s);

                    int responseCode = jsonObject.getInt("response_code");
                    MainActivity.showError("PNR Response Code : " + String.valueOf(responseCode));

                    if(responseCode == 200) {
                        response.setText("Your request was successfully processed.");
                        response.setTextColor(Color.GREEN);
                    } else if(responseCode == 204) {
                        response.setText("Empty response. Not able to fetch required data.");
                        response.setTextColor(Color.RED);
                    } else if(responseCode == 401) {
                        response.setText("Authentication Error. You passed an unknown API Key.");
                        response.setTextColor(Color.RED);
                    } else if(responseCode == 403) {
                        response.setText("Quota for the day exhausted. Applicable only for FREE users.");
                        response.setTextColor(Color.RED);
                    } else if(responseCode == 405) {
                        response.setText("Quota for the day exhausted. Applicable only for FREE users.");
                        response.setTextColor(Color.RED);
                    } else if(responseCode == 404 || responseCode == 410) {
                        response.setText("PNR status not found.");
                        response.setTextColor(Color.RED);
                    }

                    String trainName = jsonObject.getString("train_name");
                    String trainNum = jsonObject.getString("train_num");
                    String pnr = jsonObject.getString("pnr");
                    String dateJourney = jsonObject.getString("doj");
                    String chart = jsonObject.getString("chart_prepared");
                    String getClassName = jsonObject.getString("class");
                    String getTotalPassengers = jsonObject.getString("total_passengers");

                    trainNameNo.setText(trainName+"/"+trainNum);
                    pnrNo.setText(pnr);
                    doj.setText(dateJourney);
                    chartPrepared.setText(chart);
                    className.setText(getClassName);
                    totalPassengers.setText(getTotalPassengers);

                    JSONObject boardingPoint = jsonObject.getJSONObject("boarding_point");
                    String code_boardingPoint = boardingPoint.getString("code");
                    String name_boardingPoint = boardingPoint.getString("name");

                    setBoardingPoint.setText(name_boardingPoint + "/" + code_boardingPoint);

                    JSONObject reservationUpto = jsonObject.getJSONObject("reservation_upto");
                    String code_reservationUpto = reservationUpto.getString("code");
                    String name_reservationUpto = reservationUpto.getString("name");

                    setReservationUpto.setText(name_reservationUpto + "/" + code_reservationUpto);


                    JSONArray passengers = jsonObject.getJSONArray("passengers");
                    for(int i=0; i<passengers.length(); i++) {
                        JSONObject getPassengerDetails = passengers.getJSONObject(i);
                        passengerDetails.add(new PassengerDetails(getPassengerDetails.getInt("no"), getPassengerDetails.getString(
                                "booking_status"), getPassengerDetails.getString("current_status"), getPassengerDetails.getInt(
                                        "coach_position")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
