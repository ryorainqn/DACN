package com.example.dell.dacn.SaveLogin;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dell.dacn.Model.Login;

import java.util.HashMap;

/**
 * Created by DELL on 20/10/17.
 */

public class SessionManagerUser {


//    private static SessionManagerUser instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;
    private Context context;

    public SessionManagerUser(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    private static final String PREF_NAME = "DataUser";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_CREATE_DATE = "createDate";
    public static final String KEY_ALLOW_NOTIFICATION = "allowNotification";


    /**
     * Create login session
     */
    public void createLoginSession(Login dataUser) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing in pref
        editor.putString(KEY_TOKEN, dataUser.getToken());
        editor.putString(KEY_ID, dataUser.getProfile().getUserId());
        editor.putString(KEY_EMAIL, dataUser.getProfile().getEmail());
        editor.putString(KEY_FULLNAME, dataUser.getProfile().getName());
        editor.putString(KEY_IMAGE, dataUser.getProfile().getImg_Profile());
        editor.putString(KEY_ADDRESS, dataUser.getProfile().getAddress());
        editor.putString(KEY_BIRTHDAY, dataUser.getProfile().getDate());
        editor.putString(KEY_IMAGE, dataUser.getProfile().getImg_Profile());
        // commit changes
        editor.commit();
    }


    public void updateUserProfileSession(Login dataUser) {
        // Storing in pref
        editor.putString(KEY_TOKEN, dataUser.getToken());
        editor.putString(KEY_ID, dataUser.getProfile().getUserId());
        editor.putString(KEY_EMAIL, dataUser.getProfile().getEmail());
        editor.putString(KEY_FULLNAME, dataUser.getProfile().getName());
        editor.putString(KEY_IMAGE, dataUser.getProfile().getImg_Profile());
        editor.putString(KEY_ADDRESS, dataUser.getProfile().getAddress());
        editor.putString(KEY_BIRTHDAY, dataUser.getProfile().getDate());
        editor.putString(KEY_IMAGE, dataUser.getProfile().getImg_Profile());
        // commit changes
        editor.commit();
    }

    public void createLoginSessionFace() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    // Remove value whose key
    public void removeValue() {
        editor.remove(KEY_ID);
        editor.remove(KEY_FULLNAME);
        editor.remove(KEY_ADDRESS);
        editor.remove(KEY_PHONE);
        editor.remove(KEY_BIRTHDAY);
        editor.remove(KEY_IMAGE);
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> data = new HashMap<String, String>();
        // data name
        data.put(KEY_TOKEN, pref.getString(KEY_TOKEN, ""));
        data.put(KEY_ID, pref.getString(KEY_ID, null));
        data.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        data.put(KEY_FULLNAME, pref.getString(KEY_FULLNAME, null));
        data.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        data.put(KEY_BIRTHDAY,pref.getString(KEY_BIRTHDAY,""));
        data.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        // return data
        return data;
    }

    public String getUserToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    //change noti
    public void addNotificationSession(boolean state) {
        editor.putBoolean(KEY_ALLOW_NOTIFICATION, state);
        editor.commit();
    }

    public boolean getNotification() {
        boolean state = pref.getBoolean(KEY_ALLOW_NOTIFICATION, true);
        return state;
    }

}
