package com.quanvu201120.goodbooksellers.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.quanvu201120.goodbooksellers.Fragment.OrderFragment1;
import com.quanvu201120.goodbooksellers.Fragment.OrderFragment2;

import org.jetbrains.annotations.NotNull;

public class ViewPagerOrderAdapter extends FragmentStateAdapter {
    public ViewPagerOrderAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new OrderFragment1();
                break;
            case 1:
                fragment = new OrderFragment2();
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
