package com.quanvu201120.goodbooksellers.intro;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerIntroAdapter extends FragmentStateAdapter {
    public ViewPagerIntroAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new BlankFragment1();
                break;
            case 1:
                fragment = new BlankFragment2();
                break;


        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
