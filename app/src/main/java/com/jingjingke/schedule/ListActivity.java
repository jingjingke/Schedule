package com.jingjingke.schedule;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    private List<Schedule> mScheduleList = new ArrayList<Schedule>();
    private NoStatusScheduleAdapter adapter;
    private ListView list;

    private ScheduleDatabaseHelper dbHelper;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        // 数据库
        dbHelper = new ScheduleDatabaseHelper(this, "Schedule.db", null, 1);
        database = dbHelper.getWritableDatabase();

        // 列表渲染及事件
        adapter = new NoStatusScheduleAdapter(
                ListActivity.this, R.layout.no_status_schedule_item, mScheduleList
        );
        list = findViewById(R.id.allList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                String id = ((EditText) view.findViewById(R.id.closedId)).getText().toString();
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // 获取状态
        getStatus();

    }

    private void getStatus() {
        final Spinner spinner = findViewById(R.id.status_options);
        List<String> options = new ArrayList<String>();
        // 数据库中查询
        Cursor cursor = database.rawQuery("select name from status", null);
        if (cursor.moveToFirst()) {
            do {
                options.add(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
        }
        // 渲染
        ArrayAdapter<String> optionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(optionAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getItemAtPosition(i).toString();
                // 根据状态选择相应列表
                getList(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getList(String status) {
        // 数据库中查询数据
        Cursor cursor = database.rawQuery("select s.id,s.name,s.cost_time from schedule as s inner join status on s.status_id=status.id where status.name=? order by s.create_time desc", new String[]{status});
        mScheduleList.clear();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String cost = cursor.getString(cursor.getColumnIndex("cost_time"));

                Schedule schedule = new Schedule(id, name, cost);
                mScheduleList.add(schedule);
            } while (cursor.moveToNext());
        }
        list.setAdapter(adapter);
    }
}
