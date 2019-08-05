package com.jingjingke.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ScheduleDatabase {

    private ScheduleDatabaseHelper dbHelper;
    SQLiteDatabase database;

    SimpleDateFormat simpleDateFormat;

    public ScheduleDatabase(Context context) {
        dbHelper = new ScheduleDatabaseHelper(context, "Schedule.db", null, 1);
        database = dbHelper.getWritableDatabase();

        // 设置时区
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    // 获取未完成列表
    public List<Schedule> getUnFinishedList() {
        List<Schedule> list = new ArrayList<Schedule>();

        Cursor cursor = database.rawQuery("select s.id,s.name,s.cost_time,i.name as status_name from schedule as s inner join status as i on s.status_id=i.id where s.status_id<4 order by s.create_time desc", null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String cost = cursor.getString(cursor.getColumnIndex("cost_time"));
                String status = cursor.getString(cursor.getColumnIndex("status_name"));
                Schedule schedule = new Schedule(id, name, cost, status);
                list.add(schedule);
            } while (cursor.moveToNext());
        }

        return list;
    }

    // 根据状态获取列表
    public List<Schedule> getListByStatus(String status) {
        List<Schedule> list = new ArrayList<Schedule>();

        Cursor cursor = database.rawQuery("select s.id,s.name,s.cost_time from schedule as s inner join status on s.status_id=status.id where status.name=? order by s.create_time desc", new String[]{status});
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String cost = cursor.getString(cursor.getColumnIndex("cost_time"));
                Schedule schedule = new Schedule(id, name, cost);
                list.add(schedule);
            } while (cursor.moveToNext());
        }

        return list;
    }

    // 获取状态列表
    public List<String> getStatusList() {
        List<String> list = new ArrayList<String>();

        Cursor cursor = database.rawQuery("select name from status", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
        }

        return list;
    }


    // 新增日程后返回当前自增id
    public int addSchedule(String title, String content, String remark, int status) {
        long time = new Date().getTime();
        database.execSQL("insert into schedule (name,content,remark,status_id,create_time,cost_time) values (?,?,?,?,?,?);",
                new Object[]{title, content, remark, status, time, 0});
        Cursor cursor = database.rawQuery("select last_insert_rowid() from schedule", null);
        int sid = 0;
        if (cursor.moveToFirst()) {
            sid = cursor.getInt(0);
        }
        return sid;
    }

    // 查询日程详情
    public Schedule querySchedule(int schedule_id) {
        Schedule schedule = new Schedule();
        Cursor cursor = database.rawQuery("select * from schedule where id=? limit 1", new String[]{String.valueOf(schedule_id)});
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            int status = cursor.getInt(cursor.getColumnIndex("status_id"));
            String cost = cursor.getString(cursor.getColumnIndex("cost_time"));

            long create_time = cursor.getLong(cursor.getColumnIndex("create_time"));
            String create = simpleDateFormat.format(new Date(create_time));

            schedule = new Schedule(id, name, content, remark, cost, status, create);
        }
        return schedule;
    }

    // 查询进度列表
    public List<Details> queryProgress(int schedule_id) {
        List<Details> list = new ArrayList<Details>();
        Cursor cursor = database.rawQuery("select * from progress where schedule_id=? order by start_time desc", new String[]{String.valueOf(schedule_id)});
        if (cursor.moveToFirst()) {
            do {
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                long start_time = cursor.getLong(cursor.getColumnIndex("start_time"));
                long end_time = cursor.getLong(cursor.getColumnIndex("end_time"));

                String start = simpleDateFormat.format(new Date(start_time));
                String end = "";
                if (end_time != 0) {
                    end = simpleDateFormat.format(new Date(end_time));
                }

                Details details = new Details(start, end, remark);
                list.add(details);
            } while (cursor.moveToNext());
        }
        return list;
    }

    // 日程状态变更
    public void changeSchedule(int schedule_id, int status_id) {
        database.execSQL("update schedule set status_id=? where id=?", new Object[]{status_id, schedule_id});
        endProgress(schedule_id);
    }

    public void changeSchedule(int schedule_id, int status_id, String remark) {
        database.execSQL("update schedule set status_id=? where id=?", new Object[]{status_id, schedule_id});
        addProgress(schedule_id, "");
    }


    // 开始一个进度
    public void addProgress(int schedule_id, String remark) {
        long time = new Date().getTime();
        database.execSQL("insert into progress (schedule_id,remark,start_time,end_time) values (?,?,?,?);",
                new Object[]{schedule_id, remark, time, 0});
    }

    // 结束一个进度
    private void endProgress(int schedule_id) {
        Cursor cursor = database.rawQuery("select id from progress where schedule_id=? order by start_time desc limit 1", new String[]{String.valueOf(schedule_id)});
        String rid = "";
        if (cursor.moveToFirst()) {
            rid = Integer.toString(cursor.getInt(0));
            ContentValues values = new ContentValues();
            long time = new Date().getTime();
            values.put("end_time", time);
            database.update("progress", values, "id=?", new String[]{rid});
        }
    }

}
