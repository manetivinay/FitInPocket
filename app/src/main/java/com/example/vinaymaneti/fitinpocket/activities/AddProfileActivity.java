package com.example.vinaymaneti.fitinpocket.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.db.DatabaseHandler;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private TextView dateTextView;
    private EditText nameEditText;
    private EditText yearsEditText;
    private EditText monthsEditText;
    private EditText currentWeightEditText;
    private EditText targetWeightEditText;
    private Button backButton;
    private Button saveButton;
    private Button cancelButton;
    private DatabaseHandler mDatabaseHandler;
    private RadioButton radioMale, radioFemale;
    private TextView addMoreWeights;


    public static final String MyPREFERENCES = "FitInPocketPref";
    public static final String PREF_NAME = "name";
    public static final String PREF_GENDER = "gender";
    public static final String PREF_AGE_YEAR = "yeards";
    public static final String PREF_AGE_MONTH = "months";
    public static final String PREF_CURRENT_WEIGHT = "current_weight";
    public static final String PREF_TARGET_WEIGHT = "target_weight";
    public static final String PREF_DATE = "date";


    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add profile");
        mDatabaseHandler = new DatabaseHandler(this);
        setSupportActionBar(toolbar);
        addMoreWeights = (TextView) findViewById(R.id.addMoreWeights);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        yearsEditText = (EditText) findViewById(R.id.yearsEditText);
        monthsEditText = (EditText) findViewById(R.id.monthsEditText);
        currentWeightEditText = (EditText) findViewById(R.id.currentWeightEditText);
        targetWeightEditText = (EditText) findViewById(R.id.targetWeightEditText);
        backButton = (Button) findViewById(R.id.backButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(PREF_NAME, null);
        String gender = sharedpreferences.getString(PREF_GENDER, null);
        int age_years = sharedpreferences.getInt(PREF_AGE_YEAR, 0);
        int age_months = sharedpreferences.getInt(PREF_AGE_MONTH, 0);
        int current_weight = sharedpreferences.getInt(PREF_CURRENT_WEIGHT, 0);
        int target_weight = sharedpreferences.getInt(PREF_TARGET_WEIGHT, 0);
        String date = sharedpreferences.getString(PREF_DATE, null);

        if (name != null) {
            if (gender.equalsIgnoreCase("male")) {
                radioMale.setChecked(true);
            } else {
                radioFemale.setChecked(true);
            }
            dateTextView.setText(date);
            nameEditText.setText(name);
            yearsEditText.setText(String.valueOf(age_years));
            monthsEditText.setText(String.valueOf(age_months));
            currentWeightEditText.setText(String.valueOf(current_weight));
            targetWeightEditText.setText(String.valueOf(target_weight));
        }


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();


        // Now we display formattedDate value in TextView
        dateTextView.setText(formattedDate);

        backButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        addMoreWeights.setOnClickListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private boolean validateFields() {
        boolean value = true;

        String mName = nameEditText.getText().toString();
        String mGender = radioSexButton.getText().toString();
        String mYearsEditText = yearsEditText.getText().toString();
        String mMonthsEditText = monthsEditText.getText().toString();
        String mCurrentWeightEditText = currentWeightEditText.getText().toString();
        String mTargetWeightEditText = targetWeightEditText.getText().toString();

        if (mName.isEmpty() || mName.trim().length() <= 0) {
            Snackbar.make(nameEditText, "Name is in valid", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            value = false;
        } else if (mGender.isEmpty() || mGender.trim().length() <= 0) {
            Snackbar.make(radioSexButton, "Gender is in valid", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            value = false;
        } else if (mYearsEditText.isEmpty() || mYearsEditText.trim().length() <= 0) {
            Snackbar.make(yearsEditText, "Year is in valid", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            value = false;
        } else if (mMonthsEditText.isEmpty() || mMonthsEditText.trim().length() <= 0) {
            Snackbar.make(monthsEditText, "months is in valid", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            value = false;
        } else if (mCurrentWeightEditText.isEmpty() || mCurrentWeightEditText.trim().length() <= 0) {
            Snackbar.make(currentWeightEditText, "Current Weight is in valid", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            value = false;
        } else if (mTargetWeightEditText.isEmpty() || mTargetWeightEditText.trim().length() <= 0) {
            Snackbar.make(targetWeightEditText, "Target¬ Weight is in valid", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            value = false;
        }

        return value;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreWeights:
                Intent updateIntent = new Intent(this, UpdateWeightActivity.class);
                startActivity(updateIntent);
                break;
            case R.id.cancelButton:
                Intent cancelIntent = new Intent(this, MainActivity.class);
                startActivity(cancelIntent);
                finish();
                break;
            case R.id.saveButton:

                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                // find the radio button by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(AddProfileActivity.this,
                        radioSexButton.getText(), Toast.LENGTH_SHORT).show();

                if (!validateFields()) {
                    return;
                }

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(PREF_DATE, dateTextView.getText().toString());
                editor.putString(PREF_NAME, nameEditText.getText().toString());
                editor.putString(PREF_GENDER, radioSexButton.getText().toString());
                editor.putInt(PREF_AGE_YEAR, Integer.parseInt(yearsEditText.getText().toString()));
                editor.putInt(PREF_AGE_MONTH, Integer.parseInt(monthsEditText.getText().toString()));
                editor.putInt(PREF_CURRENT_WEIGHT, Integer.parseInt(currentWeightEditText.getText().toString()));
                editor.putInt(PREF_TARGET_WEIGHT, Integer.parseInt(targetWeightEditText.getText().toString()));
                editor.apply();

                ProfileModel profileModel = new ProfileModel();
                profileModel.setDate_time_created(dateTextView.getText().toString());
                profileModel.setName(nameEditText.getText().toString());
                profileModel.setGender(radioSexButton.getText().toString());
                profileModel.setAge_years(Integer.parseInt(yearsEditText.getText().toString()));
                profileModel.setAge_months(Integer.parseInt(monthsEditText.getText().toString()));
                profileModel.setCurrent_weight(Integer.parseInt(currentWeightEditText.getText().toString()));
                profileModel.setTarget_weight(Integer.parseInt(targetWeightEditText.getText().toString()));
                mDatabaseHandler.addTodo(new ProfileModel(profileModel.getDate_time_created(), profileModel.getName(), profileModel.getGender(), profileModel.getAge_years(), profileModel.getAge_months(), profileModel.getCurrent_weight(), profileModel.getTarget_weight()));
                Toast.makeText(this, "successfully stored", Toast.LENGTH_LONG).show();
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                startActivity(mainActivityIntent);
                break;
            case R.id.backButton:
                Intent backIntent = new Intent(this, MainActivity.class);
                startActivity(backIntent);
                finish();
                break;
        }
    }
}
