package com.pp1.parkingfinder.model;

public class Constants {
    public static final String URL_PF_ENDPOINT =
            "https://avid-invention-249406.appspot.com/";
            //"https://postman-echo.com/";


    public static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static final Object SUCCESS_RESULT = 9004;
    public static final Object FAILURE_RESULT = 9005;
    public static final String LOCATION_DATA_EXTRA = "";

    // Booking key values
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String AVAILABILITY = "availability";
}