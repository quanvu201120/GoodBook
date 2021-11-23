package com.quanvu201120.goodbooksellers.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quanvu201120.goodbooksellers.LoadingActivity;
import com.quanvu201120.goodbooksellers.OrderConfirmActivity;
import com.quanvu201120.goodbooksellers.R;
import com.quanvu201120.goodbooksellers.model.ListViewOrderAdapter;
import com.quanvu201120.goodbooksellers.model.Order;

import org.jetbrains.annotations.NotNull;


public class OrderFragment2 extends Fragment {

    ListView listView;
    ListViewOrderAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listview_order2);


        adapter = new ListViewOrderAdapter(getContext(),R.layout.item_listview_order, LoadingActivity.mOrder2);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Order order = LoadingActivity.mOrder2.get(position);
                Intent intent = new Intent(getContext(), OrderConfirmActivity.class);
                intent.putExtra("order_data",order);
                startActivity(intent);

            }
        });


    }
}