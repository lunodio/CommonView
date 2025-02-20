package cn.lunodio.commonview.abs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = BaseAdapter.class.getSimpleName();
    protected Context context;
    protected LayoutInflater inflater;

    public BaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public abstract BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull BaseViewHolder holder, int position);

    @Override
    public abstract int getItemViewType(int position);

    @Override
    public abstract int getItemCount();

}
