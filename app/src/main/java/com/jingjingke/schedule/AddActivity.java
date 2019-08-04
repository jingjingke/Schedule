package com.jingjingke.schedule;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddActivity extends Activity {

    private ScheduleDatabaseHelper dbHelper;
    SQLiteDatabase database;

    private EditText addTitle;
    private EditText addContent;
    private EditText addRemark;

    private Button addButton;
    private Button addStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);

        // 数据库
        dbHelper = new ScheduleDatabaseHelper(this, "Schedule.db", null, 1);
        database = dbHelper.getWritableDatabase();

        // 获取输入内容
        addTitle = findViewById(R.id.addTitle);
        addContent = findViewById(R.id.addContent);
        addRemark = findViewById(R.id.addRemark);

        // 获取按钮
        addButton = findViewById(R.id.addSchedule);
        addStartButton = findViewById(R.id.addStartSchedule);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule(1);
            }
        });

        addStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule(2);
            }
        });

    }

    private void addSchedule(int status) {
        String title = addTitle.getText().toString();
        String content = addContent.getText().toString();
        String remark = addRemark.getText().toString();
        if (title.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.empty_add_title, Toast.LENGTH_SHORT).show();
            return;
        }
        addButton.setEnabled(false);
        addStartButton.setEnabled(false);
        long time = new Date().getTime();
        database.execSQL("insert into schedule (name,content,remark,status_id,create_time,cost_time) values (?,?,?,?,?,?);",
                new Object[]{title, content, remark, status, time, 0});
        if (status == 2) {
            Cursor cursor = database.rawQuery("select last_insert_rowid() from schedule", null);
            int sid = 0;
            if (cursor.moveToFirst()) {
                sid = cursor.getInt(0);
                database.execSQL("insert into remark (schedule_id,start_time,end_time) values (?,?,?);",
                        new Object[]{sid, time, 0});
            }
        }
        Toast.makeText(getApplicationContext(), R.string.success_add_tip, Toast.LENGTH_SHORT).show();
        AddActivity.this.finish();

    }
}