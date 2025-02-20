package cn.lunodio.commonview.abs;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected View container;
    protected SparseArray<View> views;


    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.container = itemView;
        this.views = new SparseArray<>();
    }

    /**
     * @param vId vId
     * @param txt txt
     * @return 设置TextView内容
     */
    public BaseViewHolder setText(int vId, String txt) {
        final TextView tv = findViewById(vId);
//        if (!TextUtil.isEmpty(txt)) {
        tv.setText(txt);
//        }
        return this;
    }

    /**
     * @return 根布局
     */
    public View getContainer() {
        return container;
    }

    /**
     * @param <T>    view本身
     * @param viewId view的id
     * @return 得到相信的view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


}
