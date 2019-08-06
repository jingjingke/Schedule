package com.jingjingke.schedule;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends CommonActivity {

    private ScheduleDatabase database;

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
        database = new ScheduleDatabase(this);

        // 获取输入内容
        addTitle = findViewById(R.id.addTitle);
        addContent = findViewById(R.id.addContent);
        addRemark = findViewById(R.id.addRemark);

        // 获取按钮并定义事件
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

        // 向数据库中新增
        int sid = database.addSchedule(title, content, remark, 1);
        // 若创建并开始则将新增进度表
        if (status == 2) {
            database.changeSchedule(sid,2,"");
        }
        Toast.makeText(getApplicationContext(), R.string.success_add_tip, Toast.LENGTH_SHORT).show();
        AddActivity.this.finish();

    }
}