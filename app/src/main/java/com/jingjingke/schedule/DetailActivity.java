package com.jingjingke.schedule;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DetailActivity extends Activity {

    SimpleDateFormat simpleDateFormat;

    private ScheduleDatabaseHelper dbHelper;
    SQLiteDatabase database;

    private List<Details> mDetails = new ArrayList<Details>();

    private TextView scheduleTitle;
    private TextView scheduleBuildTime;
    private TextView scheduleContent;

    private String id;

    private Button scheduleStart;
    private Button scheduleContinue;
    private Button scheduleSuspend;
    private Button scheduleComplete;

    private DetailsAdapter detailsAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        // 获取传入的id
        id = getIntent().getStringExtra("id");

        // 设置时区
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 数据库
        dbHelper = new ScheduleDatabaseHelper(this, "Schedule.db", null, 1);
        database = dbHelper.getWritableDatabase();

        // 获取标题与内容
        scheduleTitle = findViewById(R.id.scheduleTitle);
        scheduleBuildTime = findViewById(R.id.scheduleBuildTime);
        scheduleContent = findViewById(R.id.scheduleContent);

        // 获取按钮
        scheduleStart = findViewById(R.id.scheduleStart);
        scheduleContinue = findViewById(R.id.scheduleContinue);
        scheduleSuspend = findViewById(R.id.scheduleSuspend);
        scheduleComplete = findViewById(R.id.scheduleComplete);

        // 按钮事件
        scheduleStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleStart();
            }
        });
        scheduleContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleContinue();
            }
        });
        scheduleSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleSuspend();
            }
        });
        scheduleComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleComplete();
            }
        });

        // 下方列表
        detailsAdapter = new DetailsAdapter(DetailActivity.this, R.layout.schedule_detail_item, mDetails);
        listView = findViewById(R.id.detailList);
        listView.setAdapter(detailsAdapter);

        // 获取上半部日程信息
        getScheduleInfo();
        // 获取下半部细节列表
        getScheduleList();

    }

    // 点击开始
    private void scheduleStart() {
        ContentValues values = new ContentValues();
        values.put("status_id", 2);
        database.update("schedule", values, "id=?", new String[]{id});
        long time = new Date().getTime();
        database.execSQL("insert into remark (schedule_id,start_time,end_time) values (?,?,?);",
                new Object[]{id, time, 0});
        // 更新按钮及下方列表
        setButtonStatus(2);
        getScheduleList();
    }

    // 点击继续
    private void scheduleContinue() {
        ContentValues values = new ContentValues();
        values.put("status_id", 2);
        database.update("schedule", values, "id=?", new String[]{id});
        long time = new Date().getTime();
        database.execSQL("insert into remark (schedule_id,start_time,end_time) values (?,?,?);",
                new Object[]{id, time, 0});
        // 更新按钮及下方列表
        setButtonStatus(2);
        getScheduleList();
    }

    // 点击暂停
    private void scheduleSuspend() {
        ContentValues values = new ContentValues();
        values.put("status_id", 3);
        database.update("schedule", values, "id=?", new String[]{id});
        long time = new Date().getTime();
        // 获取该日程最后一条记录
        Cursor cursor = database.rawQuery("select id from remark where schedule_id=? order by start_time desc limit 1", new String[]{id});
        String rid = "";
        if (cursor.moveToFirst()) {
            rid = Integer.toString(cursor.getInt(0));
            ContentValues values1 = new ContentValues();
            values1.put("end_time", time);
            database.update("remark", values1, "id=?", new String[]{rid});
        }
        // 更新按钮及下方列表
        setButtonStatus(3);
        getScheduleList();
    }

    // 点击完成
    private void scheduleComplete() {
        ContentValues values = new ContentValues();
        values.put("status_id", 4);
        database.update("schedule", values, "id=?", new String[]{id});
        long time = new Date().getTime();
        // 获取该日程最后一条记录
        Cursor cursor = database.rawQuery("select id from remark where schedule_id=? order by start_time desc limit 1", new String[]{id});
        String rid = "";
        if (cursor.moveToFirst()) {
            rid = Integer.toString(cursor.getInt(0));
            ContentValues values1 = new ContentValues();
            values1.put("end_time", time);
            database.update("remark", values1, "id=?", new String[]{rid});
        }
        // 更新按钮及下方列表
        setButtonStatus(4);
        getScheduleList();

    }

    private void setButtonStatus(int status) {
        switch (status) {
            case 1:
                scheduleStart.setVisibility(View.VISIBLE);
                scheduleContinue.setVisibility(View.GONE);
                scheduleSuspend.setVisibility(View.GONE);
                scheduleComplete.setVisibility(View.GONE);
                break;
            case 2:
                scheduleStart.setVisibility(View.GONE);
                scheduleContinue.setVisibility(View.GONE);
                scheduleSuspend.setVisibility(View.VISIBLE);
                scheduleComplete.setVisibility(View.VISIBLE);
                break;
            case 3:
                scheduleStart.setVisibility(View.GONE);
                scheduleContinue.setVisibility(View.VISIBLE);
                scheduleSuspend.setVisibility(View.GONE);
                scheduleComplete.setVisibility(View.GONE);
                break;
            default:
                scheduleStart.setVisibility(View.GONE);
                scheduleContinue.setVisibility(View.GONE);
                scheduleSuspend.setVisibility(View.GONE);
                scheduleComplete.setVisibility(View.GONE);
                break;
        }

    }

    private void getScheduleInfo() {
        // 数据库中查询数据
        Cursor cursor = database.rawQuery("select * from schedule where id=?", new String[]{id});
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                long create_time = cursor.getLong(cursor.getColumnIndex("create_time"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int status = cursor.getInt(cursor.getColumnIndex("status_id"));

                scheduleTitle.setText(name);
                scheduleBuildTime.setText("创建时间：" + simpleDateFormat.format(new Date(create_time)));
                scheduleContent.setText("内容：" + content);
                // 根本状态设置按钮状态
                setButtonStatus(status);
            } while (cursor.moveToNext());
        }
    }

    private void getScheduleList() {
        // 数据库中查询数据
        Cursor cursor = database.rawQuery("select * from remark where schedule_id=? order by start_time desc", new String[]{id});
        mDetails.clear();
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
                mDetails.add(details);
            } while (cursor.moveToNext());
        }

        listView.setAdapter(detailsAdapter);

    }
}