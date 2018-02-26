package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.Button;
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

import java.util.ArrayList;

public class ReferwinActivity extends AppCompatActivity {
    Toolbar toolbar;
    protected ArrayList<NavDrawerItem> _items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.app.ActionBar actionBar;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    ImageView cartImg;
    TextView counttt;
    protected static int position;
    private static boolean isLaunch = true;
    //Button gmailBtn,fbBtn;
    Button shareviaBtn;
    String subject="subject";
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
        setContentView(R.layout.activity_referwin);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartImg = (ImageView)findViewById(R.id.cartimg);
        shareviaBtn = (Button)findViewById(R.id.shareviaBtn);
        mainviewLayout = (RelativeLayout)findViewById(R.id.mainviewLayout);
        cartImg.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.refwin));
        navDrawer();
        /*gmailBtn = (Button)findViewById(R.id.gmail);
        fbBtn = (Button)findViewById(R.id.fb);*/
        counttt = (TextView)findViewById(R.id.counttt);
        counttt.setVisibility(View.GONE);

        shareviaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "http://www.yumtiff.com");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

       /* fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent facebookIntent = getOpenFacebookIntent(ReferwinActivity.this);
                startActivity(facebookIntent);

            }
        });

        gmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jimblackler@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, content);
                ReferwinActivity.this.startActivity(intent);

            }
        });*/

    }

    /*public void shareToGMail(String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = ReferwinActivity.this.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        ReferwinActivity.this.startActivity(emailIntent);
    }*/

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/254175194653125")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/arkverse")); //catches and opens a url to the desired page
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
                Intent i=new Intent(ReferwinActivity.this,HomeActivity.class);
                startActivity(i);
                break;
            case 1:
                Intent i1=new Intent(ReferwinActivity.this,MyAccountActivity.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2=new Intent(ReferwinActivity.this,OffercouponActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3=new Intent(ReferwinActivity.this,ReferwinActivity.class);
                startActivity(i3);
                break;
            case 4:
                Intent i4=new Intent(ReferwinActivity.this,SettingActivity.class);
                startActivity(i4);
                break;
            case 5:
                Intent i5=new Intent(ReferwinActivity.this,BookorderActivity.class);
                startActivity(i5);
                break;
            case 6:
                Intent i6=new Intent(ReferwinActivity.this,NotificationActivity.class);
                startActivity(i6);
                break;
            case 7:
                Intent i7=new Intent(ReferwinActivity.this,FeedbackActivity.class);
                startActivity(i7);
                break;
            case 8:
                Intent i8=new Intent(ReferwinActivity.this,LogoutActivity.class);
                startActivity(i8);
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


}

