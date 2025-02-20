package cn.lunodio.commonview.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static final long origin = 1621353600000L;

    public static final long SECOND_MILLIS = 1000;
    public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;

    public static long currentTimeSecond() {
        return System.currentTimeMillis() / 1000;
    }

    public static long timeMillis() {
        return System.currentTimeMillis();
    }

    public static String formatTimeStampSecond(long timeStampSecond) {
        return getFormatDate(new Date(timeStampSecond * 1000));
    }

    public static String formatTimeStampMillis(long timeStampMillis) {
        return getFormatDate(new Date(timeStampMillis));
    }

    /**
     * 2021-11-17 19:59:17
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return (formatter.format(date));
    }

    public static String getFormatDateYMDHM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return (format.format(date));
    }

    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else {
            return "晚上 " + timeformatter1to12.format(date);
        }

    }

    public static String getTimeShowString(long milliseconds, boolean abbreviate) {
        String dataString;
        String timeStringBy24;

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todayBegin = todayStart.getTime();
        Date yesterdayBegin = new Date(todayBegin.getTime() - 3600 * 24 * 1000);
        Date preYesterday = new Date(yesterdayBegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todayBegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdayBegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preYesterday)) {
            dataString = "前天";
        } else if (isSameWeekDates(currentTime, today)) {
            dataString = getWeekOfDate(currentTime);
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateFormatter.format(currentTime);
        }

        SimpleDateFormat timeFormatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeStringBy24 = timeFormatter24.format(currentTime);

        if (abbreviate) {
            if (!currentTime.before(todayBegin)) {
                return getTodayTimeBucket(currentTime);
            } else {
                return dataString;
            }
        } else {
            return dataString + " " + timeStringBy24;
        }
    }

    /**
     * @param date1 date1
     * @param date2 date2
     * @return 判断两个日期是否在同一周
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        } else if (1 == subYear && Calendar.DECEMBER == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        } else if (-1 == subYear && Calendar.DECEMBER == cal1.get(Calendar.MONTH)) {
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        }
        return false;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static int hms2s(int hour, int minute, int second) {
        return 3600 * hour + 60 * minute + second;
    }

    public static int s2h(int s) {
        return s / 3600;
    }

    public static int s2m(int s) {
        return (s % 3600) / 60;
    }

    public static int s2s(int s) {
        return s % 60;
    }
}
