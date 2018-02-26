package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Adapter.SettingNavAdapter;
import com.yumtiff.mohitkumar.tiffin.Model.SettingDrawerItem;
import com.yumtiff.mohitkumar.tiffin.R;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity  {

    Toolbar toolbar;
    TextView counttt;
    protected ArrayList<SettingDrawerItem> _items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.app.ActionBar actionBar;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    ImageView cartImg;
    protected static int position;
    private static boolean isLaunch = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartImg = (ImageView)findViewById(R.id.cartimg);
        cartImg.setVisibility(View.INVISIBLE);
        counttt = (TextView)findViewById(R.id.counttt);
        counttt.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.setting));
        navDrawer();

        //mDrawerLayout.openDrawer(mDrawerList);
    }

    private void navDrawer() {

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        //mDrawerLayout.setDrawerShadow(R.drawable.menu, GravityCompat.START);
        mDrawerLayout.openDrawer(Gravity.LEFT);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        //mDrawerLayout.openDrawer(mDrawerLayout);


        _items = new ArrayList<SettingDrawerItem>();
        _items.add(new SettingDrawerItem(getString(R.string.aboutus)));
        _items.add(new SettingDrawerItem(getString(R.string.privpolicy)));
        _items.add(new SettingDrawerItem(getString(R.string.termscondition)));
        _items.add(new SettingDrawerItem(getString(R.string.rateus)));
        _items.add(new SettingDrawerItem(getString(R.string.helpsupport)));

        /*View header = (View) getLayoutInflater().inflate(R.layout.nav_header_layout, null);
        mDrawerList.addHeaderView(header);*/

        mDrawerList.setAdapter(new SettingNavAdapter(this, _items));
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
               /* invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);*/
                getSupportActionBar().setTitle(getResources().getString(R.string.setting));
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
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

        switch (position) {
            case 0:
                Intent i=new Intent(SettingActivity.this,AboutUsActivity.class);
                startActivity(i);
                finish();
                break;
            case 1:
                Intent i2=new Intent(SettingActivity.this,PrivacypolicyActivity.class);
                startActivity(i2);
                finish();
                break;
            case 2:
                Intent i3=new Intent(SettingActivity.this,TermsConditionActivity.class);
                startActivity(i3);
                finish();
                break;
            case 3:
                Intent i4=new Intent(SettingActivity.this,RateusActivity.class);
                startActivity(i4);
                finish();
                break;
            case 4:
                Intent i6=new Intent(SettingActivity.this,HelpSupportActivity.class);
                startActivity(i6);
                finish();
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

}

