package com.example.vinaymaneti.fitinpocket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vinaymaneti.fitinpocket.R;

public class ProgressActivity extends AppCompatActivity {

    private TextView needInputLossWeight;
    private Button updateButton;
    private TextView weightLossed;
    private TextView lbs;
    private TextView CongratsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        needInputLossWeight = (TextView) findViewById(R.id.needInputLossWeight);
        weightLossed = (TextView) findViewById(R.id.weightLossed);
        lbs = (TextView) findViewById(R.id.lbs);
        CongratsTextView = (TextView) findViewById(R.id.CongratsTextView);
        if (getIntent().getExtras() != null) {
            String targetReached = getIntent().getExtras().getString("Target");
            needInputLossWeight.setText(targetReached);
        } else {
            needInputLossWeight.setVisibility(View.INVISIBLE);
            weightLossed.setVisibility(View.INVISIBLE);
            lbs.setVisibility(View.INVISIBLE);
            CongratsTextView.setText("You still need to work out, Keep moving!");
        }

        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(ProgressActivity.this, AddProfileActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
