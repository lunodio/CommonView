package cn.lunodio.commonview.recyclerview.touch;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.flexbox.FlexboxLayoutManager;


public class ItemHelperCallBack extends ItemTouchHelper.Callback {
    private static final String TAG = ItemHelperCallBack.class.getSimpleName();
    private OnItemListener onItemListener;


    public ItemHelperCallBack(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (ItemTouchHelper.ACTION_STATE_IDLE == actionState) {
            onItemListener.onStop();
        } else if (ItemTouchHelper.ACTION_STATE_DRAG == actionState) {
            //正在移动
//                onItemListener.onItemMoveState(1);
            onItemListener.onStart();
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 返回可以滑动的方向
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        int dragFlags;
        if (viewHolder.getItemViewType() == 1) {
            //禁止指定type拖动事件
            dragFlags = 0;
        } else if (manager instanceof GridLayoutManager ||
                manager instanceof StaggeredGridLayoutManager ||
                manager instanceof FlexboxLayoutManager) {
            //网格布局管理器允许上下左右拖动
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
        } else {
            //其他布局管理器默认上下拖动
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return makeMovementFlags(dragFlags, 0);
    }

    /**
     * 拖拽到新位置时候的回调方法
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //不同Type之间不允许移动
        Log.e("TTT", "s " + viewHolder.getAdapterPosition() + " " + target.getAdapterPosition());
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        onItemListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 当用户左右滑动的时候执行的方法
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

}
