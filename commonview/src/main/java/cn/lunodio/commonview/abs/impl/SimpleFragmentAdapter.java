package cn.lunodio.commonview.abs.impl;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class SimpleFragmentAdapter extends FragmentStateAdapter {
    private static final String TAG = SimpleFragmentAdapter.class.getSimpleName();
    protected final List<Fragment> fragments;

    public List<Fragment> getFragments() {
        return fragments;
    }

    public SimpleFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    public SimpleFragmentAdapter(@NonNull Fragment fragment, List<Fragment> fragments) {
        super(fragment);
        this.fragments = fragments;
    }

    public SimpleFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
