package com.example.vinaymaneti.fitinpocket.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.db.DatabaseHandler;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private RelativeLayout rrAddProfile, rrHistory, rrProgress, rrUpdateWeight;

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

        mToolbar.setTitle("Home");
        setSupportActionBar(mToolbar);


        rrAddProfile.setOnClickListener(this);
        rrHistory.setOnClickListener(this);
        rrProgress.setOnClickListener(this);


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
