package com.jingjingke.schedule;

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

        Cursor cursor = database.rawQuery("select s.id,s.name,s.content,s.remark,s.create_time,s.cost_time,i.name as status,i.id as sid " +
                "from schedule as s inner join status as i on s.status_id=i.id where s.status_id<4 order by s.create_time desc", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(extractionOfSchedule(cursor));
            } while (cursor.moveToNext());
        }

        return list;
    }

    // 根据状态获取列表
    public List<Schedule> getListByStatus(String status_name) {
        List<Schedule> list = new ArrayList<Schedule>();

        Cursor cursor = database.rawQuery("select s.id,s.name,s.content,s.remark,s.create_time,s.cost_time,i.name as status,i.id as sid " +
                "from schedule as s inner join status as i on s.status_id=i.id where i.name=? order by s.create_time desc", new String[]{status_name});
        if (cursor.moveToFirst()) {
            do {
                list.add(extractionOfSchedule(cursor));
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
        Cursor cursor = database.rawQuery("select s.id,s.name,s.content,s.remark,s.create_time,s.cost_time,i.name as status,i.id as sid " +
                "from schedule as s inner join status as i on s.status_id=i.id where s.id=? limit 1", new String[]{String.valueOf(schedule_id)});
        if (cursor.moveToFirst()) {
            schedule = extractionOfSchedule(cursor);
        }
        return schedule;
    }

    // 查询进度列表
    public List<Progress> queryProgress(int schedule_id) {
        List<Progress> list = new ArrayList<Progress>();
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

                Progress progress = new Progress(start, end, remark);
                list.add(progress);
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
    private void addProgress(int schedule_id, String remark) {
        long time = new Date().getTime();
        database.execSQL("insert into progress (schedule_id,remark,start_time,end_time) values (?,?,?,?);",
                new Object[]{schedule_id, remark, time, 0});
    }

    // 结束一个进度
    private void endProgress(int schedule_id) {
        Cursor cursor = database.rawQuery("select s.cost_time,p.id,p.start_time from progress as p inner join schedule as s on s.id=p.schedule_id where schedule_id=? order by start_time desc limit 1", new String[]{String.valueOf(schedule_id)});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            long start = cursor.getLong(cursor.getColumnIndex("start_time"));
            long cost_time = cursor.getLong(cursor.getColumnIndex("cost_time"));
            long time = new Date().getTime();
            cost_time = cost_time + (time - start);
            database.execSQL("update progress set end_time=? where id=?", new Object[]{time, id});
            database.execSQL("update schedule set cost_time=? where id=?", new Object[]{cost_time, schedule_id});
        }
    }


    // 根本日程Id取得最后一条开始时间与当前时间差
    private long getLastCostTime(int schedule_id) {
        long cost = 0;
        Cursor cursor = database.rawQuery("select start_time from progress where schedule_id=? order by start_time desc limit 1", new String[]{String.valueOf(schedule_id)});
        if (cursor.moveToFirst()) {
            long start_time = cursor.getLong(cursor.getColumnIndex("start_time"));
            long time = new Date().getTime();
            cost = time - start_time;
        }
        return cost;
    }

    // 提取每一条查询得出的日程数据
    private Schedule extractionOfSchedule(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String remark = cursor.getString(cursor.getColumnIndex("remark"));
        String status = cursor.getString(cursor.getColumnIndex("status"));
        int sid = cursor.getInt(cursor.getColumnIndex("sid"));

        long cost_time = cursor.getLong(cursor.getColumnIndex("cost_time"));
        if (sid == 2) {
            cost_time += getLastCostTime(id);
        }
        String cost = getCostText(cost_time);

        long create_time = cursor.getLong(cursor.getColumnIndex("create_time"));
        String create = simpleDateFormat.format(new Date(create_time));

        Schedule schedule = new Schedule(id, name, content, remark, status, sid, create, cost);
        return schedule;
    }

    // 工具--返回用时文本
    private String getCostText(long time) {
        long day, hour, minute, second;
        time = time / 1000;
        day = time / 86400;
        hour = (time % 86400) / 3600;
        minute = (time % 86400 % 3600) / 60;
        second = (time % 86400 % 3600 % 60);
        // 过滤无用数据
        return (day == 0 ? "" : (day + "天")) +
                (day + hour == 0 ? "" : (hour + "小时")) +
                (day + hour + minute == 0 ? "" : (minute + "分")) +
                (day + hour + minute + second == 0 ? "--" : (second + "秒"));
    }

}