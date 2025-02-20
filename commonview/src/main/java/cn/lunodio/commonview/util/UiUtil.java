package cn.lunodio.commonview.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.FloatRange;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.text.DecimalFormat;

public class UiUtil {
    public static final String TAG = UiUtil.class.getSimpleName();

    public static Boolean isNight(Context context) {
        if (context != null) {
            return (Boolean) SpUtil.get(context, "isNight", false);
        } else {
            return false;
        }
    }

    public static void setIsNight(Context context, Boolean isNight) {
        if (null == context) return;
        SpUtil.put(context, "isNight", isNight);


    }

    /**
     * 设定透明度按压变化，设置点击事件后生效
     *
     * @param view
     * @param up
     * @param down
     */
    public static void setWater(View view, float up, float down) {
        view.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (v.getAlpha() != down) {
                        v.setAlpha(down);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    v.setAlpha(up);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setAlpha(up);
                    break;
            }
            return false;
        });
    }

    private static float scale;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context context
     * @param sp      sp
     * @return px
     */
    public static int sp2px(Context context, float sp) {
        return (int) ((sp * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    //应用界面可见高度，可能不包含导航和状态栏，看Rom实现
    public static int getAppHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static int getAppWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    //屏幕的高度，包含状态栏，导航栏，看Rom实现
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    /**
     * @param context context
     * @return pixel
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    /**
     * 以下均为状态栏相关
     * <p>
     * 获取状态栏高度
     */
    public static int getStatusHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度
     */
    public static void incPaddingTopStatusHeight(Context context, View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp.height > 0) {
            lp.height += getStatusHeight(context);//增高
        }
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusHeight(context),
                view.getPaddingRight(), view.getPaddingBottom());
    }


    /**
     * 以下均为键盘相关
     * <p>
     * 注：view为EditText时，执行完成光标会自动到末尾
     *
     * @param view 使控件获取焦点
     */
    public static void requestFocus(View view) {
        if (view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
    }

    /**
     * @param view 使控件失去焦点
     */
    public static void loseFocus(View view) {
        if (null == view) return;
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setFocusable(true);
            ((ViewGroup) parent).setFocusableInTouchMode(true);
            ((ViewGroup) parent).requestFocus();
        }
    }


    private static int keyboardHeight = 0;

    /**
     * @param context context
     * @return 获取键盘高度
     */
    public static int getKeyboardHeight(Context context) {
        if (keyboardHeight <= 0) {
            keyboardHeight = SpUtil.getInt(context, "keyboardHeight", getAppHeight(context) / 2);
        }
        return keyboardHeight;
    }

    public static void setKeyboardHeight(Context context, int height) {
        keyboardHeight = height;
        if (keyboardHeight > 0) {
            SpUtil.put(context, "keyboardHeight", keyboardHeight);
        }
    }


    /**
     * @param activity activity
     * @return 判断当前软键盘是否打开
     */
    public static boolean isKeyboardShow(Activity activity) {
        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }


    /**
     * 判断软键盘是否弹出
     */
    public static boolean isKeyboardShow(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }


    /**
     * 显示键盘
     */
    public static void showKeyboard(Context context, View view) {
        if (context == null || view == null) return;
        InputMethodManager service = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        service.showSoftInput(view, 0);
//        LogUtil.i(TAG, "显示键盘");
    }

    /**
     * 打开软键盘
     *
     * @param et      editText
     * @param context context
     */
    public static void showKeyboard(Context context, EditText et) {
//        Log.i(TAG, "openKeyboard");
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Context context, View view) {
        if (context == null || view == null) return;
        InputMethodManager service = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        service.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * 关闭软键盘
     *
     * @param editText 输入框
     * @param context  上下文
     */
    public static void hideKeyboard(Context context, EditText editText) {
        if (null == context || null == editText) return;
        final InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * immersiveStatus
     */
    //<editor_yu-wm-wv-fold desc="沉侵">
    public static void immersiveStatus(Activity activity) {
        immersiveStatus(activity, 0, 0);
    }

    public static void immersiveStatus(Activity activity, int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        immersiveStatus(activity.getWindow(), color, alpha);
    }

    public static void immersiveStatus(Window window, int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(mixtureColor(color, alpha));

        int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
        systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        window.getDecorView().setSystemUiVisibility(systemUiVisibility);
    }

    public static void statusColor(final Activity activity, int color) {
        final Window window = activity.getWindow();
        window.setStatusBarColor(color);
    }

    public static int mixtureColor(int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        int a = (color & 0xff000000) == 0 ? 0xff : color >>> 24;
        return (color & 0x00ffffff) | (((int) (a * alpha)) << 24);
    }

    public static int getAttr(Context context, int resId) {
        // 获取 attr 中定义的颜色属性
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(resId, typedValue, true);
        // 将属性值解析为颜色值
        return typedValue.data;
    }

    public static int getColor(Context context, int resId) {
        return context.getColor(resId);
    }

    public static Drawable getDrawable(Context context, int rId) {
        return AppCompatResources.getDrawable(context, rId);
    }

    /**
     * @param context
     * @param resId
     * @return #ffffff
     */
    public static String getColorStr(Context context, int resId) {
        final int colorValue = context.getColor(resId);
        return String.format("#%06X", 0xFFFFFF & colorValue);
    }

    public static void setStatusBarVisibility(Activity activity, boolean show) {
        if (show) {
            // 显示状态栏
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            // 隐藏状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public static String formatCount(Long count) {
        if (null == count || 0 > count) return "0";
        final DecimalFormat decimalFormat = new DecimalFormat("#.0");
        if (count < 10000) {
            return String.valueOf(count);
        } else if (count < 100000000) {
            return decimalFormat.format(count / 10000.0) + "万";
        } else {
            return decimalFormat.format(count / 100000000.0) + "亿";
        }
    }

    public static void vp2OverScrollModeNever(ViewPager2 vp2) {
        final View child = vp2.getChildAt(0);
        if (child instanceof RecyclerView) {
            child.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    public static boolean isNetworkUrl(final String url) {
        return (!TextUtil.isEmpty(url)) && ((url.startsWith("http://") || url.startsWith("https://")));

    }

    /**
     * @param phoneNumber 86 12345678910
     * @return 123******10
     */
    public static String encryptPhoneNumber(String phoneNumber) {
        if (TextUtil.isEmpty(phoneNumber)) return "";
        int start = phoneNumber.indexOf(' ') + 1;
        if (0 != start) {
            return phoneNumber.substring(0, start) + phoneNumber.substring(start).replaceAll("(\\d{3})\\d{6}(\\d{2})", "$1******$2");
        }
        return phoneNumber.replaceAll("(\\d{3})\\d{6}(\\d{2})", "$1******$2");
    }

    public static void setPadding(View view) {
        setPadding(view, 0, 0, 0, 0);
    }

    /**
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     * 这里设置的padding是包含状态栏的
     */
    public static void setPadding(View view, int left, int top, int right, int bottom) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            final Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + left, systemBars.top + top, systemBars.right + right, systemBars.bottom + bottom);
            return insets;
        });
    }
}
