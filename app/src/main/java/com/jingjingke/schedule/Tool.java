package com.jingjingke.schedule;

public class Tool {
    // 工具--返回用时文本
    public static String getCostText(long time) {
        long day, hour, minute, second;
        time = time / 1000;
        day = time / 86400;
        hour = (time % 86400) / 3600;
        minute = (time % 86400 % 3600) / 60;
        second = (time % 86400 % 3600 % 60);
        // 过滤无用数据
        return (day == 0 ? "" : (day + "天")) +
                (day + hour == 0 ? "" : (hour + "小时")) +
                (day + hour + minute == 0 ? "" : (minute + "分")) +
                (day + hour + minute + second == 0 ? "--" : (second + "秒"));
    }
}
