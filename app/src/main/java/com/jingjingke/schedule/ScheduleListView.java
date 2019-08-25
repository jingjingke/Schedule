package com.jingjingke.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ScheduleListView extends ListView {


    public ScheduleListView(Context context) {
        super(context);
    }

    public ScheduleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScheduleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mMeasureSpec);
    }


}
