package com.example.hailan.hailan_subbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listSubscriptions;
    private EditText nameOfSubscription;
    private EditText dateOfSubscription;
    private EditText costOfSubscription;
    private EditText commentOfSubscription;

    private ArrayList<Subscription> allSubscriptions;
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

        //listSubscriptions = (ListView) findViewById(R.id.listviewID);
        //ArrayAdapter<>

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);

                //String text = bodyText.getText().toString();
                //Tweet newtweet = new NormalTweet(text);
                //tweetlist.add(newtweet);
                //adapter.notifyDataSetChanged();
                //saveInFile();

            }
        });
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
}
