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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roushan.railwayenquiry.Fragments.ShowTrainStatusFragment;
import com.example.roushan.railwayenquiry.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LiveTrainStatusActivity extends AppCompatActivity {

    private EditText trainNumberEditText;
    private EditText setDateEditText;
    private Button liveTrainSearchButton;
    private DatePickerDialog setDateDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat jsonDateFormatter;
    public static String jsonDate = "";
    public static String getTrainNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        jsonDateFormatter = new SimpleDateFormat("yyyyMMdd", Locale.US);

        setViewsToActivity();
        onClickSetDateEditText();
        onClickSearchButton();
    }

    private void setViewsToActivity() {
        trainNumberEditText   = (EditText) findViewById(R.id.enter_trainNumber);
        setDateEditText       = (EditText) findViewById(R.id.set_liveTrainDate);
        liveTrainSearchButton = (Button) findViewById(R.id.search_train_status);
    }

    private void onClickSetDateEditText() {
        setDateEditText.setOnClickListener(new View.OnClickListener() {
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
                setDateEditText.setText(dateFormatter.format(newDate.getTime()));

                jsonDate = jsonDateFormatter.format(newDate.getTime());
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void onClickSearchButton() {

        liveTrainSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()) {
                    getTrainNumber = trainNumberEditText.getText().toString();
                    String getTrainDate = setDateEditText.getText().toString();

                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (getTrainNumber.length() != 5) {
                        trainNumberEditText.setError("Please enter correct train number");
                    } else {
                        trainNumberEditText.setError(null);
                    }

                    if (getTrainDate.isEmpty()) {
                        Toast.makeText(LiveTrainStatusActivity.this, "Date field is not set.", Toast.LENGTH_SHORT).show();
                    } else {
                        setDateEditText.setError(null);
                    }

                    if (getTrainNumber.length() == 5 && !getTrainDate.isEmpty()) {
                        ShowTrainStatusFragment fragment = new ShowTrainStatusFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.train_status_fragment_container, fragment);
                        transaction.commit();
                    }
                } else {
                    Toast.makeText(LiveTrainStatusActivity.this, "Network unavailable. Please check and try again.", Toast.LENGTH_SHORT).show();
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
