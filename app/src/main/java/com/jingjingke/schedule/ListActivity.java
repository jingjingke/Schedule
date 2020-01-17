package com.jingjingke.schedule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends CommonActivity {

    private NoStatusScheduleAdapter adapter;
    private TextView statusButton;
    private ListView list;
    private ImageView noData;
    private int choice = 0;
    private String[] statusArray;

    private ScheduleDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        // 数据库
        database = new ScheduleDatabase(this);

        // 无数据展示
        noData = findViewById(R.id.listNoData);
        // 定义列表事件
        list = findViewById(R.id.allList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                int id = Integer.parseInt(((EditText) view.findViewById(R.id.closedId)).getText().toString());
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // 状态列表及状态切换
        statusArray = database.getStatusList();
        statusButton = findViewById(R.id.statusButton);
        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        // 获取页面默认数据
        setStatus(choice);
    }

    // 创建dialog弹窗
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setSingleChoiceItems(statusArray, choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = which;
                setStatus(choice);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void setStatus(int index) {
        String statusText = statusArray[index];
        statusButton.setText(statusText);
        getList(statusText);
    }

    private void getList(String status) {
        adapter = new NoStatusScheduleAdapter(ListActivity.this, R.layout.no_status_schedule_item, database.getListByStatus(status));
        if (adapter.getCount() == 0) {
            list.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            list.setAdapter(adapter);
            list.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }
}
