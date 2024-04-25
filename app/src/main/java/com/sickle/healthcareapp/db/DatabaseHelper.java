package com.sickle.healthcareapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.sickle.healthcareapp.home.HomeItem;
import com.sickle.healthcareapp.home.dashboard.HistoryItem;
import com.sickle.healthcareapp.model.hydrationModel.HydrationModel;
import com.sickle.healthcareapp.model.moodsModel.Mood;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Med-Dose";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Medicines";
    private static final String DB_HYDRATION_TABLE = "Hydration";
    private static final String DB_MOODS_TABLE = "Moods";
    private static final String KEY_ID = "ID";
    private static final String KEY_EMOTION_MOOD = "emo_mood";
    private static final String KEY_EMOTION_LEVEL = "emo_level";
    private static final String KEY_EMOTION_NOTES = "emo_notes";
    private static final String KEY_EMOTION_NOTE_DATE = "emo_date";


    private static final String KEY_HYDRATION_DATE = "hyd_date";
    private static final String KEY_TOTAL_OUNCE_VALUE = "ounce_value";
    private static final String KEY_BOTTLE_OUNCE_VALUE = "bottle_value";
    private static final String KEY_GLASS_OUNCE_VALUE = "glass_value";


    private static final String KEY_NAME = "Name";
    private static final String KEY_DAY = "Day";
    private static final String KEY_MONTH = "Month";
    private static final String KEY_NDC = "NDC";
    private static final String KEY_ACTIVE_ING_NAME = "activeIngName";
    private static final String KEY_ACTIVE_ING_STRENGTH = "activeIngStrength";
    private static final String KEY_YEAR = "Year";
    private static final String KEY_TIMES_PER_DAY = "TimesPerDay";
    private static final String KEY_TOTAL_DOSES = "TotalDoses";
    private static final String KEY_TIMINGS = "Timings";
    private static final String KEY_ALERT_TYPE = "AlertType";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL);", DB_TABLE, KEY_ID, KEY_NAME, KEY_DAY, KEY_MONTH,
                KEY_NDC, KEY_ACTIVE_ING_NAME, KEY_ACTIVE_ING_STRENGTH,
                KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES, KEY_TIMINGS, KEY_ALERT_TYPE);


        String emotionQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL);", DB_MOODS_TABLE, KEY_ID, KEY_EMOTION_MOOD,
                KEY_EMOTION_LEVEL, KEY_EMOTION_NOTES, KEY_EMOTION_NOTE_DATE);

        String hydrationQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL);", DB_HYDRATION_TABLE, KEY_ID, KEY_TOTAL_OUNCE_VALUE,
                KEY_BOTTLE_OUNCE_VALUE, KEY_GLASS_OUNCE_VALUE, KEY_HYDRATION_DATE);

//        KEY_HYDRATION_DATE
//                KEY_OUNCE_VALUE

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(emotionQuery);
        sqLiteDatabase.execSQL(hydrationQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryUpgrade = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        sqLiteDatabase.execSQL(queryUpgrade);
        onCreate(sqLiteDatabase);
    }

    public long insertNewHydration(HydrationModel hydrationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TOTAL_OUNCE_VALUE, hydrationModel.getOunceValue());
        values.put(KEY_BOTTLE_OUNCE_VALUE, hydrationModel.getOunceBottleValue());
        values.put(KEY_GLASS_OUNCE_VALUE, hydrationModel.getOunceGlassValue());
        values.put(KEY_HYDRATION_DATE, hydrationModel.getHydrationDate());

        long check = db.insertWithOnConflict(DB_HYDRATION_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("Hyd DB Helper", hydrationModel.getOunceValue() + " " +
                hydrationModel.getHydrationDate());
        db.close();

        return check;
    }

    public ArrayList<HydrationModel> getAllHydrationList() {
        ArrayList<HydrationModel> hydrationModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_HYDRATION_TABLE, new String[]{KEY_TOTAL_OUNCE_VALUE,
                KEY_BOTTLE_OUNCE_VALUE,
                KEY_GLASS_OUNCE_VALUE, KEY_HYDRATION_DATE}, null, null, null, null, null);
        while (cursor.moveToNext()) {

            HydrationModel hydrationModel = new HydrationModel();
            hydrationModel.setOunceValue(cursor.getInt(0));
            hydrationModel.setOunceBottleValue(cursor.getInt(1));
            hydrationModel.setOunceGlassValue(cursor.getInt(2));
            hydrationModel.setHydrationDate(cursor.getString(3));

            hydrationModels.add(hydrationModel);
        }
        cursor.close();
        db.close();
        return hydrationModels;

    }


    public long insertNewMood(Mood mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMOTION_MOOD, mood.getSelectedMood());
        values.put(KEY_EMOTION_LEVEL, mood.getSelectedMoodLevel());
        values.put(KEY_EMOTION_NOTES, mood.getSelectedMoodNote());
        values.put(KEY_EMOTION_NOTE_DATE, mood.getSelectedMoodNoteDate());

        long check = db.insertWithOnConflict(DB_MOODS_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("Mood DB Helper", mood.getSelectedMood() + " " + mood.getSelectedMoodLevel() + " " +
                mood.getSelectedMoodNote() + " " + mood.getSelectedMoodNoteDate());
        db.close();

        return check;
    }

    public ArrayList<Mood> getMoodsList() {
        ArrayList<Mood> moodsFetchedList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_MOODS_TABLE, new String[]{KEY_EMOTION_MOOD,
                KEY_EMOTION_LEVEL,
                KEY_EMOTION_NOTES, KEY_EMOTION_NOTE_DATE}, null, null, null, null, null);
        while (cursor.moveToNext()) {

            Mood mood = new Mood();
            mood.setSelectedMood(cursor.getString(0));
            mood.setSelectedMoodLevel(cursor.getInt(1));
            mood.setSelectedMoodNote(cursor.getString(2));
            mood.setSelectedMoodNoteDate(cursor.getString(3));

            moodsFetchedList.add(mood);
        }
        cursor.close();
        db.close();
        return moodsFetchedList;

    }


    public void insertNewMedicine(String medicineName, int day, int month, int year,
                                  int noOfTimesPerDay, int totalDoses, String timings,
                                  String alertType, String NDC, String activeIngName, String activeIngStrength) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicineName);
        values.put(KEY_DAY, day);
        values.put(KEY_MONTH, month);

        values.put(KEY_NDC, NDC);
        values.put(KEY_ACTIVE_ING_NAME, activeIngName);
        values.put(KEY_ACTIVE_ING_STRENGTH, activeIngStrength);

        values.put(KEY_YEAR, year);
        values.put(KEY_TIMES_PER_DAY, noOfTimesPerDay);
        values.put(KEY_TOTAL_DOSES, totalDoses);
        values.put(KEY_TIMINGS, timings);
        values.put(KEY_ALERT_TYPE, alertType);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i("Med-Dose DB Helper", medicineName + day + month + year + noOfTimesPerDay + totalDoses + timings + alertType);
        db.close();
    }

    public void deleteMedicine(String medicineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, KEY_NAME + " = ?", new String[]{medicineName});
        db.close();
    }

    public void updateMedicine(String oldNAme, String medicineName, int day, int month, int year, int noOfTimesPerDay, int totalDoses, String timings, String alertType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicineName);
        values.put(KEY_DAY, day);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_TIMES_PER_DAY, noOfTimesPerDay);
        values.put(KEY_TOTAL_DOSES, totalDoses);
        values.put(KEY_TIMINGS, timings);
        values.put(KEY_ALERT_TYPE, alertType);
        Log.i("Timings", medicineName);

        db.update(DB_TABLE, values, KEY_NAME + " = ?", new String[]{oldNAme});

        db.close();
    }

    public List<HomeItem> getMedicineList() {
        List<HomeItem> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME,
                KEY_TIMES_PER_DAY,
                KEY_TOTAL_DOSES, KEY_TIMINGS, KEY_ACTIVE_ING_NAME, KEY_ACTIVE_ING_STRENGTH, KEY_NDC
        }, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HomeItem homeItem = new HomeItem(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),//NDC
                    cursor.getString(5),//Name
                    cursor.getString(6));//Strength
            medicineList.add(homeItem);
        }
        cursor.close();
        db.close();
        return medicineList;
       /* JSONObject json = new JSONObject(stringreadfromsqlite);
        ArrayList items = json.optJSONArray("uniqueArrays");*/
    }

    public int getId(String name) {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_TIMES_PER_DAY}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }

    public List<HistoryItem> getMedicineHistory() {
        List<HistoryItem> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES, KEY_TIMINGS}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(cursor.getInt(3), cursor.getInt(2), cursor.getInt(1));
            SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            String date = format1.format(calendar.getTime());
            HistoryItem historyItem = new HistoryItem(cursor.getString(0), date, cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
            historyList.add(historyItem);
        }
        cursor.close();
        db.close();
        return historyList;
    }

    public List<String> getTimings(String medicineName) {
        List<String> timingList = new ArrayList<>();
        StringBuffer timingsString = new StringBuffer();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_TIMINGS}, KEY_NAME + " = ?", new String[]{medicineName}, null, null, null);
        while (cursor.moveToNext()) {
            timingsString.append(cursor.getString(0));
            Log.i("Timings", timingsString.toString());
        }
        JSONObject json = null;
        try {
            json = new JSONObject(new String(timingsString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray items = json.optJSONArray("timingArrays");
        for (int i = 0; i < items.length(); i++) {
            try {
                timingList.add(items.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return timingList;
    }

    public int noOfDaysLeft(String medicineName, Calendar mNextAlarmDate) {
        int mPerDay = 0, mTotalDodes = 0;
        int day = 0, month = 0, year = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES}, KEY_NAME + " = ?", new String[]{medicineName}, null, null, null);
        while (cursor.moveToNext()) {
            day = cursor.getInt(0);
            month = cursor.getInt(1);
            year = cursor.getInt(2);
            mPerDay = cursor.getInt(3);
            mTotalDodes = cursor.getInt(4);
        }
        int totalDays = mTotalDodes / mPerDay;
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, day);
        startDate.set(Calendar.MONTH, month);
        startDate.set(Calendar.YEAR, year);
        long daysBetween = Math.round((float) (mNextAlarmDate.getTimeInMillis() - startDate.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        int daysLeft = (int) (totalDays - daysBetween);
        return daysLeft;
    }
}
