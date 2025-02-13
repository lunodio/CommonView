package cn.lunodio.commonview.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * 用在bottomMenu，将跟随Appbar状态移动
 */
public class FollowTopMenuBehavior extends CoordinatorLayout.Behavior<View> {
    public FollowTopMenuBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
//        ULog.e(TAG, "d " + dependency.getY() + " h " + dependency.getHeight() + " sy " + dependency.getScrollY());
        //获取依据的view的高度百分比
        float heightRatio = dependency.getY() / dependency.getHeight();
        child.setTranslationY(-child.getHeight() * heightRatio);
        return true;
    }
}
