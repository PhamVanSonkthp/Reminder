package com.infinity.reminder.data_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.gson.Gson;
import com.infinity.reminder.model_objects.SensorInfor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DBeBacSens";

    private String[] arrCity, arrDistrict, arrSubDistrict;

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "devices" + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mac_address" + " TEXT, " +
                "name" + " TEXT " +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "setting_offlines" + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "object" + " TEXT " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean isCreatedSettingOffline() {
        int counter = 0;
        String query = "SELECT * FROM setting_offlines";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                counter++;
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return counter > 0;
    }

    public void insertDevice(String macAddress, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mac_address", macAddress);
        values.put("name", name);
        db.insert("devices", null, values);
    }

    public ArrayList<SensorInfor> getDevice() {
        ArrayList<SensorInfor> campaigns = new ArrayList<>();

        String query = "SELECT * FROM devices";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                campaigns.add(new SensorInfor(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                0,
                                0,
                                null,
                                null,
                                null
                        )
                );
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return campaigns;
    }
}
