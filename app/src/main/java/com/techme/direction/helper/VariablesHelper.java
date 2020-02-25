package com.techme.direction.helper;


import android.Manifest;

public class VariablesHelper {

    // to know which new code to use next time for intents (update if create a new code)
    private int nextCodeToUse = 4;

    // countries
    public static final String CANADA = "Canada";
    public static final String USA = "USA";

    // for database
    public static final String GROCERY = "grocery";
    public static final String DINING = "dining";
    public static final int FALSE = 0;
    public static final int TRUE = 1;
    public static String countryName = "none";
    public static boolean countrySelected = false;
    public static final String DAILY_NOTES = "Daily";


    // for activity and fragments
    public static final String EXTRA_NOTE_NAME = "note name";
    public static final String EXTRA_NOTE_ID = "note id";
    public static final int EXTRA_COUNTRY_CODE = 2;
    public static final String EXTRA_FRAGMENT = "fragment note";
    public static final String NOTE_FRAGMENT = "note";
    public static final String DINING_FRAGMENT = "dining";
    public static final String GROCERY_FRAGMENT = "grocery";
    public static final String STORE_FRAGMENT = "store";
    public static final int MY_STORE_ACTIVITY_CODE = 3;

    // for search view
    public static final String REPLACE = "replace";

    // recycle view cache size
    public static final int RECYCLE_CACHE = 7;

    // for shared preferences
    public static final String SHARED_PREF_COUNTRY = "hold country";
    public static final String LOAD_COUNTRY = "get country";

    // for permissions
    public static final int ALL_PERMISSION_CODE = 1;
    public static final String[] APP_PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET};


    // for formatting store names that include spaces to fit Uri format
    public static String stringToUri(String name) {
        String[] list = name.split(" ");
        String uriName = "";
        for (int i = 0; i < list.length; i++) {
            if (i < list.length - 1) {
                uriName += list[i] + "+";
            } else {
                uriName += list[i];
            }
        }
        return uriName;
    }


    private VariablesHelper() {
    } // cant create an instance of this class
}
