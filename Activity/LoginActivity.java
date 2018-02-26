package com.yumtiff.mohitkumar.tiffin.Activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.SingleShotLocationProvider;
import com.yumtiff.mohitkumar.tiffin.Utility.CustomToast;
import com.yumtiff.mohitkumar.tiffin.Utility.GPSTracker;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.SessionManager;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Dialog dialog;
    Context context=this;
    private CallbackManager callbackManager;
    RelativeLayout forgotLayout,resetLayout,fcancelRl,fsubmitRl,resetcencelRl,resetsaveRl,otpLayout,allowRl,denyRl;
    EditText fmobileEt,newpwdEt,confpwdEt;
    CustomToast customToast;
    //ImageView fbloginBtn;
    TextView signupTv,forgotpwdTv,toasttxtTv;
    EditText mobileEt,pwdEt,otpEt;
    Button loginBtn;
    String mobileStr,pwdStr;
    String googleName,googlemail;

    String fmobStr;
    String otpStr,otpVerifiedId;
    //private ImageView signInButton;
    RelativeLayout verifyRl,resendotpRl;
    ProgressDialog pDialog;
    TextView otpTv;
    Bitmap fbpicture=null;
    private static final int PROFILE_PIC_SIZE = 400;
    String globalotpStr;
    LocationManager locationManager;

    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 100;

    public static final String STORAGE_FBIMAGE = "fbimage";
    public static final String STORAGE_GMAILIMAGE="gmailimage";
    private String fbid;
    public static String personPhotoUrl;
    String roleId,userName,image,mobileNumber,deliveryAddress,otp,password,qrcode,indexValue,
            createdOn,modifiedOn,createdBy,dataStr,displyMessage,officeAddress,newpwdStr,confirmpwdStr,userid,mailId;
    public static String id;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    SessionManager session;

    SharedPreferences profileSp;
    public static final String profilePrefrence = "profileSp";

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    String currentAddress;
    double latitude,longitude;
    List<String> userlocationList;
    LocationProvider provider;
    String flagStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        session = new SessionManager();



        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        flagStr = getIntent().getStringExtra("flag");
        //Log.d("flagStr",flagStr);


        customToast=new CustomToast();

        /*fbloginBtn = (ImageView) findViewById(R.id.fblogin);
        signInButton = (ImageView) findViewById(R.id.gmaillogin);*/
        signupTv = (TextView)findViewById(R.id.signup);
        forgotpwdTv = (TextView)findViewById(R.id.forgotpwd);
        mobileEt = (EditText)findViewById(R.id.mobile);
        pwdEt = (EditText)findViewById(R.id.pwd);
        loginBtn = (Button)findViewById(R.id.loginBtn);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "gooddog.otf");
        signupTv.setTypeface(face);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.userLoginclick=true;
                mobileStr=mobileEt.getText().toString();
                pwdStr=pwdEt.getText().toString();
                Log.d("mobile",mobileStr);
                Log.d("pwd",pwdStr);


                if (mobileStr.equalsIgnoreCase("") && pwdStr.equalsIgnoreCase("")) {

                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.fieldsblank));

                }
                else if (mobileStr.length()!=10) {

                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.validmobile));
                }
                else if (mobileStr.equalsIgnoreCase("")) {

                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.mobileblank));
                }
                else if (pwdStr.equalsIgnoreCase("")) {

                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.pwdblank));
                }
                else {

                    if (Network.isNetworkCheck(LoginActivity.this)) {
                        Global.userLoginclick=true;
                        getLogin();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


        /*fbloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.fbclick=true;
                if (Network.isNetworkCheck(LoginActivity.this)) {

                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(final LoginResult loginResult) {
                            Log.d("fb success", "success");

                            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            if (response != null) {
                                                try {
                                                    fbid = object.getString("id");
                                                    Global.fbname = object.getString("name");
                                                    Global.fbemail = object.getString("email");
                                                    Log.d("fbname", Global.fbname);
                                                    Log.d("fbemail", Global.fbemail);

                                                    profileSp = getApplicationContext().getSharedPreferences(profilePrefrence, MODE_PRIVATE);
                                                    SharedPreferences.Editor editorProfile = profileSp.edit();
                                                    editorProfile.putString("fbemail",Global.fbemail);
                                                    editorProfile.commit();
                                                    editorProfile.apply();


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email");
                            request.setParameters(parameters);
                            request.executeAndWait();
                            //updateProfile();
                            getfbProfilePic();

                            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                            startActivity(i);

                           *//* SharedPreferences sprefFb = getApplicationContext().getSharedPreferences(STORAGE_FB, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sprefFb.edit();
                            editor.putString("fbname", Global.fbname);
                            editor.putString("fbid", fbid);
                            editor.putString("fbemail", Global.fbemail);
                            editor.commit();
                            editor.apply();
                        }
*//*
                        }
                        @Override
                        public void onCancel() {
                            Log.d("fb cancel", "cancel fb");
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Log.d("error", exception.toString());
                        }
                    });
                }
               *//* else {
                    //Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }*//*
            }
        });*/

        /*signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });*/

        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);

            }
        });

        forgotpwdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(LoginActivity.this,ForgotPwdActivity.class);
                startActivity(i);
                finish();*/

                dialog = new Dialog(context);
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forgotpwd_popup_layout);
                dialog.show();

                forgotLayout = (RelativeLayout)dialog.findViewById(R.id.forgotLayout);
                resetLayout = (RelativeLayout)dialog.findViewById(R.id.resetLayout);
                fcancelRl = (RelativeLayout)dialog.findViewById(R.id.fcencelRl);
                fsubmitRl = (RelativeLayout)dialog.findViewById(R.id.fsubmitRl);
                resetcencelRl = (RelativeLayout)dialog.findViewById(R.id.resetcencelRl);
                resetsaveRl = (RelativeLayout)dialog.findViewById(R.id.resetsaveRl);
                otpLayout = (RelativeLayout)dialog.findViewById(R.id.otpLayout);
                resendotpRl = (RelativeLayout)dialog.findViewById(R.id.resendotpRl);
                verifyRl = (RelativeLayout)dialog.findViewById(R.id.verifyRl);
                fmobileEt = (EditText)dialog.findViewById(R.id.fmobile);
                newpwdEt = (EditText)dialog.findViewById(R.id.newpwd);
                confpwdEt = (EditText)dialog.findViewById(R.id.confpwd);
                otpEt = (EditText)dialog.findViewById(R.id.otp);
                forgotLayout.setVisibility(View.VISIBLE);
                resetLayout.setVisibility(View.INVISIBLE);

                fcancelRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                fsubmitRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fmobStr = fmobileEt.getText().toString();
                        if (fmobStr.equalsIgnoreCase("")) {
                            showCustomAlert();
                            toasttxtTv.setText(getResources().getString(R.string.mobileblank));
                        } else if (fmobStr.length() != 10) {
                            showCustomAlert();
                            toasttxtTv.setText(getResources().getString(R.string.validmobile));
                        } else {
                            forgotLayout.setVisibility(View.INVISIBLE);
                            resetLayout.setVisibility(View.INVISIBLE);
                            otpLayout.setVisibility(View.VISIBLE);
                            getotp();




                            /*resetcencelRl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fmobileEt.setText("");
                                    forgotLayout.setVisibility(View.VISIBLE);
                                    resetLayout.setVisibility(View.INVISIBLE);
                                    otpLayout.setVisibility(View.INVISIBLE);

                                }
                            });

                            resetsaveRl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    newpwdStr = newpwdEt.getText().toString();
                                    confirmpwdStr = confpwdEt.getText().toString();
                                    if (newpwdStr.equalsIgnoreCase("")) {
                                        showCustomAlert();
                                        toasttxtTv.setText(getResources().getString(R.string.newpwdblank));
                                    } else if (confirmpwdStr.equalsIgnoreCase("")) {
                                        showCustomAlert();
                                        toasttxtTv.setText(getResources().getString(R.string.cpwdblank));
                                    } else if (!confirmpwdStr.equalsIgnoreCase(newpwdStr)) {
                                        showCustomAlert();
                                        toasttxtTv.setText(getResources().getString(R.string.confirmpwd));
                                    } else {
                                        dialog.dismiss();
                                    }
                                }
                            });*/
                        }
                    }
                });
            }
        });

        //foo(LoginActivity.this);

    }



  /*  private void showGPSDisabledAlertToUser() {

        dialog = new Dialog(context);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gps_popup_layout);
        dialog.show();
        allowRl = (RelativeLayout) dialog.findViewById(R.id.allowRl);
        denyRl = (RelativeLayout) dialog.findViewById(R.id.denyRl);

        allowRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
                try {
                    getcurrentLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();


            }
        });

        denyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(LoginActivity.this,UserlocationActivity.class);
                startActivity(intent);
            }
        });

    }


    private void getcurrentLocation() throws IOException {

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        GPSTracker gps = new GPSTracker(LoginActivity.this);


         latitude = gps.getLatitude();
         longitude = gps.getLongitude();

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        if (addresses != null && !addresses.isEmpty()) {

            String address = addresses.get(0).getAddressLine(0);
            String street = addresses.get(0).getSubLocality();
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            currentAddress = address + " , " + street + " , " + city + " " + state + " , " + country + " , " + postalCode;
        }
        else {
            Toast.makeText(getApplicationContext(),"Address is Empty",Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Address: "+currentAddress, Toast.LENGTH_SHORT).show();
        Log.d("Latlong", Double.toString(latitude) + " " + Double.toString(longitude));
        //Log.d("Address", currentAddress);

       *//* GPSTracker GTracker = new GPSTracker(LoginActivity.this);

        if (GTracker != null) {
            System.out.print("location is NOT null.... ");


            latitude = GTracker.getLatitude();
            longitude = GTracker.getLongitude();


            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                currentCityName = addresses.get(0).getAddressLine(2);
                //String stateName = addresses.get(0).getAddressLine(2);
                //String countryName = addresses.get(0).getAddressLine(3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            latitude = Double.parseDouble(null);
            longitude = Double.parseDouble(null);

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            Log.d("Latlong", Double.toString(latitude) + " " + Double.toString(longitude));
            Log.d("Address", currentCityName);

        }*//*
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


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/


    private void verifyforgotpassword() {

        globalotpStr=otpEt.getText().toString();
        Log.d("globalotpStr",globalotpStr);

        String OTP_URL=Utility.Base_Url+"&action=forget_otp_varified&type=Edit&mobileNumber="+fmobStr+"&otp="+globalotpStr;

        //http://www.yumtiff.com/api/ws/controller/?access=true&action=forget_otp_varified&type=Edit&mobileNumber=8510808187&otp=

        Log.d("OTP_URL",OTP_URL);

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=forgot_password&type=Edit&mobileNumber=8510808187

        //showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("forgototpverifyresponse",response.toString());
                        JSONObject jobj= null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status=jobj.getBoolean("status");
                            Log.d("forgotStatus",""+status);
                            if (status==true) {

                                JSONObject jsonObject=jobj.getJSONObject("data");
                                otpVerifiedId=jsonObject.getString("id");



                                displyMessage=jobj.getString("displyMessage");

                                //Toast.makeText(getApplicationContext(),"forgot",Toast.LENGTH_LONG).show();

                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                                Log.d("displyMessage",displyMessage);

                                resetLayout.setVisibility(View.VISIBLE);
                                otpLayout.setVisibility(View.INVISIBLE);
                                forgotLayout.setVisibility(View.INVISIBLE);

                               /* newpwdStr=newpwdEt.getText().toString();
                                confirmpwdStr=confpwdEt.getText().toString();*/

                                resetcencelRl.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                                resetsaveRl.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        newpwdStr=newpwdEt.getText().toString();
                                        confirmpwdStr=confpwdEt.getText().toString();
                                        getupdatepwd();
                                    }
                                });


                            }
                            else if (status==false) {
                                displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // hideProgressDialog();
                        Log.d("error",error.toString());
                    }
                }) {
           /* @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Utility.KEY_MOBILENUMBER,mobileNumber);
              //  params.put(Utility.KEY_OTP, otp);
                return params;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

     private void getupdatepwd() {
        Log.d("otpVerifiedId",otpVerifiedId);
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        userid = loginSp.getString("loginId", "");


        String OTP_URL=Utility.Base_Url+"&action=manage_users&type=Edit&editId="+otpVerifiedId+"&password="+newpwdStr;

        http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=manage_users&type=Edit&editId=321&password=12345

        Log.d("OTP_PWD_URL",OTP_URL);

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=forgot_password&type=Edit&mobileNumber=8510808187

        //showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("passwordresponse",response.toString());
                        JSONObject jobj= null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status=jobj.getBoolean("status");
                            Log.d("forgotStatus",""+status);
                            if (status==true) {



                                displyMessage=jobj.getString("displyMessage");

                                //Toast.makeText(getApplicationContext(),"forgot",Toast.LENGTH_LONG).show();

                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                                Log.d("displyMessage",displyMessage);
                                dialog.dismiss();


                            }
                            else if (status==false) {
                                displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // hideProgressDialog();
                        Log.d("error",error.toString());
                    }
                }) {
           /* @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Utility.KEY_MOBILENUMBER,mobileNumber);
              //  params.put(Utility.KEY_OTP, otp);
                return params;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getLogin() {

        String login_url=Utility.Base_Url+"&action=user_login&mobileNumber="+mobileStr+"&password="+pwdStr;

        Log.d("login_url",login_url);

        //http://www.yumtiff.com/api/ws/controller/?access=true&action=user_login&mobileNumber=8470072027&password=12345

        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            hideProgressDialog();
                            //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            Log.d("loginresponseeee",response.toString());
                            JSONObject jobj= null;
                            try {
                                jobj = new JSONObject(response);
                                boolean status=jobj.getBoolean("status");
                                if (status==true) {

                                     JSONObject jsonObject=jobj.getJSONObject("data");
                                     id=jsonObject.getString("id");
                                     userName=jsonObject.getString("userName");
                                     mailId=jsonObject.getString("mailId");
                                     image=jsonObject.getString("image");
                                     mobileNumber=jsonObject.getString("mobileNumber");
                                     deliveryAddress=jsonObject.getString("deliveryAddress");
                                     officeAddress=jsonObject.getString("officeAddress");
                                     otp=jsonObject.getString("otp");
                                     password=jsonObject.getString("password");
                                     qrcode=jsonObject.getString("qrcode");
                                    /* merchantId=jsonObject.getString("merchantId");
                                     indexValue=jsonObject.getString("indexValue");
                                     createdOn=jsonObject.getString("createdOn");
                                     modifiedOn=jsonObject.getString("modifiedOn");
                                     createdBy=jsonObject.getString("createdBy");
                                     modifiedBy=jsonObject.getString("modifiedBy");*/

                                    userid=id;

                                    Log.d("userIddddd",id);
                                    Log.d("userName",userName);
                                    Log.d("mailId",mailId);
                                    Log.d("officeAddress",officeAddress);

                                    loginSp = getApplicationContext().getSharedPreferences(loginPrefrence, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = loginSp.edit();
                                    editor.putString("loginId",id);
                                    editor.putString("deliveryAddress",deliveryAddress);
                                    editor.putString("officeAddress",officeAddress);
                                    editor.commit();
                                    editor.apply();

                                    profileSp = getApplicationContext().getSharedPreferences(profilePrefrence, MODE_PRIVATE);
                                    SharedPreferences.Editor editorProfile = profileSp.edit();
                                    editorProfile.putString("mailId",mailId);
                                    editorProfile.putString("userName",userName);
                                    editorProfile.putString("deliveryAddress",deliveryAddress);
                                    editorProfile.putString("officeAddress",officeAddress);
                                    editorProfile.putString("currentAddress",currentAddress);
                                    editorProfile.commit();
                                    editorProfile.apply();

                                    displyMessage=jobj.getString("displyMessage");
                                    session.setPreferences(LoginActivity.this, "status", "1");
                                    String statuss=session.getPreferences(LoginActivity.this,"status");
                                    Log.d("status",statuss);
                                    if (flagStr.equalsIgnoreCase("1")) {
                                        Intent i = new Intent(LoginActivity.this, CheckoutActivity.class);
                                        startActivity(i);

                                    }
                                    else if (flagStr.equalsIgnoreCase("2")) {
                                        Intent i = new Intent(LoginActivity.this, PreCheckoutActivity.class);
                                        startActivity(i);
                                    }
                                    else if (flagStr.equalsIgnoreCase("3")) {
                                        Intent i = new Intent(LoginActivity.this, TiffinCheckoutActivity.class);
                                        startActivity(i);

                                    }

                                    Log.d("displyMessage",displyMessage);

                                }
                                else if (status==false) {
                                    displyMessage=jobj.getString("displyMessage");
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.usernamepwdinc),Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideProgressDialog();
                            Log.d("error",error.toString());
                        }
                    }) {
                /*@Override
                protected Map<String,String> getParams() {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(Utility.KEY_MOBILENO, mobileStr);
                    params.put(Utility.KEY_PASSWORD, pwdStr);
                    return params;
                }*/
            };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
       //* SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
        //SharedPreferences.Editor editor = loginSp.edit();


//**************** Storing data as KEY/VALUE pair *******************//**//*

        /*editor.putBoolean(" KEY_MOBILENO", true);           // Saving boolean - true/false
        editor.putInt("key_name2", Integer.parseInt("int value"));        // Saving integer
        editor.putFloat("KEY_PASSWORD", Float.parseFloat("float value"));    // Saving float*/



    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }



    private void getFbKeyHash(String s) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.mohitkumar.tiffin",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public void getfbProfilePic() {

        String sdpath= Environment.getExternalStorageDirectory()+ File.separator+"fbimg.jpg";
        SharedPreferences ss2= getSharedPreferences(STORAGE_FBIMAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ss2.edit();

        Profile profile=Profile.getCurrentProfile();
        if (profile!=null) {

            URL url = null;
            try {
                url = new URL("https://graph.facebook.com/"+profile.getId()+"/picture?type=large");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpURLConnection.setFollowRedirects(true);
            conn.setInstanceFollowRedirects(true);

            try {
                fbpicture = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //pp.setImageBitmap(fbpicture);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            fbpicture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            File f=new File(sdpath);

            FileOutputStream fos= null;
            try {
                f.createNewFile();
                fos = new FileOutputStream(f);
                fos.write(baos.toByteArray());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("fbpic",""+fbpicture);

            editor.putString("imgpref", sdpath);
            editor.apply();
            editor.commit();

                /*byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);*/
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                fbpicture.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();*/


            //editor.putString("imgpref", encodeTobase64(fbpicture));
            //ppImg.setImageBitmap(fbpicture);

        }

        else {
            Toast.makeText(getApplicationContext(),"Please Connect to Internet",Toast.LENGTH_LONG).show();
        }

    }


    public void verifyforgotPwd() {

        dialog = new Dialog(context);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.verify_popup_layout);
        dialog.show();
        otpTv = (EditText) dialog.findViewById(R.id.otp);


        resendotpRl = (RelativeLayout)dialog.findViewById(R.id.resendotpRl);
        verifyRl = (RelativeLayout)dialog.findViewById(R.id.verifyRl);
        resendotpRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        verifyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fmobileEt.setText("");
                forgotLayout.setVisibility(View.VISIBLE);
                resetLayout.setVisibility(View.INVISIBLE);
                Intent i=new Intent(LoginActivity.this,LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


   /* public void getFbKeyHash(String packageName) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }*/

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent i) {
        callbackManager.onActivityResult(reqCode, resCode, i);
        if (resCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(i);
            //Calling a new function to handle signin
            handleSignInResult(result);

            Intent i1=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(i1);
            finish();
        }
    }



    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            Toast.makeText(this, "Login Google plus", Toast.LENGTH_LONG).show();
            GoogleSignInAccount acct = result.getSignInAccount();

            Global.gmlname=acct.getDisplayName();
            Global.gmlemail=acct.getEmail();

            Log.d("googlename",googleName);
            Log.d("googlemail",googlemail);

            personPhotoUrl = personPhotoUrl.substring(0,
                    personPhotoUrl.length() - 2)
                    + PROFILE_PIC_SIZE;

            SharedPreferences spgmail = getApplicationContext().getSharedPreferences(STORAGE_GMAILIMAGE, MODE_PRIVATE);
            SharedPreferences.Editor editor = spgmail.edit();
            editor.putString("gmailImg",personPhotoUrl);
            editor.putString("gmailemail",googlemail);
            editor.commit();
            editor.apply();

            Intent i=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(i);
            finish();


            //Initializing image loader
          /*  imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();

            imageLoader.get(acct.getPhotoUrl().toString(),
                    ImageLoader.getImageListener(profilePhoto,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));

            //Loading image
            profilePhoto.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);*/

        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    public void showCustomAlert() {

        Context context = getApplicationContext();
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = getLayoutInflater();

        // Call toast.xml file for toast layout
        View toastRoot = inflater.inflate(R.layout.activity_custom_toast, null);
        toasttxtTv = (TextView)toastRoot.findViewById(R.id.toasttxt);

        Toast toast = new Toast(context);

        // Set layout to toast
        toast.setView(toastRoot);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }


  /*  public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;*/

  private void getotp() {


      String OTP_URL=Utility.Base_Url+"&action=forgot_password&type=Edit&mobileNumber="+fmobStr;

      //http://www.yumtiff.com/api/ws/controller/?access=true&action=forgot_password&type=Edit&mobileNumber=8510808187

      Log.d("OTP_URL",OTP_URL);

      //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=forgot_password&type=Edit&mobileNumber=8510808187

      //showProgressDialog();

      StringRequest stringRequest = new StringRequest(Request.Method.GET, OTP_URL,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      //hideProgressDialog();
                      //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                      Log.d("forgototpresponse",response.toString());
                      JSONObject jobj= null;
                      try {
                          jobj = new JSONObject(response);
                          boolean status=jobj.getBoolean("status");
                          Log.d("forgotStatus",""+status);
                          if (status==true) {



                              displyMessage=jobj.getString("displyMessage");

                              //Toast.makeText(getApplicationContext(),"forgot",Toast.LENGTH_LONG).show();

                              Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                              Log.d("displyMessage",displyMessage);
                              String otpStr=otpEt.getText().toString();
                              globalotpStr=otpStr;
                              Log.d("otpStr",otpStr);
                              resendotpRl.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      getotp();

                                  }
                              });

                              verifyRl.setOnClickListener(new View.OnClickListener() {

                                  @Override
                                  public void onClick(View v) {

                                      if (otpEt.getText().toString().equalsIgnoreCase("")) {
                                          showCustomAlert();
                                          toasttxtTv.setText(getResources().getString(R.string.otpblank));
                                      } else {

                                        /*forgotLayout.setVisibility(View.INVISIBLE);
                                        otpLayout.setVisibility(View.INVISIBLE);
                                        resetLayout.setVisibility(View.VISIBLE);*/
                                          //verifyforgotPwd();
                                          //Toast.makeText(getApplicationContext(), "otppp", Toast.LENGTH_LONG).show();
                                          verifyforgotpassword();
                                      }
                                  }
                              });

                          }
                          else if (status==false) {
                              displyMessage=jobj.getString("displyMessage");
                              Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                          }
                          else {
                              Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                     // hideProgressDialog();
                      Log.d("error",error.toString());
                  }
              }) {
           /* @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Utility.KEY_MOBILENUMBER,mobileNumber);
              //  params.put(Utility.KEY_OTP, otp);
                return params;
            }*/
      };

      RequestQueue requestQueue = Volley.newRequestQueue(this);
      requestQueue.add(stringRequest);
       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSp.edit();


*//**************** Storing data as KEY/VALUE pair *******************//*

        editor.putBoolean(" KEY_MOBILENO", true);           // Saving boolean - true/false
        editor.putInt("key_name2", Integer.parseInt("int value"));        // Saving integer
        editor.putFloat("KEY_PASSWORD", Float.parseFloat("float value"));    // Saving float
      */


  }

    private void getverify() {


    }


    /*private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }*/

    /*  private void showProgressDialog() {
          if (!pdialog.isShowing())
              pdialog.show();
      }

      private void hideProgressDialog() {
          if (pdialog.isShowing())
              pdialog.hide();*/
    private void getotpverified() {
        otpStr=otpEt.getText().toString();
        String OTPVERIFY_OTP=Utility.Base_Url+"&action=otp_varified&otp="+otpStr+"&mobileNumber="+mobileStr;
        //String otpverified_url=Utility.Base_Url+"&action=otp_varified&type=Edit&otp"+otpStr;

        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTPVERIFY_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("otpresponse",response.toString());
                        JSONObject jobj= null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status=jobj.getBoolean("status");
                            if (status==true) {


                                dataStr = jobj.getString("data");
                                displyMessage = jobj.getString("displyMessage");
                                Log.d("displyMessage", displyMessage);
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                i.putExtra("mobile", mobileStr);
                                startActivity(i);
                                finish();

                            }
                            else if (status==false) {
                                displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Log.d("error",error.toString());
                    }
                }) {
         /* @Override
          protected Map<String,String> getParams() {
              Map<String,String> params = new HashMap<String, String>();
              params.put(Utility.KEY_OTP,otp);
              //  params.put(Utility.KEY_OTP, otp);
              return params;
          }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSp.edit();


*//**************** Storing data as KEY/VALUE pair *******************//*

        editor.putBoolean(" KEY_MOBILENO", true);           // Saving boolean - true/false
        editor.putInt("key_name2", Integer.parseInt("int value"));        // Saving integer
        editor.putFloat("KEY_PASSWORD", Float.parseFloat("float value"));    // Saving float
      */


    }


    /*private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }*/

  /*  private void showProgressDialog() {
        if (!pdialog.isShowing())
            pdialog.show();
    }

    private void hideProgressDialog() {
        if (pdialog.isShowing())
            pdialog.hide();*/



}

