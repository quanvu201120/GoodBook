package com.quanvu201120.goodbook.navigation_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.quanvu201120.goodbook.HistoryActivity;
import com.quanvu201120.goodbook.LoadingActivity;
import com.quanvu201120.goodbook.R;
import com.quanvu201120.goodbook.model.HistoryAdapter;
import com.quanvu201120.goodbook.model.mHistory;


public class HistoryFragment extends Fragment {

    ListView listView;
    HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listView = view.findViewById(R.id.listview_history);
        historyAdapter = new HistoryAdapter(getContext(),R.layout.item_listview_history, LoadingActivity.mHistory);

        listView.setAdapter(historyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mHistory mHistory = LoadingActivity.mHistory.get(position);
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("DataFromHistoryFragment",mHistory);
                startActivity(intent);
            }
        });

        return view;
    }
}