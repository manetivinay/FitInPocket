package com.example.thukuntla_sai.FitInPocket;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import java.io.ByteArrayOutputStream;

public class Utils {

    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;
    public final static int THEME_BLUE = 2;


    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        } else {
            return null;
        }
    }

    // convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image) {
        if (image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return null;
    }

    public static void themeSettings(Activity activity) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(activity);
        String themeName = pref.getString("theme", "green");
        if (themeName.equals("green")) {
            activity.setTheme(R.style.green);
        } else if (themeName.equals("red")) {
            activity.setTheme(R.style.red);
        } else if (themeName.equals("blue")) {
            activity.setTheme(R.style.blue);
        } else if (themeName.equals("grey")) {
            activity.setTheme(R.style.grey);
        } else if (themeName.equals("yellow")) {
            activity.setTheme(R.style.yellow);
        }
    }
}