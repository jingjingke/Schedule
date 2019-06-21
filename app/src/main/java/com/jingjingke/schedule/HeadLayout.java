package com.jingjingke.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeadLayout extends LinearLayout {
    public HeadLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.head,this);

        // 定义返回按键事件
        Button backButton = findViewById(R.id.headBack);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
            }
        });

        // 获取组件传入的标题文本并赋值
        String textSelf = getResources().getText(attrs.getAttributeResourceValue(null,"title",0)).toString();
        TextView titleText = findViewById(R.id.headTitle);
        titleText.setText(textSelf);

        // 获取左则图标进行赋值并绑定事件
        ImageView image = findViewById(R.id.headLeftIcon);
        image.setImageResource(R.mipmap.closed);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.jingjingke.schedule.ACTION_LIST");
                context.startActivity(intent);
            }
        });

        // 判断如果头部是首页标识的话隐藏返回按钮
        if(attrs.getAttributeResourceValue(null,"title",0) == R.string.main_page_title){
            backButton.setVisibility(View.INVISIBLE);
            image.setVisibility(View.VISIBLE);
        }
    }
}
