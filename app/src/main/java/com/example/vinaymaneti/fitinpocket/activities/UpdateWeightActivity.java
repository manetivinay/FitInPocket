package com.example.vinaymaneti.fitinpocket.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.db.DatabaseHandler;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateWeightActivity extends AppCompatActivity {

    private EditText updateWeight;
    private Button updateButton;
    private TextView dateTextView;
    private DatabaseHandler datebaseHandler;
    byte[] byteArray;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_weight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateWeight = (EditText) findViewById(R.id.updateWeight);
        updateButton = (Button) findViewById(R.id.updateButton);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

        if (getIntent().getExtras() != null) {
            byte[] byteArray = getIntent().getByteArrayExtra("imageUri");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());

        SharedPreferences sharedpreferences = getSharedPreferences(AddProfileActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final int target_weight = sharedpreferences.getInt(AddProfileActivity.PREF_TARGET_WEIGHT, 0);
        datebaseHandler = new DatabaseHandler(this);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                List<ProfileModel> allList = datebaseHandler.getAllList();
////                for (int i = 0; i < allList.size(); i++) {
                if (Integer.parseInt(updateWeight.getText().toString()) <= target_weight) {
                    ProfileModel profileModel = new ProfileModel();
                    profileModel.setDate_time_created(formattedDate);
                    profileModel.setCurrent_weight(Integer.parseInt(updateWeight.getText().toString()));
                    profileModel.setImge_bitmap(bmp);
                    datebaseHandler.addTodo(new ProfileModel(profileModel.getDate_time_created(), null, null, 0, 0, profileModel.getCurrent_weight(), 0, profileModel.getImge_bitmap()));
                    Intent mainActivityIntent = new Intent(UpdateWeightActivity.this, ProgressActivity.class);
                    mainActivityIntent.putExtra("Target", updateWeight.getText().toString());
                    startActivity(mainActivityIntent);
                    finish();
                } else {
//                }

                    ProfileModel profileModel = new ProfileModel();
                    profileModel.setDate_time_created(formattedDate);
                    profileModel.setCurrent_weight(Integer.parseInt(updateWeight.getText().toString()));
                    profileModel.setImge_bitmap(bmp);
                    datebaseHandler.addTodo(new ProfileModel(profileModel.getDate_time_created(), null, null, 0, 0, profileModel.getCurrent_weight(), 0, profileModel.getImge_bitmap()));
                    Intent mainActivityIntent = new Intent(UpdateWeightActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
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
