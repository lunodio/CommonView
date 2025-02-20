package cn.lunodio.commonview.util;

import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.HashMap;

import cn.lunodio.commonview.R;


public class ClickUtil {
    public static final int DEFAULT = 500;
    //点击防抖
    private static long lastClickTime;

    private ClickUtil() {
    }

    /**
     * 轻量
     *
     * @param min min
     * @return 是否可以点击
     */
    public static boolean canClick(int min) {
        boolean flag = false;
        long curClickTime = TimeUtil.timeMillis();
        if ((curClickTime - lastClickTime) >= min) {
            //当前时间距离上一次点击超出设置的最小点击间隔
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * postDelayed实现
     *
     * @param v   v
     * @param min min
     */
    public static void shakeClick(final View v, int min) {
        v.setClickable(false);
        v.postDelayed(() -> v.setClickable(true), min);
    }

    /**
     * 腾讯用的
     */
    private static final HashMap<Integer, Long> sLastClickTimeMap = new HashMap<>();

    public static boolean isFastClick(int viewId) {
        boolean flag = false;
        long timeMillis = TimeUtil.timeMillis();
        long lastClickTime = getLastClickTime(viewId);
        if ((timeMillis - lastClickTime) < DEFAULT) {
            flag = true;
        }
        sLastClickTimeMap.put(viewId, timeMillis);
        return flag;
    }

    public static void clear() {
        sLastClickTimeMap.clear();
    }

    private static Long getLastClickTime(int viewId) {
        Long lastClickTime = sLastClickTimeMap.get(viewId);
        if (lastClickTime == null) {
            lastClickTime = TimeUtil.timeMillis();
        }
        return lastClickTime;
    }


    /**
     * @param v target widget
     * @return true, invalid click event.
     * @author yckj
     * 用法：
     * if (AntiShakeUtils.isInvalidClick(v)) return;
     * <p>
     * Whether this click event is invalid.
     * @see #isInvalidClick(View, long)
     */
    public static boolean isInvalidClick(@NonNull View v) {
        return isInvalidClick(v, DEFAULT);
    }

    /**
     * Whether this click event is invalid.
     *
     * @param v            target widget
     * @param internalTime the internal time. The unit is millisecond.
     * @return true, invalid click event.
     */
    public static boolean isInvalidClick(@NonNull View v, @IntRange(from = 0) long internalTime) {
        final long timeMillis = TimeUtil.timeMillis();
        final Object o = v.getTag(R.id.last_click_time);
        if (null == o) {
            v.setTag(R.id.last_click_time, timeMillis);
            return false;
        } else {
            final long lastClickTimeStamp = (Long) o;
            boolean isInvalid = (timeMillis - lastClickTimeStamp) < internalTime;
            if (!isInvalid) {
                v.setTag(R.id.last_click_time, timeMillis);
            }
            return isInvalid;
        }
    }
}
