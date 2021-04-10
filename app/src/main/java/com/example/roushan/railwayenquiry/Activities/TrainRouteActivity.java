package com.example.roushan.railwayenquiry.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roushan.railwayenquiry.Fragments.TrainRouteFragment;
import com.example.roushan.railwayenquiry.R;

public class TrainRouteActivity extends AppCompatActivity {

    private EditText mEnterTrainNumber;
    private Button mSearchTrainRoute;
    public static String getTrainNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_route);

        setViewsToActivity();
        onClickSearchButton();
    }

    private void setViewsToActivity() {
        mEnterTrainNumber = (EditText) findViewById(R.id.enter_train_number);
        mSearchTrainRoute = (Button) findViewById(R.id.search_train_route);
    }

    private void onClickSearchButton() {
        mSearchTrainRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getTrainNumber = mEnterTrainNumber.getText().toString().trim();

                    if(getTrainNumber.length() != 5) {
                        mEnterTrainNumber.setError("Please enter correct train number");
                    } else {
                        TrainRouteFragment fragment = new TrainRouteFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.train_route_container, fragment);
                        transaction.commit();
                    }
                } else {
                    Toast.makeText(TrainRouteActivity.this, "Network unavailable. Please check and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
