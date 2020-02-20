package com.techme.direction.helper;



public class VariablesHelper {

    // for database
    public static final String GROCERY = "grocery";
    public static final String DINING = "dining";
    public static final int FALSE = 0;
    public static final int TRUE = 1;
    public static String countryName = "Canada";


    // for activity and fragments
    public static final String EXTRA_NOTE_NAME = "note name";
    public static final String EXTRA_NOTE_ID = "note id";
    public static final String EXTRA_COUNTRY = "country";
    public static final String REPLACE = "replace";

    // for formatting store names that include spaces to fit Uri format
    public static String stringToUri(String name){
        String[] list = name.split(" ");
        String uriName = "";
        for(int i = 0; i < list.length; i++){
            if(i < list.length -1){
                uriName += list[i] + "+";
            }else {
                uriName += list[i];
            }
        }
        return uriName;
    }



    private VariablesHelper(){} // cant create an instance of this class
}
