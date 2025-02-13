package cn.lunodio.commonview.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredItemDecoration extends RecyclerView.ItemDecoration {
    int space;

    public StaggeredItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //设置上下间距
//        if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
//            outRect.top = space;
//        }
        outRect.bottom = space;

//        //判断左右列，方法1
//        //设置列间距
//        if (parent.getChildAdapterPosition(view) % 2 == 0) {
//            //左列
//            outRect.right = space / 2;
//        } else {
//            //右列
//            outRect.left = space / 2;
//        }
        outRect.left = space/2;
        outRect.right = space/2;
//        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
//        int spanIndex = layoutParams.getSpanIndex();
//        int position = parent.getChildAdapterPosition(view);
//        outRect.bottom = space;
//        if (position == 0 || position == 1) {
//            outRect.top = space * 2;
//        } else {
//            outRect.top = 0;
//        }
//        if (spanIndex % 2 == 0) {//偶数项
//            outRect.left = space;
//            outRect.right = space / 2;
//        } else {
//            outRect.left = space / 2;
//            outRect.right = space;
//        }
    }
}
