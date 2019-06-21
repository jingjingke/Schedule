package com.jingjingke.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DetailsAdapter extends ArrayAdapter<Details> {
    private int resourceId;

    public DetailsAdapter(Context context, int resource, List<Details> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Details details = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView startTime = view.findViewById(R.id.startTime);
        TextView endingTime = view.findViewById(R.id.endingTime);
        TextView detailRemark = view.findViewById(R.id.detailRemark);

        startTime.setText("开始时间：" + details.getStartTime());
        endingTime.setText("结束时间：" + details.getEndingTime());
        detailRemark.setText("备注：" + details.getRemark());

        return view;

    }
}
