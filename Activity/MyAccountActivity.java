package com.yumtiff.mohitkumar.tiffin.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
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

import com.yumtiff.mohitkumar.tiffin.Adapter.NavigationDrawerListAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.NavDrawerItem;
import com.yumtiff.mohitkumar.tiffin.R;

import java.io.InputStream;
import java.util.ArrayList;

public class MyAccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static TextView myprofileTv,myorderTv,changepwdTv;
    protected ArrayList<NavDrawerItem> _items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.app.ActionBar actionBar;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    protected static int position;
    private static boolean isLaunch = true;
    ImageView cartImg,accprofileIv;
    TextView counttt;
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
        setContentView(R.layout.activity_my_account);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartImg = (ImageView)findViewById(R.id.cartimg);
        myprofileTv = (TextView)findViewById(R.id.myprofile);
        myorderTv = (TextView)findViewById(R.id.myorder);
        changepwdTv = (TextView)findViewById(R.id.changepwd);
        accprofileIv = (ImageView)findViewById(R.id.profileImg);
        counttt = (TextView)findViewById(R.id.counttt);
        mainviewLayout = (RelativeLayout)findViewById(R.id.mainviewLayout);

        counttt.setVisibility(View.GONE);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.myaccount));
        navDrawer();
        cartImg.setVisibility(View.INVISIBLE);
       /* profileimgSp = getSharedPreferences(profileImgPrefrence, MODE_PRIVATE);
        profileimgStr = profileimgSp.getString("profileImg", "");
        Log.d("profileimgStr",profileimgStr);
        accprofileIv.setImageBitmap(BitmapFactory.decodeFile(profileimgStr));*/


        myprofileTv.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

            @Override
            public void onClick(View v) {
                myprofileTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg_selected));
                myorderTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg));
                changepwdTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg));
                Intent i=new Intent(MyAccountActivity.this,MyProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        myorderTv.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

            @Override
            public void onClick(View v) {
                myprofileTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg));
                myorderTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg_selected));
                changepwdTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg));
                Intent i=new Intent(MyAccountActivity.this,MyOrderActivity.class);
                startActivity(i);
                finish();

            }
        });



        changepwdTv.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                myprofileTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg));
                myorderTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg));
                changepwdTv.setBackground(getResources().getDrawable(R.drawable.myacc_text_bg_selected));
                Intent i=new Intent(MyAccountActivity.this,ChangePwdActivity.class);
                startActivity(i);
                finish();

            }
        });

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
                Intent i=new Intent(MyAccountActivity.this,HomeActivity.class);
                startActivity(i);
                break;
            case 1:
                Intent i1=new Intent(MyAccountActivity.this,MyAccountActivity.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2=new Intent(MyAccountActivity.this,OffercouponActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3=new Intent(MyAccountActivity.this,ReferwinActivity.class);
                startActivity(i3);
                break;
            case 4:
                Intent i4=new Intent(MyAccountActivity.this,SettingActivity.class);
                startActivity(i4);
                break;

            case 5:
                Intent i5=new Intent(MyAccountActivity.this,BookorderActivity.class);
                startActivity(i5);
                break;
            case 6:
                Intent i6=new Intent(MyAccountActivity.this,NotificationActivity.class);
                startActivity(i6);
                break;
            case 7:
                Intent i7=new Intent(MyAccountActivity.this,FeedbackActivity.class);
                startActivity(i7);
                break;
            case 8:
                Intent i8=new Intent(MyAccountActivity.this,LogoutActivity.class);
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

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
