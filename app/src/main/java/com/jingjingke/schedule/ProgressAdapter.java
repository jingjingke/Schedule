package com.jingjingke.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProgressAdapter extends ArrayAdapter<Progress> {
    private int resourceId;

    public ProgressAdapter(Context context, int resource, List<Progress> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Progress progress = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView startTime = view.findViewById(R.id.startTime);
        TextView endingTime = view.findViewById(R.id.endingTime);
        TextView detailRemark = view.findViewById(R.id.detailRemark);

        startTime.setText("开始时间：" + progress.getStartTime());
        endingTime.setText("结束时间：" + progress.getEndingTime());
        detailRemark.setText("备注：" + progress.getRemark());

        return view;

    }
}
