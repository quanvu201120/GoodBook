package com.quanvu201120.goodbooksellers.fragment_navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.quanvu201120.goodbooksellers.R;
import com.quanvu201120.goodbooksellers.model.ViewPagerOrderAdapter;


public class OrderFragment extends Fragment {

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    ViewPagerOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewpager_order);
        tabLayout = view.findViewById(R.id.tablayout_order);
        adapter = new ViewPagerOrderAdapter(getFragmentManager(),getLifecycle());
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Đơn hàng mới");
                    break;
                case 1:
                    tab.setText("Đã xác nhận");
                    break;
            }
        }).attach();




    }
}