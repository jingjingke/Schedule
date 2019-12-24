package com.jingjingke.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoStatusScheduleAdapter extends ArrayAdapter<Schedule> {

    private int resourceId;

    public NoStatusScheduleAdapter(Context context, int resource, List<Schedule> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule schedule = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView scheduleId = view.findViewById(R.id.closedId);
        TextView scheduleName = view.findViewById(R.id.closedName);
        TextView scheduleTime = view.findViewById(R.id.closedTime);

        scheduleId.setText(Integer.toString(schedule.getId()));
        scheduleName.setText(schedule.getName());
        scheduleTime.setText(getContext().getString(R.string.field_title_time_create) + schedule.getCreate());

        return view;
    }
}