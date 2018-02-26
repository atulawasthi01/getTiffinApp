package com.yumtiff.mohitkumar.tiffin.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.Adapter.NavigationDrawerListAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.NavDrawerItem;
import com.yumtiff.mohitkumar.tiffin.R;

import java.util.ArrayList;

public class BookorderActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button callnow;
    TextView counttt;
    protected ArrayList<NavDrawerItem> _items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.app.ActionBar actionBar;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    ImageView cartImg;
    protected static int position;
    private static boolean isLaunch = true;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 123;
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
        setContentView(R.layout.activity_bookorder);
        callnow = (Button) findViewById(R.id.callnowBtn);
        mainviewLayout = (RelativeLayout)findViewById(R.id.mainviewLayout);

        callnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //onCall();


                makePhoneCall(view);

                /*Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("1800888888"));
                if (ActivityCompat.checkSelfPermission(BookorderActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);*/


            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartImg = (ImageView)findViewById(R.id.cartimg);
        cartImg.setVisibility(View.INVISIBLE);
        counttt = (TextView)findViewById(R.id.counttt);
        counttt.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.bookyourorder));
        navDrawer();




    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:18002006544")));
        }
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }*/

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
                Intent i=new Intent(BookorderActivity.this,HomeActivity.class);
                startActivity(i);
                break;
            case 1:
                Intent i1=new Intent(BookorderActivity.this,MyAccountActivity.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2=new Intent(BookorderActivity.this,OffercouponActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3=new Intent(BookorderActivity.this,ReferwinActivity.class);
                startActivity(i3);
                break;
            case 4:
                Intent i4=new Intent(BookorderActivity.this,SettingActivity.class);
                startActivity(i4);
                break;

            case 5:
                Intent i5=new Intent(BookorderActivity.this,BookorderActivity.class);
                startActivity(i5);
                break;
            case 6:
                Intent i6=new Intent(BookorderActivity.this,NotificationActivity.class);
                startActivity(i6);
                break;
            case 7:
                Intent i7=new Intent(BookorderActivity.this,FeedbackActivity.class);
                startActivity(i7);
                break;
            case 8:
                Intent i8=new Intent(BookorderActivity.this,LogoutActivity.class);
                startActivity(i8);
                break;

            default:
                break;
        }
    }

    public void makePhoneCall(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone();
        }

    }

    private void callPhone() {
        if (checkPermissionCALL_PHONE(BookorderActivity.this)) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:18002006544"));

            try {
                startActivity(intent);
            }
            catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(BookorderActivity.this, "There are no email applications installed.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:1800888888"));
            try {
                startActivity(callIntent);
            }
            catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(BookorderActivity.this, "There are no email applications installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                }
            }
        }
    }

    public boolean checkPermissionCALL_PHONE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (AppCompatActivity) context,
                        Manifest.permission.CALL_PHONE)) {
                    showDialog("call_phone", context, Manifest.permission.CALL_PHONE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (AppCompatActivity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((AppCompatActivity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
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
