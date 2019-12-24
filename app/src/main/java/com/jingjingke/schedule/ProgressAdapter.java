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

        startTime.setText(getContext().getString(R.string.field_title_time_start) + progress.getStartTime());
        endingTime.setText(getContext().getString(R.string.field_title_time_end) + progress.getEndingTime());
        detailRemark.setText(getContext().getString(R.string.field_title_remark) + progress.getRemark());

        return view;

    }
}
