package com.example.vinaymaneti.fitinpocket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vinaymaneti.fitinpocket.Utils;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //All static variables
    //Data base version
    private static final int DATABASE_VERSION = 1;  // here I upgrade the database version from 1 to 2

    // Database name
    private static final String DATABASE_NAME = "fitInPocketOrganiser";

    //Table name
    public static final String TABLE_NAME = "fitInPocketTable";

    private Context context;

    //TO-DO List Table column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE_YEARS = "age_years";
    private static final String KEY_AGE_MONTHS = "age_months";
    private static final String KEY_CURRENT_WEIGHT = "current_weight";
    private static final String KEY_TARGET_WEIGHT = "target_weight";
    private static final String KEY_CREATED_DATE_TIME_ = "date_time_created";
    private static final String KEY_IMAGE = "image";

    private static final String DATABASE_ALTER_IMAGE = "ALTER TABLE "
            + TABLE_NAME + " ADD COLUMN "
            + KEY_IMAGE + " BLOB;";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_LIST_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_AGE_YEARS + " INTEGER DEFAULT 0,"
                + KEY_AGE_MONTHS + " INTEGER DEFAULT 0,"
                + KEY_CURRENT_WEIGHT + " INTEGER DEFAULT 0,"
                + KEY_TARGET_WEIGHT + " INTEGER DEFAULT 0,"
                + KEY_CREATED_DATE_TIME_ + " DATETIME,"
                + KEY_IMAGE + " BLOB" + ");";
        db.execSQL(CREATE_TODO_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if exited
//        db.execSQL("DROP TABLE IF EXITS " + TABLE_NAME);

        //create tables again
        onCreate(db);
        if (oldVersion < 2) {
            db.execSQL(DATABASE_ALTER_IMAGE);
        }
    }

    public boolean checkUserExist(String name) {
        SQLiteDatabase database = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + KEY_NAME + " = " + name;
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    //Adding an new task-item(#todo) to db
    public void addTodo(ProfileModel profileModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profileModel.getName());
        values.put(KEY_GENDER, profileModel.getGender());
        values.put(KEY_AGE_YEARS, profileModel.getAge_years());
        values.put(KEY_AGE_MONTHS, profileModel.getAge_months());
        values.put(KEY_CURRENT_WEIGHT, profileModel.getCurrent_weight());
        values.put(KEY_TARGET_WEIGHT, profileModel.getTarget_weight());
        values.put(KEY_CREATED_DATE_TIME_, profileModel.getDate_time_created());
        values.put(KEY_IMAGE, Utils.getBytes(profileModel.getImge_bitmap()));

        Log.d("Data Insertion ::--", values.toString());
        //Insert new row
        database.insert(TABLE_NAME, null, values);
        database.close();

    }

    public ProfileModel retrieveUserDetails() throws SQLException {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cur = database.query(true, TABLE_NAME, new String[]{KEY_NAME,
                KEY_GENDER, KEY_AGE_YEARS, KEY_AGE_MONTHS, KEY_CURRENT_WEIGHT, KEY_TARGET_WEIGHT, KEY_IMAGE}, null, null, null, null, KEY_ID + " DESC", null);
        if (cur.moveToFirst()) {
            String name = cur.getString(cur.getColumnIndex(KEY_NAME));
            String gender = cur.getString(cur.getColumnIndex(KEY_GENDER));
            int age_years = cur.getInt(cur.getColumnIndex(KEY_AGE_YEARS));
            int age_months = cur.getInt(cur.getColumnIndex(KEY_AGE_MONTHS));
            int current_weight = cur.getInt(cur.getColumnIndex(KEY_CURRENT_WEIGHT));
            int target_weight = cur.getInt(cur.getColumnIndex(KEY_TARGET_WEIGHT));
            byte[] blob = cur.getBlob(cur.getColumnIndex(KEY_IMAGE));
            cur.close();
            return new ProfileModel(name, gender, age_years, age_months, current_weight, target_weight, Utils.getPhoto(blob));
        }
        cur.close();
        return null;
    }


    //Get all task -item list from db
    public List<ProfileModel> getAllList() {
        List<ProfileModel> profileModelList = new ArrayList<>();
        //select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID  DESC ";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        //loop through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProfileModel profileModel = new ProfileModel();
                profileModel.setName(cursor.getString(1));
                profileModel.setGender(cursor.getString(2));
                profileModel.setAge_years(Integer.parseInt(cursor.getString(3)));
                profileModel.setAge_months(Integer.parseInt(cursor.getString(4)));
                profileModel.setCurrent_weight(Integer.parseInt(cursor.getString(5)));
                profileModel.setTarget_weight(Integer.parseInt(cursor.getString(6)));
                profileModel.setDate_time_created(cursor.getString(7));
                profileModelList.add(profileModel);
            } while (cursor.moveToNext());
        }
        // return all task item list
        return profileModelList;
    }


//    //Get all task -item list from db
//    public List<ProfileModel> getAllProfileList() {
//        List<ProfileModel> todoList = new ArrayList<>();
//        //select All Query
//        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID  DESC ";
//
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//
//        //loop through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ProfileModel profileModel = new ProfileModel();
//                profileModel.setName(cursor.getString(1));
//                profileModel.setGender(cursor.getString(2));
//                profileModel.setAge_years(Integer.parseInt(cursor.getString(3)));
//                profileModel.setAge_months(Integer.parseInt(cursor.getString(4)));
//                profileModel.setCurrent_weight(Integer.parseInt(cursor.getString(5)));
//                profileModel.setTarget_weight(Integer.parseInt(cursor.getString(6)));
//                profileModel.setDate_time_created(cursor.getString(7));
//                todoList.add(profileModel);
//            } while (cursor.moveToNext());
//        }
//        // return all task item list
//        return todoList;
//    }
}
