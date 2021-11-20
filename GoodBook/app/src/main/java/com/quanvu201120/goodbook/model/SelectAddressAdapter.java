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

public class SelectAddressAdapter extends ArrayAdapter<Address> {
    public SelectAddressAdapter(@NonNull Context context, int resource, @NonNull List<Address> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Address address = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview_address,parent,false);

        TextView txtName = convertView.findViewById(R.id.txtHoTen_Item_Address);
        TextView txtPhone = convertView.findViewById(R.id.txtSDT_Item_Address);
        TextView txtAddress = convertView.findViewById(R.id.txtDiaChi_Item_Address);

        txtName.setText(address.getName());
        txtPhone.setText(address.getPhone());
        txtAddress.setText(address.getAddress());

        return convertView;
    }
}
