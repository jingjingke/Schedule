package com.jingjingke.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends CommonActivity {

    private HasStatusScheduleAdapter adapter;
    private ListView list;

    private ScheduleDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 数据库
        database = new ScheduleDatabase(this);

        // 定义列表事件
        list = findViewById(R.id.homeList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                int id = Integer.parseInt(((EditText) view.findViewById(R.id.unClosedId)).getText().toString());
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
        adapter = new HasStatusScheduleAdapter(MainActivity.this, R.layout.has_status_schedule_item, database.getUnFinishedList());
        list.setAdapter(adapter);
    }

}
