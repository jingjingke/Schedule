package com.jingjingke.schedule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DetailActivity extends Activity {

    private int id;

    private ScheduleDatabase database;

    private DetailsAdapter detailsAdapter;
    private ListView listView;

    private TextView scheduleTitle;
    private TextView scheduleBuildTime;
    private TextView scheduleContent;
    private Button scheduleStart;
    private Button scheduleContinue;
    private Button scheduleSuspend;
    private Button scheduleComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        // 获取传入的id
        id = getIntent().getIntExtra("id", 0);

        // 数据库
        database = new ScheduleDatabase(this);

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
        // 开始
        scheduleStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.changeSchedule(id, 2, "");
                setButtonStatus(2);
                getProgressList();
            }
        });
        // 继续
        scheduleContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.changeSchedule(id, 2, "");
                setButtonStatus(2);
                getProgressList();
            }
        });
        // 暂停
        scheduleSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.changeSchedule(id, 3);
                setButtonStatus(3);
                getProgressList();
            }
        });
        // 完成
        scheduleComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.changeSchedule(id, 4);
                setButtonStatus(4);
                getProgressList();
            }
        });

        // 进度列表列表
        listView = findViewById(R.id.detailList);

        // 获取上半部日程信息
        getScheduleInfo();
        // 获取下半部细节列表
        getProgressList();

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
        // 获取数据并赋值
        Schedule schedule = database.querySchedule(id);
        scheduleTitle.setText(schedule.getName());
        scheduleBuildTime.setText("创建时间：" + schedule.getCreate_time());
        scheduleContent.setText("内容：" + schedule.getContent());
        // 根本状态设置按钮状态
        setButtonStatus(schedule.getStatus());
    }

    private void getProgressList() {
        // 获取数据并赋值
        detailsAdapter = new DetailsAdapter(DetailActivity.this, R.layout.schedule_detail_item, database.queryProgress(id));
        listView.setAdapter(detailsAdapter);
    }
}