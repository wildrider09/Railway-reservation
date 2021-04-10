package com.example.roushan.railwayenquiry;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.roushan.railwayenquiry.Activities.LiveTrainStatusActivity;
import com.example.roushan.railwayenquiry.Activities.PnrStatusActivity;
import com.example.roushan.railwayenquiry.Activities.SeatAvailabilityActivity;
import com.example.roushan.railwayenquiry.Activities.TrainBtnStationsActivity;
import com.example.roushan.railwayenquiry.Activities.TrainRouteActivity;

public class MainActivity extends AppCompatActivity {

    private Button mPnrStatus;
    private Button mLiveTrainStatus;
    private Button mTrainRoute;
    private Button mSeatAvailability;
    private Button mTrainBtnStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Network unavailable. Please check and try again.", Toast.LENGTH_LONG).show();
        }

        setViewsToActivity();
        onClickLiveTrainStatus();
        onClickPnrStatus();
        onClickTrainRoute();
        onClickSeatAvailability();
        onClickTrainBtnStations();
    }

    private void setViewsToActivity() {
        mPnrStatus = (Button) findViewById(R.id.pnr_status);
        mLiveTrainStatus = (Button) findViewById(R.id.liveTrain_status);
        mTrainRoute = (Button) findViewById(R.id.train_route);
        mSeatAvailability = (Button) findViewById(R.id.seat_availability);
        mTrainBtnStations = (Button) findViewById(R.id.train_between_stations);
    }

    private void onClickLiveTrainStatus() {
        mLiveTrainStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LiveTrainStatusActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickPnrStatus() {
        mPnrStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PnrStatusActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickTrainRoute() {
        mTrainRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainRouteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickSeatAvailability() {
        mSeatAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeatAvailabilityActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickTrainBtnStations() {
        mTrainBtnStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainBtnStationsActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void showError(String s) {
        Log.d("RailwayEnquiry", s);
    }
}
