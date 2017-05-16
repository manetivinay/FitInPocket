package com.example.thukuntla_sai.FitInPocket.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.thukuntla_sai.FitInPocket.R;
import com.example.thukuntla_sai.FitInPocket.Utils;
import com.example.thukuntla_sai.FitInPocket.adapter.HistoryAdapter;
import com.example.thukuntla_sai.FitInPocket.db.DatabaseHandler;
import com.example.thukuntla_sai.FitInPocket.model.ProfileModel;

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
