package com.jingjingke.schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<Schedule> mScheduleList = new ArrayList<Schedule>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 塞入列表数据并渲染页面
        initData();
        HasStatusScheduleAdapter adapter = new HasStatusScheduleAdapter(
                MainActivity.this, R.layout.has_status_schedule_item, mScheduleList
        );
        ListView list = findViewById(R.id.homeList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        // 获取新增入口按钮并跳转
        Button jumpAddButton = findViewById(R.id.jumpAddPage);
        jumpAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        Schedule schedule01 = new Schedule("出门买菜", "10分10秒", "已暂停");
        mScheduleList.add(schedule01);

        Schedule schedule02 = new Schedule("狂街购物", "2小时48分04秒", "已暂停");
        mScheduleList.add(schedule02);

        Schedule schedule03 = new Schedule("写作业", "1小时0分34秒", "已暂停");
        mScheduleList.add(schedule03);

        Schedule schedule04 = new Schedule("吃中饭", "30分12秒", "已暂停");
        mScheduleList.add(schedule04);

        Schedule schedule05 = new Schedule("上兴趣班", "--", "进行中");
        mScheduleList.add(schedule05);

        Schedule schedule06 = new Schedule("溜狗", "--", "未开始");
        mScheduleList.add(schedule06);

        Schedule schedule07 = new Schedule("晚餐", "--", "未开始");
        mScheduleList.add(schedule07);

        Schedule schedule08 = new Schedule("看新闻电视", "--", "未开始");
        mScheduleList.add(schedule08);

        Schedule schedule09 = new Schedule("睡前刷微博", "--", "未开始");
        mScheduleList.add(schedule09);
    }

}
