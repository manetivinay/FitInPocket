package com.example.vinaymaneti.fitinpocket.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.Utils;
import com.example.vinaymaneti.fitinpocket.db.DatabaseHandler;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;

public class AddProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String APP_NAME = "FitInPocket";
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
    private Button editButton;
    private DatabaseHandler mDatabaseHandler;
    private RadioButton radioMale, radioFemale;
    private TextView addMoreWeights;
    private CircleImageView captureImage;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;

    Bitmap myBitmap;
    Uri picUri;

    public static final String MyPREFERENCES = "FitInPocketPref";
    public static final String PREF_NAME = "name";
    public static final String PREF_GENDER = "gender";
    public static final String PREF_AGE_YEAR = "yeards";
    public static final String PREF_AGE_MONTH = "months";
    public static final String PREF_CURRENT_WEIGHT = "current_weight";
    public static final String PREF_TARGET_WEIGHT = "target_weight";
    public static final String PREF_DATE = "date";

    SharedPreferences sharedpreferences;
    ProfileModel profileModel;

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
        editButton = (Button) findViewById(R.id.editButton);
        captureImage = (CircleImageView) findViewById(R.id.img_profile);

        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        profileModel = mDatabaseHandler.retrieveUserDetails();
        if (profileModel != null && profileModel.getImge_bitmap() != null) {
            captureImage.setImageBitmap(profileModel.getImge_bitmap());
        }
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String name = sharedpreferences.getString(PREF_NAME, null);
        String gender = sharedpreferences.getString(PREF_GENDER, null);
        int age_years = sharedpreferences.getInt(PREF_AGE_YEAR, 0);
        int age_months = sharedpreferences.getInt(PREF_AGE_MONTH, 0);
        int current_weight = sharedpreferences.getInt(PREF_CURRENT_WEIGHT, 0);
        int target_weight = sharedpreferences.getInt(PREF_TARGET_WEIGHT, 0);
        String date = sharedpreferences.getString(PREF_DATE, null);
        if (name != null && age_years != 0 && current_weight != 0 && target_weight != 0) {
            captureImage.setEnabled(false);
            nameEditText.setEnabled(false);
            yearsEditText.setEnabled(false);
            monthsEditText.setEnabled(false);
            currentWeightEditText.setEnabled(false);
            targetWeightEditText.setEnabled(false);
            for (int i = 0; i < radioSexGroup.getChildCount(); i++) {
                radioSexGroup.getChildAt(i).setEnabled(false);
            }
            saveButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.VISIBLE);
        }

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
        captureImage.setOnClickListener(this);
        editButton.setOnClickListener(this);

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
                byte[] byteArray;
                Bitmap bitmap = profileModel.getImge_bitmap();
                if (myBitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                } else {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                }
                updateIntent.putExtra("imageUri", byteArray);
                startActivity(updateIntent);
                break;
            case R.id.cancelButton:
                Intent cancelIntent = new Intent(this, MainActivity.class);
                startActivity(cancelIntent);
                finish();
                break;
            case R.id.img_profile:
                Toast.makeText(getApplicationContext(), "sddsfs", Toast.LENGTH_LONG).show();
                startActivityForResult(getPickImageChooserIntent(), 200);
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
                Uri outputFileUri = getCaptureImageOutputUri();
                Log.d("mBitmap", String.valueOf(myBitmap));
                if (myBitmap != null) {
                    profileModel.setImge_bitmap(myBitmap);
                } else {
                    myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_icon);
                    profileModel.setImge_bitmap(myBitmap);
                }

                mDatabaseHandler.addTodo(new ProfileModel(profileModel.getDate_time_created(), profileModel.getName(), profileModel.getGender(), profileModel.getAge_years(), profileModel.getAge_months(), profileModel.getCurrent_weight(), profileModel.getTarget_weight(), profileModel.getImge_bitmap()));
                Toast.makeText(this, "successfully stored", Toast.LENGTH_LONG).show();
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                startActivity(mainActivityIntent);
                break;
            case R.id.backButton:
                Intent backIntent = new Intent(this, MainActivity.class);
                startActivity(backIntent);
                finish();
                break;

            case R.id.editButton:
                captureImage.setEnabled(true);
                nameEditText.setEnabled(true);
                yearsEditText.setEnabled(true);
                monthsEditText.setEnabled(true);
                currentWeightEditText.setEnabled(true);
                targetWeightEditText.setEnabled(true);
                for (int i = 0; i < radioSexGroup.getChildCount(); i++) {
                    radioSexGroup.getChildAt(i).setEnabled(true);
                }
                saveButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);
                break;
        }
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }


    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     */
    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));//
            Log.d("outputFileUri :- ", outputFileUri.toString());
        }
        return outputFileUri;
    }

    private File CreateImageFile() {
        File defaultFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_NAME);
        if (!defaultFile.exists())
            defaultFile.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File file = new File(defaultFile, imageFileName);

        //renaming file if exist
        int i = 2;
        while (file.exists()) {
            file = new File(defaultFile, timeStamp + "(" + i + ")" + ".jpg");
            i++;
        }

        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);

                    CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                    croppedImageView.setImageBitmap(myBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;
                CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }
            }
        }

    }

    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }


}
