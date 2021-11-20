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

public class ListviewOrderAdapter extends ArrayAdapter<ItemCart> {
    public ListviewOrderAdapter(@NonNull Context context, int resource, @NonNull List<ItemCart> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ItemCart itemCart = getItem(position);
        IViewHolder iViewHolder;

        if(convertView == null){

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_order,parent,false);
            iViewHolder = new IViewHolder();

            iViewHolder.txt_gia = convertView.findViewById(R.id.txtGia_item_order);
            iViewHolder.txt_ten = convertView.findViewById(R.id.txtName_item_order);
            iViewHolder.txt_soluong = convertView.findViewById(R.id.txtSoluong_item_order);

            convertView.setTag(iViewHolder);
        }
        else{
            iViewHolder  = (IViewHolder) convertView.getTag();
        }

        iViewHolder.txt_ten.setText("Tên sách: " + itemCart.getTenSach());
        iViewHolder.txt_soluong.setText("Số lượng: " + itemCart.getSoLuong());
        iViewHolder.txt_gia.setText("Giá bán: " + itemCart.getGiaSach());

        return convertView;
    }

    class IViewHolder{

        TextView txt_ten, txt_gia, txt_soluong;

    }
}
