package com.quanvu201120.goodbook;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.quanvu201120.goodbook.model.Address;
import com.quanvu201120.goodbook.model.ListviewOrderAdapter;

public class OrderActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    ListView listView;
    TextView txtName, txtPhone, txtAddress, txtTongTien;
    Button btnDatHang;
    ListviewOrderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseFirestore = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listView_order);
        txtName = findViewById(R.id.txt_name_order);
        txtPhone = findViewById(R.id.txt_phone_order);
        txtAddress = findViewById(R.id.txt_address_order);
        txtTongTien = findViewById(R.id.txt_tongtien_order);
        btnDatHang = findViewById(R.id.btn_DatHang_order);

        Address address = (Address) getIntent().getSerializableExtra("AddressFromCart");
        String tongtien = getIntent().getStringExtra("TongTienFromCart");



        txtName.setText("Tên: " + address.getName());
        txtPhone.setText("SĐT: " + address.getPhone());
        txtAddress.setText( "Địa chỉ: " + address.getAddress());
        txtTongTien.setText("Tổng tiền: " + tongtien);


        adapter = new ListviewOrderAdapter(OrderActivity.this,R.layout.item_listview_order,
                LoadingActivity.mCart);

        listView.setAdapter(adapter);
    }
}