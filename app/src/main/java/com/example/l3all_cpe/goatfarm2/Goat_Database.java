package com.example.l3all_cpe.goatfarm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by l3all_cpe on 16/8/2559.
 */
public class Goat_Database extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "goatDB";
    // Table Name
    private static final String TABLE_MEMBER = "goat";
    public Goat_Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MEMBER +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Stall INTEGER (100)," +
                " Age INTEGER (100)," +
                " Genetic TEXT (100)," +
                " Sex TEXT (100)," +
                " Date_start TEXT (100));");

        Log.d("CREATE TABLE","Create Table Successfully.");
    }
    // Insert Data
    public long InsertData(String strGoatID, String strStall, String strAge, String strGenetic, String strSex, String strDate_start) {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data

            /**
             *  for API 11 and above
             SQLiteStatement insertCmd;
             String strSQL = "INSERT INTO " + TABLE_Goat
             + "(MemberID,Name,Tel) VALUES (?,?,?)";

             insertCmd = db.compileStatement(strSQL);
             insertCmd.bindString(1, strMemberID);
             insertCmd.bindString(2, strName);
             insertCmd.bindString(3, strTel);
             return insertCmd.executeInsert();
             */

            ContentValues Val = new ContentValues();
            Val.put("ID", strGoatID);
            Val.put("Stall", strStall);
            Val.put("Age", strAge);
            Val.put("Genetic", strGenetic);
            Val.put("Sex", strSex);
            Val.put("Date_start", strDate_start);

            long rows = db.insert(TABLE_MEMBER, null, Val);

            db.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }

    }
    public String[] SelectData(String strGoatID) {
        // TODO Auto-generated method stub

        try {
            String arrData[] = null;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            Cursor cursor = db.query(TABLE_MEMBER, new String[] { "*" },
                    "ID=?",
                    new String[] { String.valueOf(strGoatID) }, null, null, null, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getColumnCount()];
                    /***
                     *  0 = GoatID
                     *  1 = Stall
                     *  2 = Age
                     *  3 = Genetic
                     *  4 = Sex
                     *  5 = Date_start
                     */
                    arrData[0] = cursor.getString(0);
                    arrData[1] = cursor.getString(1);
                    arrData[2] = cursor.getString(2);
                    arrData[3] = cursor.getString(3);
                    arrData[4] = cursor.getString(4);
                    arrData[5] = cursor.getString(5);
                }
            }
            cursor.close();
            db.close();
            return arrData;

        } catch (Exception e) {
            return null;
        }

    }


    // Delete Data
    public long DeleteData(String strGoatID) {
        // TODO Auto-generated method stub

        try {

            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data

            /**
             * for API 11 and above
             SQLiteStatement insertCmd;
             String strSQL = "DELETE FROM " + TABLE_MEMBER
             + " WHERE MemberID = ? ";

             insertCmd = db.compileStatement(strSQL);
             insertCmd.bindString(1, strMemberID);

             return insertCmd.executeUpdateDelete();
             *
             */

            long rows = db.delete(TABLE_MEMBER, "ID=?",
                    new String[] { String.valueOf(strGoatID) });

            db.close();
            return rows; // return rows deleted.

        } catch (Exception e) {
            return -1;
        }

    }
    // Show All Data
    public ArrayList<HashMap<String, String>> SelectAllData() {
        // TODO Auto-generated method stub

        try {

            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT * FROM " + TABLE_MEMBER;
            Cursor cursor = db.rawQuery(strSQL, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        map.put("ID", cursor.getString(0));
                        map.put("Stall", cursor.getString(1));
                        map.put("Age", cursor.getString(2));
                        map.put("Genetic", cursor.getString(3));
                        map.put("Sex", cursor.getString(4));
                        map.put("Date_start", cursor.getString(5));
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;

        } catch (Exception e) {
            return null;
        }

    }

    //Update Data
    public  long UpdateData(String strGoatID, String strStall, String strAge, String strGenetic, String strSex, String strDate_start){
        try {

            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data
            /**
             *  for API 11 and above
             SQLiteStatement insertCmd;
             String strSQL = "UPDATE " + TABLE_MEMBER
             + " SET Name = ? "
             + " , Tel = ? "
             + " WHERE MemberID = ? ";

             insertCmd = db.compileStatement(strSQL);
             insertCmd.bindString(1, strName);
             insertCmd.bindString(2, strTel);
             insertCmd.bindString(3, strMemberID);

             return insertCmd.executeUpdateDelete();
             *
             */
            ContentValues Val = new ContentValues();

            Val.put("Stall", strStall);
            Val.put("Age", strAge);
            Val.put("Genetic", strGenetic);
            Val.put("Sex", strSex);
            Val.put("Date_start", strDate_start);

            long rows = db.update(TABLE_MEMBER, Val, "ID=?",
                    new String[] { String.valueOf(strGoatID) });

            db.close();
            return rows; // return rows updated.

        } catch (Exception e) {
            return -1;
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //พิมพ์ Log เพื่อให้เห็นว่ามีการ Upgrade Database
        Log.w(Goat_Database.class.getName(), "Upgread database version from version" + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        //ลบตาราง member ของเก่าทิ้ง
        db.execSQL("DROP TABLE IF EXISTS member");
        onCreate(db);
    }
}
