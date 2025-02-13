package cn.lunodio.commonview.recyclerview.snap;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class LStartOrEndPageSnapHelper extends LPageSnapHelper {
    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStartOrEnd(targetView,
                    getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStartOrEnd(targetView,
                    getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return findStartOrEndView(layoutManager, getVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            return findStartOrEndView(layoutManager, getHorizontalHelper(layoutManager));
        }
        return null;
    }

    private View findStartOrEndView(RecyclerView.LayoutManager layoutManager, LOrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        //默认为 childCount==0
        //当childCount ==1 但child高度高于屏幕时不滚
        if (childCount == 0 || childCount == 1) {
            return null;
        }
        View closestChild = null;
        final int center = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        int absCloset = Integer.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedStart(child);
            int absDistance = Math.abs(childStart - center);

            if (absDistance < absCloset) {
                absCloset = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }
}