package com.jingjingke.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_SCHEDULE = "create table schedule(" +
            "id integer primary key autoincrement," +
            "name text," +
            "content text," +
            "remark text," +
            "status_id integer," +
            "create_time integer," +
            "cost_time integer)";

    public static final String CREATE_REMARK = "create table remark(" +
            "id integer primary key autoincrement," +
            "schedule_id integer," +
            "remark text," +
            "start_time integer," +
            "end_time integer)";

    public static final String CREATE_STATUS = "create table status(" +
            "id integer primary key," +
            "name text)";

    private Context mContext;

    public ScheduleDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SCHEDULE);
        sqLiteDatabase.execSQL(CREATE_REMARK);
        // 初始化中就添加状态表
        sqLiteDatabase.execSQL(CREATE_STATUS);
        ContentValues values = new ContentValues();
        values.put("name", R.string.status_text1);
        values.put("id", 1);
        sqLiteDatabase.insert("status", null, values);
        values.clear();
        values.put("name", R.string.status_text2);
        values.put("id", 2);
        sqLiteDatabase.insert("status", null, values);
        values.clear();
        values.put("name", R.string.status_text3);
        values.put("id", 3);
        sqLiteDatabase.insert("status", null, values);
        values.clear();
        values.put("name", R.string.status_text4);
        values.put("id", 4);
        sqLiteDatabase.insert("status", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
