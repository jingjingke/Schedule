package com.jingjingke.schedule;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<Schedule> mScheduleList = new ArrayList<Schedule>();
    private HasStatusScheduleAdapter adapter;
    private ListView list;

    private ScheduleDatabaseHelper dbHelper;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 数据库
        dbHelper = new ScheduleDatabaseHelper(this, "Schedule.db", null, 1);
        database = dbHelper.getReadableDatabase();

        // 列表渲染及事件
        adapter = new HasStatusScheduleAdapter(
                MainActivity.this, R.layout.has_status_schedule_item, mScheduleList
        );
        list = findViewById(R.id.homeList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String id = ((EditText) view.findViewById(R.id.unClosedId)).getText().toString();
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // 新增入口
        Button jumpAddButton = findViewById(R.id.jumpAddPage);
        jumpAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 获取列表数据
        getList();
    }

    private void getList() {
        // 数据库中查询数据
        Cursor cursor = database.rawQuery("select s.id,s.name,s.cost_time,i.name as status_name from schedule as s inner join status as i on s.status_id=i.id where s.status_id<4 order by s.create_time desc", null);
        mScheduleList.clear();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String cost = cursor.getString(cursor.getColumnIndex("cost_time"));
                String status = cursor.getString(cursor.getColumnIndex("status_name"));
                Schedule schedule = new Schedule(id, name, cost, status);
                mScheduleList.add(schedule);
            } while (cursor.moveToNext());
        }
        list.setAdapter(adapter);
    }

}
