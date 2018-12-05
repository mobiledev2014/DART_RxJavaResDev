package com.unilab.gmp.utility;

import java.util.HashMap;

/**
 * Created by c_rcmiguel on 1/31/2017.
 */

public class Variable {
    public static boolean menu = false;
    public static boolean onTemplate = false;
    public static boolean onAudit = false;
    public static boolean onReferenceData = false;
    public static boolean isAuthorized = true;
   // public static boolean isChangedSite = false;
    public static boolean isFromBackStack = false;
    public static String checkValue = "";
    public static String elementId = "";
    public static boolean showDialog = true;
    public static int session = 0;
    public static String timeStamp = "";
    public static String status = "";
    public static HashMap<String, String> selectedProduct = new HashMap<String, String>();
    public static HashMap<String, String> selectedDisposition = new HashMap<String, String>();
    public static HashMap<String, String> elementSelect = new HashMap<String, String>();
    public static String report_id = "0";
    public static String selecteddp = "0";
}
