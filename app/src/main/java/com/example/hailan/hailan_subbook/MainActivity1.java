package com.example.hailan.hailan_subbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
    private TextView directions;

    private static final String FILENAME = "newfile.sav";

    private ArrayList<Subscription> subscriptionlist;
    private SubscriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        listView = (ListView) findViewById(R.id.listviewID);
        totalCost = (TextView) findViewById(R.id.totalCostID);
        directions = (TextView) findViewById(R.id.directionsID);
        Button addButton = (Button) findViewById(R.id.addButtonID);

        /* +Subscription button takes user to MainActivity.class */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity1.this, MainActivity.class));
            }
        });

        /*Following method is a revision of https://stackoverflow.com/a/5344958, username: esharp*/
        /* Long click for option to delete subscription */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
                alert.setTitle("DELETE");
                alert.setMessage("Are you sure you want to permanently delete this subscription?");
                final int position = i;
                alert.setNegativeButton("CANCEL",null);
                alert.setPositiveButton("OK", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        subscriptionlist.remove(position);
                        adapter.notifyDataSetChanged();
                        saveInFile();
                        calculateCost();
                        setDirections();
                    }
                });
                alert.show();
                return true;
            }
        });

        /* Short click to edit info */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                String pos = Integer.toString(i);
                intent.putExtra("pos", pos);
                startActivity(intent);
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
        setDirections();
    }

    /* Gives user directions on usage */
    private void setDirections(){
        if(subscriptionlist.size() == 0){
            directions.setText("Currently you have no subscriptions");
        } else {
            directions.setText("Click to edit a subscription \nClick and hold to delete a subscription");
        }
    }

    /* Adds up total */
    private void calculateCost(){
        double total = 0;
        for (int i = 0; i < subscriptionlist.size(); i++){
            total += Double.valueOf(subscriptionlist.get(i).getMonthlyCharge());
        }
        String totalString = "Your Total Monthly Cost: $" + String.format("%.2f",total);
        totalCost.setText(totalString);
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
