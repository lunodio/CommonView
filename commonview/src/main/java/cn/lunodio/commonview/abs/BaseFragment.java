package cn.lunodio.commonview.abs;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {
    protected View v;
    protected Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentView() != 0) {
            return inflater.inflate(getContentView(), container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract int getContentView();

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        this.v = v;
        this.v.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    protected final <T extends View> T findViewById(@IdRes int id) {
        return v.findViewById(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    protected int keypadHeight = 0;

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();
        if (v != null) {
            v.getWindowVisibleDisplayFrame(r);
            int screenHeight = v.getRootView().getHeight();
            keypadHeight = screenHeight - r.bottom;
            onKeyboard(keypadHeight > screenHeight * 0.15);
        }
    }

    protected void onKeyboard(boolean show) {

    }

}
