package com.quanvu201120.goodbooksellers.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quanvu201120.goodbooksellers.LoadingActivity;
import com.quanvu201120.goodbooksellers.OrderConfirmActivity;
import com.quanvu201120.goodbooksellers.R;
import com.quanvu201120.goodbooksellers.model.ListViewOrderAdapter;
import com.quanvu201120.goodbooksellers.model.Order;


public class OrderFragment1 extends Fragment {

    ListView listView;
    ListViewOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order1, container, false);

        listView = view.findViewById(R.id.listview_order1);


        adapter = new ListViewOrderAdapter(getContext(),R.layout.item_listview_order, LoadingActivity.mOrder1);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Order order = LoadingActivity.mOrder1.get(position);
                Intent intent = new Intent(getContext(), OrderConfirmActivity.class);
                intent.putExtra("order_data",order);
                intent.putExtra("position",position);
                startActivityForResult(intent,1000);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1000 && resultCode == 2000){
                int kq =  data.getIntExtra("OK",0);
                if (kq == 1){

                    adapter.notifyDataSetChanged();

                }
            }

    }
}