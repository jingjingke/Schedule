package com.jingjingke.schedule;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends Activity {

    private List<Details> mDetails = new ArrayList<Details>();

    private TextView scheduleTitle;
    private TextView scheduleBuildTime;
    private TextView scheduleContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        // 获取标题与内容
        scheduleTitle = findViewById(R.id.scheduleTitle);
        scheduleBuildTime = findViewById(R.id.scheduleBuildTime);
        scheduleContent = findViewById(R.id.scheduleContent);
        // 标题与内容赋值
        scheduleTitle.setText("测试日程标题");
        scheduleBuildTime.setText("创建时间：" + "2019/10/10 14:10:22");
        scheduleContent.setText("内容：" + "随便写点什么东西吧");

        // 塞入列表数据并渲染页面
        initData();

        DetailsAdapter detailsAdapter = new DetailsAdapter(DetailActivity.this, R.layout.schedule_detail_item, mDetails);
        ListView listView = findViewById(R.id.detailList);
        listView.setAdapter(detailsAdapter);

    }

    private void initData() {
        Details details01 = new Details("2019/10/10 11:03:00", "2019/10/10 12:30:00", "明明天黑");
        mDetails.add(details01);

        Details details02 = new Details("2019/10/10 13:02:00", "2019/10/10 14:00:00", "不干啥");
        mDetails.add(details02);

        Details details03 = new Details("2019/10/10 16:10:00", "2019/10/10 18:00:00", "唉");
        mDetails.add(details03);

    }
}