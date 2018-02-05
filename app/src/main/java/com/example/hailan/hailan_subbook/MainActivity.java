package com.example.hailan.hailan_subbook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "newfile.sav";
    private EditText nameOfSubscription;
    private EditText dateOfSubscription;
    private EditText costOfSubscription;
    private EditText commentOfSubscription;

    DatePickerDialog datePickerDialog;

    private ArrayList<Subscription> subscriptionlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Initialization of EditText types and save button */
        setContentView(R.layout.add_subscription);
        Button saveButton = (Button) findViewById(R.id.saveButtonID);
        Button cancelButton = (Button) findViewById(R.id.cancelButtonID);
        nameOfSubscription = (EditText) findViewById(R.id.addName);
        dateOfSubscription = (EditText) findViewById(R.id.addDate);
        costOfSubscription = (EditText) findViewById(R.id.addCost);
        commentOfSubscription = (EditText) findViewById(R.id.addComment);


        dateOfSubscription.setHint("Click for date entry");

        /* The following OnClickListener is a spinOff from http://abhiandroid.com/ui/datepicker, author: Abhishek Saini*/
        /* Date picker */
        dateOfSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int yearPicked = c.get(Calendar.YEAR);
                int monthPicked = c.get(Calendar.MONTH);
                int dayPicked = c.get(Calendar.DAY_OF_MONTH);;

                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String monthString = Integer.toString(month+1);
                        String dayString = Integer.toString(day);
                        if (month+1<10){
                            monthString = "0" + monthString;    // Convert date to right format
                        }
                        if (day<10){
                            dayString = "0" + dayString;
                        }
                        String setString = year + "-" + monthString + "-" + dayString;
                        dateOfSubscription.setText(setString);
                    }
                }, yearPicked, monthPicked, dayPicked);

                datePickerDialog.show();
            }
        });

        /* Takes user back to MainActivity1 */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* save info for subscription */
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String name = nameOfSubscription.getText().toString();
                String date = dateOfSubscription.getText().toString();
                String cost = costOfSubscription.getText().toString();
                String comment = commentOfSubscription.getText().toString();

                /* Check empty string */
                boolean allFilled = true;
                if (name.equals("")) {
                    nameOfSubscription.setHint("**");
                    allFilled = false;
                }
                if (date.equals("")) {
                    dateOfSubscription.setHint("**");
                    allFilled = false;
                }
                if (cost.equals("")) {
                    costOfSubscription.setHint("**");
                    allFilled = false;
                }
                if (!allFilled) {
                    return;
                }

                /* Convert cost to right format */
                Double costInDouble = Double.valueOf(cost);
                cost = String.format("%.2f",costInDouble);

                /* Create new subscription object */
                Subscription newSubscription = new Subscription(name, date, cost, comment);
                subscriptionlist.add(newSubscription);

                saveInFile();

                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        nameOfSubscription.setText("");
        dateOfSubscription.setText("");
        dateOfSubscription.setHint("Click for date entry");
        costOfSubscription.setText("");
        commentOfSubscription.setText("");
    }


    /*loadFromFile and saveInFile methods are from the lab tutorial - LonelyTwitterActivity */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();

            subscriptionlist = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionlist = new ArrayList<Subscription>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(subscriptionlist, out);

            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
