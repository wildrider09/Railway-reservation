package com.example.roushan.railwayenquiry.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roushan.railwayenquiry.Fragments.TrainsBtnStnFragment;
import com.example.roushan.railwayenquiry.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TrainBtnStationsActivity extends AppCompatActivity {

    private EditText setFromCode;
    private EditText setToCode;
    private EditText setDate;
    private Button search;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat jsonDate;
    private DatePickerDialog setDateDialog;

    public static String getFromCode;
    public static String getToCode;
    public static String getDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_btn_stations);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        jsonDate = new SimpleDateFormat("dd-MM", Locale.US);

        setViewsToActivity();
        onClickSetDate();
        onClickSearchButton();
    }

    private void setViewsToActivity() {
        setFromCode = (EditText) findViewById(R.id.source_stationCode);
        setToCode = (EditText) findViewById(R.id.destination_stationCode);
        setDate = (EditText) findViewById(R.id.set_date);
        search = (Button) findViewById(R.id.search_trains);
    }

    private void onClickSetDate() {
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateDialog.show();
            }
        });
        Calendar myCalendar = Calendar.getInstance();
        setDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                setDate.setText(dateFormatter.format(newDate.getTime()));
                getDate = jsonDate.format(newDate.getTime());
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void onClickSearchButton() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    getFromCode = setFromCode.getText().toString();
                    getToCode = setToCode.getText().toString();
                    String checkDate = setDate.getText().toString();

                    if(getFromCode.length() != 3) {
                        setFromCode.setError("Please enter correct code.");
                    }
                    if (getToCode.length() != 3) {
                        setToCode.setError("Please enter correct code.");
                    }
                    if (checkDate.isEmpty()) {
                        Toast.makeText(TrainBtnStationsActivity.this, "Date field is not set.", Toast.LENGTH_SHORT).show();
                    }

                    if (getFromCode.length() == 3 && getToCode.length() == 3 && !checkDate.isEmpty()) {
                        TrainsBtnStnFragment fragment = new TrainsBtnStnFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.trains_fragment_container, fragment);
                        transaction.commit();
                    }
                } else {
                    Toast.makeText(TrainBtnStationsActivity.this, "Network unavailable. Please check and try again.", Toast.LENGTH_SHORT).show();
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
