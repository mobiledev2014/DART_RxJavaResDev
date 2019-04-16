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
    public static final String token = "35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839";

    public static FragmentManager fManager;

    public static String selectedMD = "";
    public static String daySelected = "-1";
    public static String date = "";
    public static String psr = "Hello World!";
    public static String editNotes = "";
    public static boolean isMarkedAll = false;
    public static boolean submitStatus = true;

    // DIALOG MESSAGE
    public static String CLIENT_MANAGEMENT_TITLE = "Client Engagement";
    public static String CLIENT_MANAGEMENT_MESSAGE = "";
    public static String CLIENT_PRODUCT_COMMUNICATION_EXERCISE_TITLE = "Product Communication Exercise";
    public static String CLIENT_PRODUCT_COMMUNICATION_EXERCISE_MESSAGE = "";
    public static String CLIENT_SURVEY_ON_DRUGSTORE_AND_PHARMACIES_TITLE = "Survey on Drugstore and Pharmacies";
    public static String CLIENT_SURVEY_ON_DRUGSTORE_AND_PHARMACIES_MESSAGE = "";
    public static String CLIENT_COMPETITORS_ACTIVITY_TITLE = "Competitors Activity Report";
    public static String CLIENT_COMPETITORS_ACTIVITY_MESSAGE = "";
    public static String LOGIN_ERROR_TITLE = "Login Error";
    public static String LOGIN_ERROR_MESSAGE = "Username and Password is required.";

    // DIALOG TITLE AND MESSAGE
    public static String CANCEL_CONFIRMATION_TITLE = "Cancel Confirmation";
    public static String CANCEL_CONFIRMATION_MESSAGE = "Are you sure you want to cancel? All records will be discarded.";
    public static String SAVE_CONFIRMATION_TITLE = "Save Confirmation";
    public static String SAVE_CONFIRMATION_MESSAGE = "Are you sure you want to save all records?";
    public static String SUBMIT_CONFIRMATION_TITLE = "Submit Confirmation";
    public static String SUBMIT_CONFIRMATION_MESSAGE = "Are you sure you want to submit all records?";
    public static String SAVE_SUCCESS_TITLE = "Save Record";
    public static String SAVE_SUCCESS_MESSAGE = "Data has been successfully saved.";
    public static String SUBMIT_SUCCESS_TITLE = "Submit Success";
    public static String SUBMIT_SUCCESS_MESSAGE = "Data has been successfully submitted.";
    public static String SUBMIT_FAILED_TITLE = "Submit Error";
    public static String SUBMIT_FAILED_MESSAGE = "There is no internet connection detected. Please check your connection and try again.";
    public static String SYNC_SUCCESS_TITLE = "Sync Success";
    public static String SYNC_SUCCESS_MESSAGE = "Data has been successfully synced.";
    public static String SYNC_FAILED_TITLE = "Sync Error";
    public static String SYNC_FAILED_MESSAGE = "There is no internet connection detected. Please check your connection and try again.";
    public static String SUBMITTED_ALL_TITLE = "Submit information";
    public static String SUBMITTED_ALL_MESSAGE = "You have already submitted all data.";
    public static String NETWORK_CONNECTION_ERROR_TITLE = "Network error";
    public static String NETWORK_CONNECTION_ERROR_MESSAGE = "There is no internet connection detected. Please check your connection and try again.";
    public static String ADD_NOTES_CONFIRMATION_TITLE = "Add Note Confirmation";
    public static String ADD_NOTES_CONFIRMATION_MESSAGE = "Are you sure you want to save note?";
    public static String ADD_NOTES_SUCCESS_TITLE = "Save Success";
    public static String ADD_NOTES_SUCCESS_MESSAGE = "Note has been successfully saved.";
    public static String DELETE_NOTES_CONFIRMATION_TITLE = "Delete Note";
    public static String DELETE_NOTES_CONFIRMATION_MESSAGE = "Are you sure you want to delete these note(s)?";
    public static String DELETE_NOTES_SUCCESS_TITLE = "Delete Success";
    public static String DELETE_NOTES_SUCCESS_MESSAGE = "Note has been successfully deleted.";

    // ERROR MESSAGE FOR COMPLETION
    public static String ADD_ERROR_TITLE = "Add Error";
    public static String ADD_ERROR_MESSAGE = "You cannot add new md/client as the OJL is already submitted to PSR.";
    public static String SAVE_ERROR_TITLE = "Save Error";
    public static String SAVE_ERROR_MESSAGE = "You cannot save as the OJL is already submitted to PSR.";
    public static String SUBMIT_ERROR_TITLE = "Submit Error";
    public static String SUBMIT_ERROR_MESSAGE = "You cannot submit as the OJL is already submitted to PSR.";

    public static String ERROR_MESSAGE = "The data can no longer be submitted as the OJL is for completion already.";

    // SCREEN RESOLUTION
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_SIZE;

    // ARRAYLIST

    public static ArrayList<String> isNew = new ArrayList<>();

    //    CURRENT SAVE/EDIT BUTTON TEXT
    public static String CE_TXTSAVEBTN = "";
    public static String PC_TXTSAVEBTN = "";
    public static String SOD_TXTSAVEBTN = "";
    public static String CA_TXTSAVEBTN = "";

    //    PENDING DATA PER MODULE
    public static int CE_PENDINGDATA = 0;
    public static int PC_PENDINGDATA = 0;
    public static int SOD_PENDINGDATA = 0;
    public static int CA_PENDINGDATA = 0;

    //    CURRENT MODULE
    public static int CURRENT_MODULE = 0;

    // NOTE FUNCTION
    public static int CURRENT_SCREEN = 0;
}
