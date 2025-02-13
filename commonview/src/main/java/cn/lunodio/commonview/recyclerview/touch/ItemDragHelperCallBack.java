package cn.lunodio.commonview.recyclerview.touch;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.flexbox.FlexboxLayoutManager;


public class ItemDragHelperCallBack extends ItemTouchHelper.Callback {
    private static final String TAG = ItemDragHelperCallBack.class.getSimpleName();
    private OnItemDragListener onItemDragListener;


    public ItemDragHelperCallBack(OnItemDragListener onItemDragListener) {
        this.onItemDragListener = onItemDragListener;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
//        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//            if (viewHolder instanceof OnDragVHListener) {
//                OnDragVHListener itemViewHolder = (OnDragVHListener) viewHolder;
//                itemViewHolder.onItemSelected();
//            }
//            if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && dragListener != null) {
//                dragListener.dragState(true);
//            }
//        }
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (ItemTouchHelper.ACTION_STATE_DRAG == actionState) {
                onItemDragListener.onItemMoveState(1);
            }
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
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        int dragFlags;
        if (manager instanceof GridLayoutManager ||
                manager instanceof StaggeredGridLayoutManager ||
                manager instanceof FlexboxLayoutManager) {
            //网格布局管理器允许上下左右拖动
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            //其他布局管理器允许上下拖动
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
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        onItemDragListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
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

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        Log.e(TAG, "dy" + dY +
//                "\n height " + recyclerView.getHeight() +
//                "\n bottom " + viewHolder.itemView.getBottom());
        if (onItemDragListener == null) return;
        if (dY >= (recyclerView.getHeight() -
                viewHolder.itemView.getTop() -
                200)) {
            onItemDragListener.onItemRemoveState(1);
            if (up) {
                viewHolder.itemView.setVisibility(View.INVISIBLE);
                onItemDragListener.onItemRemove(viewHolder.getAdapterPosition());
                reset();
                return;
            }
        } else {
            if (viewHolder.itemView.getVisibility() == View.INVISIBLE) {//如果viewHolder不可见，则表示用户放手，重置删除区域状态
                onItemDragListener.onItemMoveState(0);
            }
            onItemDragListener.onItemRemoveState(0);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void reset() {
        onItemDragListener.onItemMoveState(0);
        onItemDragListener.onItemRemoveState(0);
        up = false;
    }

    private boolean up = false;

    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        up = true;
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        reset();
        super.clearView(recyclerView, viewHolder);

    }
}
