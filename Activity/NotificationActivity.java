package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Adapter.NavigationDrawerListAdapter;
import com.yumtiff.mohitkumar.tiffin.Adapter.NotiAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.NavDrawerItem;
import com.yumtiff.mohitkumar.tiffin.Model.Notificationn;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationActivity extends AppCompatActivity  {
    Toolbar toolbar;
    TextView counttt;
    ListView notilistView;
    protected ArrayList<NavDrawerItem> _items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.app.ActionBar actionBar;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    ImageView cartImg;
    protected static int position;
    private static boolean isLaunch = true;
    ProgressDialog pDialog;
    List<Notificationn> notiList;
    NotiAdapter notiAdapter;
    String id,notification,status,createdOn,modifiedOn,createdBy,modifiedBy;
    public static final String STORAGE_FBIMAGE = "fbimage";
    public static final String STORAGE_GMAILIMAGE="gmailimage";
    private String sgmailimg;
    SharedPreferences profileSp;
    public static final String profilePrefrence = "profileSp";
    String mailId,fbmailId;
    RelativeLayout mainviewLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartImg = (ImageView)findViewById(R.id.cartimg);
        cartImg.setVisibility(View.INVISIBLE);
        counttt = (TextView)findViewById(R.id.counttt);
        notilistView = (ListView)findViewById(R.id.notilistview);
        mainviewLayout = (RelativeLayout)findViewById(R.id.mainviewLayout);
        counttt.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.notification));
        navDrawer();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        if (Network.isNetworkCheck(getApplicationContext())) {
            getNotification();
        }
        else {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
        }
    }

    private void navDrawer() {

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);


        _items = new ArrayList<NavDrawerItem>();
        _items.add(new NavDrawerItem(getString(R.string.home)));
        _items.add(new NavDrawerItem(getString(R.string.myaccount)));
        _items.add(new NavDrawerItem(getString(R.string.offrcoupon)));
        _items.add(new NavDrawerItem(getString(R.string.refwin)));
        _items.add(new NavDrawerItem(getString(R.string.setting)));
        _items.add(new NavDrawerItem(getString(R.string.notification)));
        _items.add(new NavDrawerItem(getString(R.string.bookyourorder)));
        _items.add(new NavDrawerItem(getString(R.string.fdbk)));
        _items.add(new NavDrawerItem(getString(R.string.logout)));

        View header = (View) getLayoutInflater().inflate(R.layout.nav_header_layout, null);

        ImageView userimgIv=(ImageView)header.findViewById(R.id.item_icon_imgview);



        mDrawerList.addHeaderView(header);

        mDrawerList.setAdapter(new NavigationDrawerListAdapter(this, _items));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                R.drawable.menu,     /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,       /* "open drawer" description for accessibility */
                R.string.drawer_close)      /* "close drawer" description for accessibility */ {
            @Override
            public void onDrawerClosed(View drawerView) {
                /*getActionBar().setTitle(listArray[position]);*/
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainviewLayout.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }


            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);


        if (isLaunch) {

            isLaunch = false;
            openActivity(0);
        }
    }


    protected void openActivity(int position) {

        mDrawerLayout.closeDrawer(mDrawerList);

        switch (position - 1) {
            case 0:
                Intent i=new Intent(NotificationActivity.this,HomeActivity.class);
                startActivity(i);
                break;
            case 1:
                Intent i1=new Intent(NotificationActivity.this,MyAccountActivity.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2=new Intent(NotificationActivity.this,OffercouponActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3=new Intent(NotificationActivity.this,ReferwinActivity.class);
                startActivity(i3);
                break;
            case 4:
                Intent i4=new Intent(NotificationActivity.this,SettingActivity.class);
                startActivity(i4);
                break;
            case 5:
                Intent i5=new Intent(NotificationActivity.this,NotificationActivity.class);
                startActivity(i5);
                break;
            case 6:
                Intent i6=new Intent(NotificationActivity.this,BookorderActivity.class);
                startActivity(i6);
                break;
            case 7:
                Intent i7=new Intent(NotificationActivity.this,FeedbackActivity.class);
                startActivity(i7);
                break;
            case 8:
                Intent i8=new Intent(NotificationActivity.this,LogoutActivity.class);
                startActivity(i8);
                break;



            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
       /* menu.findItem(R.id.action_settings).setVisible(!drawerOpen);*/
        return super.onPrepareOptionsMenu(menu);
    }

    private void getNotification() {

        String notification_url=Utility.Base_Url+"&action=get_notification_list";

        notiList = new ArrayList<Notificationn>();
        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, notification_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();

                        //Toast.makeText(MenuActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("notiresponse", response.toString());

                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Response is Null", Toast.LENGTH_LONG).show();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean status = jsonObject.getBoolean("status");
                                if (status == true) {
                                    JSONArray array = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject obj = array.getJSONObject(i);

                                        String id,notification,statuss,createdOn,modifiedOn,createdBy,modifiedBy;

                                        id = obj.getString("id");
                                        notification = obj.getString("notification");
                                        statuss= obj.getString("status");
                                        createdOn = obj.getString("createdOn");
                                        modifiedOn = obj.getString("modifiedOn");
                                        createdBy = obj.getString("createdBy");
                                        modifiedBy = obj.getString("modifiedBy");


                                        Notificationn notificationn = new Notificationn();

                                        notificationn.setId(id);
                                        notificationn.setNotification(notification);
                                        notificationn.setStatus(statuss);
                                        notificationn.setCreatedOn(createdOn);
                                        notificationn.setModifiedOn(modifiedOn);
                                        notificationn.setCreatedBy(createdBy);
                                        notificationn.setModifiedBy(modifiedBy);

                                        notiList.add(notificationn);
                                    }

                                } else if (status==false) {
                                    Toast.makeText(getApplicationContext(),"No Data Found!",Toast.LENGTH_LONG).show();
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            notiAdapter = new NotiAdapter(NotificationActivity.this,notiList);
                            notilistView.setAdapter(notiAdapter);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        //Toast.makeText(MenuActivity.this,response,Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                        Log.d("error", error.toString());
                    }
                }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers == null) {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(),
                            response.notModified);
                }

                return super.parseNetworkResponse(response);
            };

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();


    }

}


