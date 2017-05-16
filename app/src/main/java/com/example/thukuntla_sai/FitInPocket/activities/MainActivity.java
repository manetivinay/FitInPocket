package com.example.thukuntla_sai.FitInPocket.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.thukuntla_sai.FitInPocket.R;
import com.example.thukuntla_sai.FitInPocket.Utils;
import com.example.thukuntla_sai.FitInPocket.db.DatabaseHandler;
import com.example.thukuntla_sai.FitInPocket.model.ProfileModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private RelativeLayout rrAddProfile, rrHistory, rrProgress;
    private RelativeLayout content_main;
    private DatabaseHandler mDatabaseHandler;
    private Activity activity = null;
    private int SETTINGS_ACTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.themeSettings(this);
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                startActivityForResult(new Intent(this,
                        ThemePreferenceActivity.class), SETTINGS_ACTION);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_ACTION) {
            if (resultCode == ThemePreferenceActivity.RESULT_CODE_THEME_UPDATED) {
                finish();
                startActivity(getIntent());
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);
//
//        MenuItem item = menu.findItem(R.id.spinner);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.category, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
//                if (selectedItem.equalsIgnoreCase("white")) {
////                    Utils.changeToTheme(MainActivity.this, Utils.THEME_DEFAULT);
////                    content_main.setBackgroundColor(Color.parseColor("#f0f8ff"));
//                } else if (selectedItem.equalsIgnoreCase("green")) {
////                    Utils.changeToTheme(MainActivity.this, Utils.THEME_WHITE);
////                    content_main.setBackgroundColor(Color.parseColor("#7fffd4"));
//                } else if (selectedItem.equalsIgnoreCase("red")) {
////                    Utils.changeToTheme(MainActivity.this, Utils.THEME_BLUE);
////                    content_main.setBackgroundColor(Color.parseColor("#ff6a6a"));
//                }
//
//
////                else if (selectedItem.equalsIgnoreCase("blue")) {
////                    content_main.setBackgroundColor(Color.parseColor("#729fcf"));
////                } else if (selectedItem.equalsIgnoreCase("grey")) {
////                    content_main.setBackgroundColor(Color.parseColor("#b4becc"));
////                } else if (selectedItem.equalsIgnoreCase("yellow")) {
////                    content_main.setBackgroundColor(Color.parseColor("#fafad2"));
////                }
//
//            } // to close the onItemSelected
//
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        return true;
//    }

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
                Intent progressIntent = new Intent(this, ProgressActivity.class);
                startActivity(progressIntent);
                break;
        }
    }
}
