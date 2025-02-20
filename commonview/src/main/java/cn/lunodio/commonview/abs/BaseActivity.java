package cn.lunodio.commonview.abs;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import cn.lunodio.commonview.util.LanguageUtil;
import cn.lunodio.commonview.util.UiUtil;


public abstract class BaseActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    protected BaseActivity activity = this;
    protected Context context = this;
//    protected Handler uiHandler = new Handler(Looper.getMainLooper());

    //    默认有状态栏，且与状态栏顶变对齐，需要手动设置容器顶部内边距
    protected View v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SkinManager.onActivityCreateSetSkin(this);
        if (UiUtil.isNight(context)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        v = getWindow().getDecorView().getRootView();
        v.getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();
        if (v != null) {
            v.getWindowVisibleDisplayFrame(r);
            int screenHeight = v.getRootView().getHeight();

            int keypadHeight = screenHeight - r.bottom;
            onKeyboard(keypadHeight > screenHeight * 0.15);
        }

    }

    protected void onKeyboard(boolean show) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        final Locale locale = LanguageUtil.getLocale();
        if (null == locale) {
            super.attachBaseContext(newBase);
        } else {
            final Configuration configuration = newBase.getResources().getConfiguration();
            Locale.setDefault(locale);

            configuration.setLocale(locale);
            super.attachBaseContext(newBase.createConfigurationContext(configuration));
        }

    }
}




