package com.unilab.gmp.utility;

import android.app.FragmentManager;
import android.content.Context;

import java.util.ArrayList;


/**
 * Created by c_jsbustamante on 8/1/2016.
 */

// Stefan Kurry Irving was here
public class Glovar {
    public static String LOGIN = "LOGIN";
    public static String POST_AUDIT = "POST_AUDIT_REPORT";
    public static String POST_TEMPLATE = "POST_TEMPLATE";

    public static Context context;
    // *****************************************
    // TRUE - DEV MODE
    // FALSE - PROD/QA/SA
    public static boolean debugging = true;
    public static boolean testMode = true;
    // *****************************************
    public static boolean isToday = true;
    // *****************************************
    // DEV - http://mrdgnsndp.hol.es/
    // PROD/QA/SA - http://biomedisojl.unilab.com.ph/
    // public static String URL = "http://biomedisojl.unilab.com.ph/";
    // public static String URL = "http://ojl.ecomqa.com";
    public static String URL = "http://mrdgnsndp.hol.es/";
    public static String date = "";

    // SCREEN RESOLUTION
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_SIZE;
}
