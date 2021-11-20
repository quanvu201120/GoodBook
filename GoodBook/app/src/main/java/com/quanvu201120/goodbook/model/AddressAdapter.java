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

import java.util.ArrayList;

public class AddressAdapter extends ArrayAdapter<Address> {
    public AddressAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Address> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Address address = getItem(position);
        AddressHolder addressHolder;

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_address,parent,false);

            addressHolder = new AddressHolder();
            addressHolder.txtName = convertView.findViewById(R.id.txtHoTen_Item_Address);
            addressHolder.txtPhone = convertView.findViewById(R.id.txtSDT_Item_Address);
            addressHolder.txtAddress = convertView.findViewById(R.id.txtDiaChi_Item_Address);

            convertView.setTag(addressHolder);
        }
        else {
            addressHolder = (AddressHolder) convertView.getTag();
        }

        addressHolder.txtName.setText(address.getName());
        addressHolder.txtPhone.setText(address.getPhone());
        addressHolder.txtAddress.setText(address.getAddress());

        return convertView;
    }


    class AddressHolder{
        TextView txtName, txtPhone, txtAddress;
    }
}
