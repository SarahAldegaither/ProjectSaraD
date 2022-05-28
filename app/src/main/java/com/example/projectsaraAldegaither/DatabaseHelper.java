package com.example.projectsaraAldegaither;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "PRODUCT_NAME";
    public static final String COL3 = "PRODUCT_QUANTITY";
    public static final String COL4 = "PRODUCT_REVIEW";
    //public static final String COL4 = "Unused";

    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL
                ("CREATE TABLE " + TABLE_NAME +
                        " ( " + COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL2 +" TEXT, " + COL3 + " TEXT, " + COL4 +" TEXT )");
        //db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(String productName, String productQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, productName);
        contentValues.put(COL3, productQuantity);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    /* Returns only one result */
    public Cursor structuredQuery(int ID) {
        SQLiteDatabase db = this.getReadableDatabase(); // No need to write
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL1,
                        COL2, COL3}, COL1 + "=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    //TODO: Students need to try to fix this
    public Cursor getSpecificResult(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+"Where ID="+ID,null);
        return data;
    }

    // Return everything inside the dB
    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public void AddProduct(String Name, String quan, String rev)
    {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();

        // insert values that user pass to the method into the table columns
        ContentValues values = new ContentValues();
        values.put(COL2, Name);
        values.put(COL3, quan);
        values.put(COL4, rev);
        db.insert(TABLE_NAME, null, values);
    }

    public void deleteProduct(String Name, String quan, String rev) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name=?", new String[]{Name,quan,rev});
        db.close();
    }

}
