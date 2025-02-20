package cn.lunodio.commonview.abs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public abstract class BaseFragmentAdapter extends FragmentStateAdapter {
    public BaseFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public BaseFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public BaseFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public abstract Fragment createFragment(int position);

    @Override
    public abstract int getItemCount();

}
