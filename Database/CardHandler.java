package com.yumtiff.mohitkumar.tiffin.Database;

/**
 * Created by Prozia Server on 6/8/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class CardHandler {

    public static final String DATABASE_NAME = "CART_DATABASE";
    public static final int DATABASE_VERSION = 4;
    Context context;
    SQLiteDatabase sqlDatabase;
    CartDatabase cartDatabase;

    public CardHandler(Context context) {

        cartDatabase = new CartDatabase(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        sqlDatabase = cartDatabase.getWritableDatabase();
    }

    public void executeQuery(String query) {
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }

            sqlDatabase = cartDatabase.getWritableDatabase();
            sqlDatabase.execSQL(query);

        } catch (Exception e) {

            Log.d("databaseerror",""+e);

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
                Log.d("err","table err");

            }
            sqlDatabase = cartDatabase.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);
            Log.d("write","writee");

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
            Log.d("error","table error");

        }
        return c1;

    }

}