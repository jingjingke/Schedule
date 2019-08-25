package com.jingjingke.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HasStatusScheduleAdapter extends ArrayAdapter<Schedule> {

    private int resourceId;

    public HasStatusScheduleAdapter(Context context, int resource, List<Schedule> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule schedule = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView scheduleId = view.findViewById(R.id.unClosedId);
        TextView scheduleStatus = view.findViewById(R.id.unclosedStatus);
        TextView scheduleName = view.findViewById(R.id.unclosedName);
        TextView scheduleTime = view.findViewById(R.id.unclosedTime);

        scheduleId.setText(Integer.toString(schedule.getId()));
        scheduleStatus.setText(schedule.getStatus());
        // 为状态上颜色
        switch (schedule.getSid()) {
            case 2:
                scheduleStatus.setBackgroundResource(R.drawable.radius_green_background);
                break;
            case 3:
                scheduleStatus.setBackgroundResource(R.drawable.radius_yellow_background);
                break;
            case 4:
                scheduleStatus.setBackgroundResource(R.drawable.radius_gray_background);
                break;
            default:
                scheduleStatus.setBackgroundResource(R.drawable.radius_gray_background);
        }
        scheduleName.setText(schedule.getName());
        scheduleTime.setText("用时：" + schedule.getCost());

        return view;
    }
}