package com.quanvu201120.goodbook.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quanvu201120.goodbook.R;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<mHistory> {
    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<mHistory> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        mHistory history = getItem(position);
        HistoryHolder holder;

        if (convertView == null){

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_history,parent,false);
            holder = new HistoryHolder();
            holder.txtName = convertView.findViewById(R.id.txt_name_item_order);
            holder.txtDate = convertView.findViewById(R.id.txt_date_item_order);

            convertView.setTag(holder);
        }
        else{
            holder = (HistoryHolder) convertView.getTag();
        }

        holder.txtName.setText("Tên: " + history.getTen());
        holder.txtDate.setText("Thời gian: " + history.getNgayDatHang());

        return convertView;
    }

    class HistoryHolder{
        TextView txtName, txtDate;
    }
}
