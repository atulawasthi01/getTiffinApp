package com.yumtiff.mohitkumar.tiffin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Prozia Server on 6/7/2016.
 */
public class CartDatabase extends SQLiteOpenHelper {
    String DB_PATH;
    private final static String DB_NAME = "db_cart";
    public final static int DB_VERSION = 1;
    public SQLiteDatabase cartDb;
    Context context;
    private final String CART_TABLE="food_table";

    private static final String KEY_ID="id";
    private static final String KEY_DID="dishid";
    private static final String KEY_DNAME="dishname";
    private static final String KEY_DPRICE="dishprice";
    private static final String KEY_DQUANTITY="dishqunt";
    private static final String KEY_DIMG="img";
    private static final String KEY_DCODE="code";
    private static final String KEY_DCATID="catid";
    private static final String KEY_DDISCRIPTION="discrip";
    private static final String KEY_DMENUID="menu";

    //dishId,dishimage,dishcode,dishname,discatId,dishdescription,price,menuId
    /*private String CREATE_CART="create table "
            + CART_TABLE + " (" + KEY_ID
            + " integer primary key autoincrement, " + KEY_PNAME
            + " text not null, " + KEY_PRICE
            + " text not null, " + KEY_QUANTITY
            + " text not null);";*/

    private String CREATE_CART="create table "
            +CART_TABLE + "("  + KEY_DID
            + " text not null, " + KEY_DNAME
            + " text not null, " + KEY_DPRICE
            + " text not null, " + KEY_DQUANTITY
            + " text not null, " + KEY_DIMG
            + " text not null, " + KEY_DCODE
            + " text not null, " + KEY_DCATID
            + " text not null, " + KEY_DDISCRIPTION
            + " text not null, " + KEY_DMENUID
            + " text not null);";




    public CartDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public CartDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
        onCreate(db);
    }
    public void insertData(String pname, String price) {
        cartDb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        cartDb.insert(CART_TABLE, null, values);
        cartDb.close();
    }

  /*  public ProductData getdata(String id) {
        cartDb=this.getReadableDatabase();
        Cursor cursor = cartDb.query(CART_TABLE, new String[] { KEY_ID,
                        KEY_PNAME, KEY_PRICE }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor!=null)
            cursor.moveToFirst();
            ProductData productData = new ProductData(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2));

        return productData;
    }

    public List<ProductData> getAllData() {
        List<ProductData> pLst=new ArrayList<ProductData>();
        String selectquery="SELECT * FROM "+CART_TABLE;
        cartDb=this.getWritableDatabase();
        Cursor cursor=cartDb.rawQuery(selectquery,null);
        if (cursor.moveToFirst()) {
            do {
                ProductData pdata=new ProductData();
                pdata.setPid(cursor.getString(0));
                pdata.setPname(cursor.getString(1));
                pdata.setPprice(cursor.getString(2));
                pLst.add(pdata);
            } while (cursor.moveToNext());
        }
        return pLst;
    }*/

   /* public void deleteproduct() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CART_TABLE, KEY_PNAME + " = ?",
                new String[] { KEY_PNAME });
        db.close();
    }*/


    public void deleteData(int id){
        // ask the database manager to delete the row of given id
        try {
            cartDb.delete(CART_TABLE, KEY_ID + "=" + id, null);}
        catch (Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }
   /* public int updatedata(ProductData pdata) {
        cartDb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_PID,pdata.getPid());
        values.put(KEY_PNAME,pdata.getPname());
        values.put(KEY_PRICE,pdata.getPprice());

        return cartDb.update(CART_TABLE, values, KEY_PID + " = ?",
                new String[] { pdata.getPid() });
    }

    public void deleteContact(ProductData pdata) {
        cartDb= this.getWritableDatabase();
        cartDb.delete(CART_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(pdata.getPid()) });
        cartDb.close();
    }

    public boolean isDataExist(String id){
        boolean exist = false;

        Cursor cursor = null;

        try{
            cursor = cartDb.query(
                    CART_TABLE,
                    new String[]{KEY_PID},
                    KEY_PID +"="+id,
                    null, null, null, null);
            if(cursor.getCount() > 0){
                exist = true;
            }

            cursor.close();
        }catch (SQLException e){
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        return exist;
    }

    public Cursor getCart(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM db_cart WHERE id=?";

        return db.rawQuery(sql, new String[] {String.valueOf(id)} );
    }

    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT * FROM "+CART_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }*/
}
