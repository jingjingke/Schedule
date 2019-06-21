package com.jingjingke.schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    private List<Schedule> mScheduleList = new ArrayList<Schedule>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        // 状态的下拉列表渲染
        Spinner spinner = findViewById(R.id.status_options);
        // 状态-选项
        List<String> options = new ArrayList<String>();
        options.add("未开始");
        options.add("进行中");
        options.add("已暂停");
        options.add("已完成");
        // 状态-渲染
        ArrayAdapter<String> optionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(optionAdapter);

        // 塞入列表数据并渲染页面
        initData();
        NoStatusScheduleAdapter adapter = new NoStatusScheduleAdapter(
                ListActivity.this, R.layout.no_status_schedule_item, mScheduleList
        );
        ListView list = findViewById(R.id.allList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        Schedule schedule01 = new Schedule("出门买菜", "10分10秒");
        mScheduleList.add(schedule01);

        Schedule schedule02 = new Schedule("狂街购物", "2小时48分04秒");
        mScheduleList.add(schedule02);

        Schedule schedule03 = new Schedule("写作业", "1小时0分34秒");
        mScheduleList.add(schedule03);

        Schedule schedule04 = new Schedule("吃中饭", "30分12秒");
        mScheduleList.add(schedule04);

        Schedule schedule05 = new Schedule("上兴趣班", "--");
        mScheduleList.add(schedule05);

        Schedule schedule06 = new Schedule("溜狗", "--");
        mScheduleList.add(schedule06);

        Schedule schedule07 = new Schedule("晚餐", "--");
        mScheduleList.add(schedule07);

        Schedule schedule08 = new Schedule("看新闻电视", "--");
        mScheduleList.add(schedule08);

        Schedule schedule09 = new Schedule("睡前刷微博", "--");
        mScheduleList.add(schedule09);
    }
}
