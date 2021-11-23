package com.quanvu201120.goodbooksellers.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quanvu201120.goodbooksellers.R;

import java.util.List;

public class ListViewOrderAdapter extends ArrayAdapter<Order> {
    public ListViewOrderAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Order order = getItem(position);
        OrderHolder orderHolder;

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview_order,parent,false);

            orderHolder = new OrderHolder();

            orderHolder.txt_ten = convertView.findViewById(R.id.txt_tensach_order);
            orderHolder.txt_soluong = convertView.findViewById(R.id.txt_soluong_order);
            orderHolder.txt_trangthai = convertView.findViewById(R.id.txt_trangthai_order);

            convertView.setTag(orderHolder);
        }
        else{
            orderHolder = (OrderHolder) convertView.getTag();
        }

        orderHolder.txt_ten.setText("Tên sách: " + order.getTenSach());
        orderHolder.txt_soluong.setText("Số lượng: " + order.getSoLuong());

        String trangthai = order.getTrangThai().equals("0")?"Chưa xác nhận":"Đã xác nhận";

        orderHolder.txt_trangthai.setText("Trạng thái: " + trangthai);

        return convertView;
    }
    class OrderHolder{
        TextView txt_ten, txt_soluong, txt_trangthai;
    }
}
