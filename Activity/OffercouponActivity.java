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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Adapter.NavigationDrawerListAdapter;
import com.yumtiff.mohitkumar.tiffin.Adapter.Offerscouponadapter;
import com.yumtiff.mohitkumar.tiffin.Adapter.OfrCopAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.NavDrawerItem;
import com.yumtiff.mohitkumar.tiffin.Model.OfferCoupon;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OffercouponActivity extends AppCompatActivity  {
    Toolbar toolbar;
    TextView counttt;
    protected ArrayList<NavDrawerItem> _items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.app.ActionBar actionBar;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList,ofrcouponListView;
    protected ImageView cartImg;
    List<OfferCoupon> offerCouponList;
    ListView offercoupenlistview;
    Offerscouponadapter offerscouponadapter;
    ProgressDialog pDialog;
    OfrCopAdapter ofrCopAdapter;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String id,userId,title,description,couponCode,amount,image,startDate,discountid,categoryid,validDate,createdOn,
            createdon,modifiedon,createdby,modifiedBy,indexValue,loginId;


    protected static int position;
    private static boolean isLaunch = true;
    int[] ofercoupnArr=new int[]{
            R.drawable.ofcopone,
            R.drawable.ofcoptwo,
            R.drawable.ofcopthree};

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
        setContentView(R.layout.activity_offercoupon);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartImg = (ImageView) findViewById(R.id.cartimg);
        cartImg.setVisibility(View.INVISIBLE);
        counttt = (TextView) findViewById(R.id.counttt);
        counttt.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.offrcoupon));
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        navDrawer();

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");

        ofrcouponListView = (ListView) findViewById(R.id.ofrcouponList);
        offerCouponList = new ArrayList<OfferCoupon>();



        if (Network.isNetworkCheck(getApplicationContext())) {
            getoffers();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
        }

    }

    private void navDrawer() {

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mainviewLayout = (RelativeLayout)findViewById(R.id.mainviewLayout);
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
        _items.add(new NavDrawerItem(getString(R.string.bookyourorder)));
        _items.add(new NavDrawerItem(getString(R.string.notification)));
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
                Intent i=new Intent(OffercouponActivity.this,HomeActivity.class);
                startActivity(i);
                break;
            case 1:
                Intent i1=new Intent(OffercouponActivity.this,MyAccountActivity.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2=new Intent(OffercouponActivity.this,OffercouponActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3=new Intent(OffercouponActivity.this,ReferwinActivity.class);
                startActivity(i3);
                break;
            case 4:
                Intent i4=new Intent(OffercouponActivity.this,SettingActivity.class);
                startActivity(i4);
                break;
            case 5:
                Intent i5=new Intent(OffercouponActivity.this,BookorderActivity.class);
                startActivity(i5);
                break;
            case 6:
                Intent i6=new Intent(OffercouponActivity.this,NotificationActivity.class);
                startActivity(i6);
                break;
            case 7:
                Intent i7=new Intent(OffercouponActivity.this,FeedbackActivity.class);
                startActivity(i7);
                break;
            case 8:
                Intent i8=new Intent(OffercouponActivity.this,LogoutActivity.class);
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

    private void getoffers() {
        showProgressDialog();
        offerCouponList = new ArrayList<OfferCoupon>();
        String offercoupon_url=Utility.Base_Url+"&action=get_offers_list&userId="+loginId;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, offercoupon_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("offerresponse", response.toString());
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            Boolean status = jobj.getBoolean("status");
                            Log.d("status",""+status);

                            JSONArray array = jobj.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject obj2 = array.getJSONObject(i);
                                id = obj2.getString("id");
                                userId = obj2.getString("userId");
                                title = obj2.getString("title");
                                description = obj2.getString("description");
                                couponCode = obj2.getString("couponCode");
                                amount = obj2.getString("amount");
                                image = obj2.getString("image");
                                startDate = obj2.getString("startDate");
                                validDate = obj2.getString("validDate");
                                indexValue = obj2.getString("indexValue");
                                createdOn = obj2.getString("createdOn");
                                modifiedon = obj2.getString("modifiedOn");
                                modifiedBy = obj2.getString("modifiedBy");
                                createdby = obj2.getString("createdBy");
                              /*   Log.d("amount",amount);
                              Log.d("id",id);
                                Log.d("Userid",userId);
                                Log.d("categoryid",categoryid);*/
                               /* Log.d("dishname",dishname);
                                Log.d("dishPrice",price);*/

                                OfferCoupon offerCoupon = new OfferCoupon();
                                offerCoupon.setId(id);
                                offerCoupon.setUserId(userId);
                                offerCoupon.setTitle(title);
                                offerCoupon.setDescription(description);
                                offerCoupon.setCouponCode(couponCode);
                                offerCoupon.setAmount(amount);
                                offerCoupon.setImage(image);
                                offerCoupon.setStartDate(startDate);
                                offerCoupon.setValidDate(validDate);
                                offerCoupon.setIndexValue(indexValue);
                                offerCoupon.setCreatedOn(createdOn);
                                offerCoupon.setModifiedBy(modifiedBy);
                                offerCoupon.setModifiedon(modifiedon);
                                offerCoupon.setCreatedby(createdby);
                                offerCouponList.add(offerCoupon);

                                offerscouponadapter = new Offerscouponadapter(OffercouponActivity.this,offerCouponList);
                                ofrcouponListView.setAdapter(offerscouponadapter);
                                offerscouponadapter.notifyDataSetChanged();

                                //Toast.makeText(getActivity(),""+breakFastList.toString(),Toast.LENGTH_LONG).show();

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
                        Log.d("error", error.toString());
                    }
                }); /*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_MOBILENO, mobileStr);
                params.put(Utility.KEY_PASSWORD, pwdStr);
                return params;
            }
        };
*/
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





