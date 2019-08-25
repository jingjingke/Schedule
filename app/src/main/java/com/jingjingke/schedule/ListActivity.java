package com.jingjingke.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class ListActivity extends CommonActivity {

    private NoStatusScheduleAdapter adapter;
    private ListView list;
    private ImageView noData;

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

        // 状态下拉选择
        setStatus();

    }

    private void setStatus() {
        // 获取状态列表数据
        List<String> options = database.getStatusList();

        // 渲染下拉列表
        ArrayAdapter<String> optionAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, options);
        optionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        final Spinner spinner = findViewById(R.id.status_options);
        spinner.setAdapter(optionAdapter);

        // 下拉列表事件触发
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 获取选择状态并更新列表
                String text = spinner.getItemAtPosition(i).toString();
                getList(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
