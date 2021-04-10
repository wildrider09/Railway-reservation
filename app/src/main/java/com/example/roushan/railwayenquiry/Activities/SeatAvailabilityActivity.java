package com.example.roushan.railwayenquiry.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roushan.railwayenquiry.Fragments.SeatAvailabilityFragment;
import com.example.roushan.railwayenquiry.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SeatAvailabilityActivity extends AppCompatActivity {
    String[] trainClass = {"FIRST AC", "SECOND AC", "THIRD AC", "3 AC Economy", "AC CHAIR CAR", "FIRST CLASS", "SLEEPER CLASS",
                           "SECOND SEATING"};

    String[] trainQuota = {"Tatkal Quota", "Premium Tatkal Quota", "Ladies Quota", "Defense Quota", "Foreign Tourist",
                           "Duty Pass Quota", "Handicapped Quota", "Parliament House Quota", "Lower Birth Quota", "Yuva Quota",
                           "GENERAL QUOTA"};

    private EditText trainNo;
    private EditText setDate;
    private EditText fromStationCode;
    private EditText toStationCode;
    private Button searchButton;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog setDateDialog;
    private AutoCompleteTextView trainClassTextView;
    private AutoCompleteTextView trainQuotaTextView;

    public static String getTrainNo;
    public static String getDate;
    public static String getFromStationCode;
    public static String getToStationCode;
    public static String getQuota = "GN";
    public static String getClass = "SL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        setViewsToActivity();
        setAdapters();
        onClickSetDate();
        onClickSearchButton();
    }

    private void setViewsToActivity() {
        trainNo = (EditText) findViewById(R.id.enter_train_number);
        setDate = (EditText) findViewById(R.id.select_date);
        fromStationCode = (EditText) findViewById(R.id.select_source_stationCode);
        toStationCode = (EditText) findViewById(R.id.select_destination_stationCode);
        searchButton = (Button) findViewById(R.id.search_seat_availability);
    }

    private void setAdapters() {
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item, trainClass);
        trainClassTextView = (AutoCompleteTextView) findViewById(R.id.select_train_class);
        trainClassTextView.setThreshold(0);
        trainClassTextView.setAdapter(classAdapter);

        ArrayAdapter<String> quotaAdapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item, trainQuota);
        trainQuotaTextView = (AutoCompleteTextView) findViewById(R.id.select_train_quota);
        trainQuotaTextView.setThreshold(0);
        trainQuotaTextView.setAdapter(quotaAdapter);
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
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void onClickSearchButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {

                    getTrainNo = trainNo.getText().toString().trim();
                    getDate = setDate.getText().toString().trim();
                    getFromStationCode = fromStationCode.getText().toString().trim();
                    getToStationCode = toStationCode.getText().toString().trim();

                    if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("FIRST AC")) {
                        getClass = "1A";
                    } else if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("SECOND AC")) {
                        getClass = "2A";
                    } else if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("THIRD AC")) {
                        getClass = "3A";
                    } else if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("3 AC Economy")) {
                        getClass = "3E";
                    } else if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("AC CHAIR CAR")) {
                        getClass = "CC";
                    } else if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("FIRST CLASS")) {
                        getClass = "FC";
                    } else if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("SLEEPER CLASS")) {
                        getClass = "SL";
                    } else if(trainClassTextView.getText().toString().trim().equalsIgnoreCase("SECOND SEATING")) {
                        getClass = "2S";
                    }

                    if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Tatkal Quota")) {
                        getQuota = "TQ";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Premium Tatkal Quota")) {
                        getQuota = "PT";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Ladies Quota")) {
                        getQuota = "LD";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Defense Quota")) {
                        getQuota = "DF";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Foreign Tourist")) {
                        getQuota = "FT";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Duty Pass Quota")) {
                        getQuota = "DP";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Handicapped Quota")) {
                        getQuota = "HP";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Parliament House Quota")) {
                        getQuota = "PH";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Lower Birth Quota")) {
                        getQuota = "LB";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("Yuva Quota")) {
                        getQuota = "YU";
                    } else if(trainQuotaTextView.getText().toString().trim().equalsIgnoreCase("GENERAL QUOTA")) {
                        getQuota = "GN";
                    }

                    if(trainNo.getText().length() != 5) {
                        trainNo.setError("Please enter correct train number");
                    } else {
                        trainNo.setError(null);
                    }

                    if(getDate.isEmpty()) {
                        Toast.makeText(SeatAvailabilityActivity.this, "Date field is not set.", Toast.LENGTH_SHORT).show();
                    } else {
                        setDate.setError(null);
                    }

                    if(getFromStationCode.length() != 3) {
                        fromStationCode.setError("Please enter correct code.");
                    } else {
                        fromStationCode.setError(null);
                    }

                    if(getToStationCode.length() != 3) {
                        toStationCode.setError("Please enter correct code.");
                    } else {
                        toStationCode.setError(null);
                    }

                    if(getTrainNo.length() == 5 && !getDate.isEmpty() && getFromStationCode.length() == 3 &&
                            getToStationCode.length() == 3 || !getClass.isEmpty() || !getQuota.isEmpty()) {
                        SeatAvailabilityFragment fragment = new SeatAvailabilityFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        fragment.show(manager, "Seat_Availability_Dialog");
                    }

                } else {
                    Toast.makeText(SeatAvailabilityActivity.this, "Network unavailable. Please check and try again.", Toast.LENGTH_SHORT).show();
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
