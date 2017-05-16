package com.example.vinaymaneti.fitinpocket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.Utils;
import com.example.vinaymaneti.fitinpocket.db.DatabaseHandler;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText;
    private Button loginButton;
    private Button signUpButton;
    private DatabaseHandler mDatabaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.themeSettings(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHandler = new DatabaseHandler(this);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                boolean isUserExist = mDatabaseHandler.checkUserExist(nameEditText.getText().toString());
                if (!isUserExist) {
                    Toast.makeText(this, "No User with this name", Toast.LENGTH_LONG).show();
                } else {
                    Intent updateWeightIntent = new Intent(this, UpdateWeightActivity.class);
                    startActivity(updateWeightIntent);
                }

                break;
            case R.id.signUpButton:
                Intent addProfileIntent = new Intent(this, AddProfileActivity.class);
                startActivity(addProfileIntent);
                break;
        }
    }
}
