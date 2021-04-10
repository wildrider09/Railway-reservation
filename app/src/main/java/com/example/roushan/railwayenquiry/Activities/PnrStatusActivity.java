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

import com.example.roushan.railwayenquiry.Fragments.ShowPnrResultFragment;
import com.example.roushan.railwayenquiry.R;

public class PnrStatusActivity extends AppCompatActivity {

    private EditText mEnterPnr;
    private Button mSearchPnr;
    public static String getPnrNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_status);

        setViewsToActivity();
        onClickSearchButton();
    }

    private void setViewsToActivity() {
        mEnterPnr = (EditText) findViewById(R.id.enter_pnr);
        mSearchPnr = (Button) findViewById(R.id.search_pnr_status);
    }

    private void onClickSearchButton() {
        mSearchPnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()) {
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getPnrNumber = mEnterPnr.getText().toString().trim();

                    if (getPnrNumber.length() != 10) {
                        mEnterPnr.setError("Please enter correct PNR number");
                    } else {
                        ShowPnrResultFragment fragment = new ShowPnrResultFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.show_pnr_status, fragment);
                        transaction.commit();
                    }
                } else {
                    Toast.makeText(PnrStatusActivity.this, "Network unavailable. Please check and try again.", Toast.LENGTH_SHORT).show();
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
