package com.example.vinaymaneti.fitinpocket.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.db.DatabaseHandler;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private RelativeLayout rrAddProfile, rrHistory, rrProgress, rrUpdateWeight;
    private RelativeLayout content_main;
    private DatabaseHandler mDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHandler = new DatabaseHandler(this);
        List<ProfileModel> mProfileList = mDatabaseHandler.getAllList();
        if (mProfileList != null) {
            Toast.makeText(MainActivity.this, mProfileList.size() + "", Toast.LENGTH_LONG).show();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        rrAddProfile = (RelativeLayout) findViewById(R.id.rrAddProfile);
        rrHistory = (RelativeLayout) findViewById(R.id.rrHistory);
        rrProgress = (RelativeLayout) findViewById(R.id.rrProgress);
        rrUpdateWeight = (RelativeLayout) findViewById(R.id.rrUpdateWeight);
        content_main = (RelativeLayout) findViewById(R.id.content_main);

        mToolbar.setTitle("Home");
        setSupportActionBar(mToolbar);


        rrAddProfile.setOnClickListener(this);
        rrHistory.setOnClickListener(this);
        rrProgress.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                if (selectedItem.equalsIgnoreCase("white")) {
                    content_main.setBackgroundColor(Color.parseColor("#f0f8ff"));
                } else if (selectedItem.equalsIgnoreCase("green")) {
                    content_main.setBackgroundColor(Color.parseColor("#7fffd4"));
                } else if (selectedItem.equalsIgnoreCase("red")) {
                    content_main.setBackgroundColor(Color.parseColor("#ff6a6a"));
                } else if (selectedItem.equalsIgnoreCase("blue")) {
                    content_main.setBackgroundColor(Color.parseColor("#729fcf"));
                } else if (selectedItem.equalsIgnoreCase("grey")) {
                    content_main.setBackgroundColor(Color.parseColor("#b4becc"));
                } else if (selectedItem.equalsIgnoreCase("yellow")) {
                    content_main.setBackgroundColor(Color.parseColor("#fafad2"));
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rrAddProfile:
                Intent addProfileIntent = new Intent(this, AddProfileActivity.class);
                startActivity(addProfileIntent);
                break;
            case R.id.rrHistory:
                Intent historyIntent = new Intent(this, HistoryActivity.class);
                startActivity(historyIntent);
                break;
            case R.id.rrProgress:
                Intent progressIntent = new Intent(this, Progressactivity.class);
                startActivity(progressIntent);
                break;
            case R.id.rrUpdateWeight:
                Intent updateWeightIntent = new Intent(this, UpdateWeightActivity.class);
                startActivity(updateWeightIntent);
                break;
        }
    }
}
