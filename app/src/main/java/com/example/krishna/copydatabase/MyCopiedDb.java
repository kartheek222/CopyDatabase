package com.example.krishna.copydatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by krishna on 22/5/15.
 */
public class MyCopiedDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "mydatabase.sqlite";
    private final String db_path;

    public MyCopiedDb(Context context) {
        super(context, DB_NAME, null, 1);
        db_path = "/data/data/" + context.getPackageName() + "/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getData() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from emp", null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (checkDataBase()) {
            return 1000;
        }
        return count;
    }

    //Check if our database already exists
    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = db_path + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }
}
