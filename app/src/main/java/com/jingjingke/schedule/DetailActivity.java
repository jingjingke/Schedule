package com.jingjingke.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DetailActivity extends CommonActivity {

    private int id;

    private ScheduleDatabase database;

    private ProgressAdapter mProgressAdapter;
    private ListView listView;

    private TextView scheduleTitle;
    private TextView scheduleBuildTime;
    private TextView scheduleContent;
    private TextView scheduleRemark;
    private ImageView scheduEdit;
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
        scheduleRemark = findViewById(R.id.scheduleRemark);

        // 编辑按钮
        scheduEdit = findViewById(R.id.scheduleEdit);

        // 获取按钮
        scheduleStart = findViewById(R.id.scheduleStart);
        scheduleContinue = findViewById(R.id.scheduleContinue);
        scheduleSuspend = findViewById(R.id.scheduleSuspend);
        scheduleComplete = findViewById(R.id.scheduleComplete);

        // 按钮事件
        // 编辑
        scheduEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
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
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        scheduleBuildTime.setText("创建时间：" + schedule.getCreate());
        scheduleContent.setText("内容：" + schedule.getContent());
        scheduleRemark.setText("备注：" + schedule.getRemark());
        // 根本状态设置按钮状态
        setButtonStatus(schedule.getSid());
    }

    private void getProgressList() {
        // 获取数据并赋值
        mProgressAdapter = new ProgressAdapter(DetailActivity.this, R.layout.schedule_detail_item, database.queryProgress(id));
        listView.setAdapter(mProgressAdapter);
    }
}