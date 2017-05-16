package com.example.vinaymaneti.fitinpocket.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.Utils;
import com.example.vinaymaneti.fitinpocket.adapter.HistoryAdapter;
import com.example.vinaymaneti.fitinpocket.db.DatabaseHandler;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter mHistoryAdapter;
    RecyclerView recyclerView;
    private DatabaseHandler mDatabaseHandler;
    private TextView noHistoryRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.themeSettings(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        noHistoryRv = (TextView) findViewById(R.id.noHistoryRv);
        setSupportActionBar(toolbar);
        mDatabaseHandler = new DatabaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        List<ProfileModel> profileModels = mDatabaseHandler.getAllList();
        if (profileModels.size() <= 0) {
            noHistoryRv.setVisibility(View.VISIBLE);
        }
        mHistoryAdapter = new HistoryAdapter(this, profileModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mHistoryAdapter);
        mHistoryAdapter.notifyDataSetChanged();
    }

}
