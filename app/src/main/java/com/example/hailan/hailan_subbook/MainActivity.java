package com.example.hailan.hailan_subbook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

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
    private ListView listOfSubscription;
    private EditText nameOfSubscription;
    private EditText dateOfSubscription;
    private EditText costOfSubscription;
    private EditText commentOfSubscription;

    DatePickerDialog datePickerDialog;


    private ArrayList<Subscription> subscriptionlist;
    private ArrayAdapter<Subscription> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.add_subscription);
        Button saveButton = (Button) findViewById(R.id.saveButtonID);
        nameOfSubscription = (EditText) findViewById(R.id.addName);
        dateOfSubscription = (EditText) findViewById(R.id.addDate);
        costOfSubscription = (EditText) findViewById(R.id.addCost);
        commentOfSubscription = (EditText) findViewById(R.id.addComment);
        listOfSubscription = (ListView) findViewById(R.id.listView);


        /**
         * The following OnClickListener is a spinOff from http://abhiandroid.com/ui/datepicker
         */
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
                        if (month<10){
                            monthString = "0" + monthString;
                        }
                        if (day<10){
                            dayString = "0" + dayString;
                        }
                        dateOfSubscription.setText(year + "-" + monthString + "-" + dayString);
                    }
                }, yearPicked, monthPicked, dayPicked);

                datePickerDialog.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String name = nameOfSubscription.getText().toString();
                String date = dateOfSubscription.getText().toString();
                String cost = costOfSubscription.getText().toString();
                String comment = commentOfSubscription.getText().toString();

                //Subscription newSubscription = new Subscription();

                try {
                    Double costInDouble = Double.valueOf(cost);
                } catch (NumberFormatException e) {
                    costOfSubscription.setHint("INVALID INPUT");
                    return;
                }

                Subscription newSubscription = new Subscription(name, date, cost, comment);
                subscriptionlist.add(newSubscription);

                adapter.notifyDataSetChanged();
                saveInFile();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Subscription>(this,
                R.layout.list_item, subscriptionlist);
        listOfSubscription.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choices_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();

            subscriptionlist = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionlist = new ArrayList<Subscription>();
        } catch (IOException e) {
            e.printStackTrace();
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
