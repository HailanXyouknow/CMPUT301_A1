package com.example.hailan.hailan_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class MainActivity1 extends AppCompatActivity {
    private ListView listView;
    private TextView totalCost;
    private Button addButton;
    private static final String FILENAME = "newfile.sav";

    private ArrayList<Subscription> subscriptionlist;
    private SubscriptionAdapter adapter;
    private static final String TAG = "onCreate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        listView = (ListView) findViewById(R.id.listviewID);
        totalCost = (TextView) findViewById(R.id.totalCostID);
        addButton = (Button) findViewById(R.id.addButtonID);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity1.this, MainActivity.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();

        adapter = new SubscriptionAdapter(this, R.layout.list_subformat, subscriptionlist);
        listView.setAdapter(adapter);

        calculateCost();

    }

    private void calculateCost(){
        double total = 0;
        for (int i = 0; i < subscriptionlist.size(); i++){
            total += Double.valueOf(subscriptionlist.get(i).getMonthlyCharge());
        }
        String totalString = Double.toString(total);
        totalCost.setText("Your Total Monthly Cost: " + totalString);
    }

    /**
     *  loadFromFile and saveInFile methods are from the lab tutorial - LonelyTwitterActivity
     */
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

    /* Might be deleted later */
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