package com.quanvu201120.goodbook;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quanvu201120.goodbook.model.ItemCart;
import com.quanvu201120.goodbook.model.ListviewOrderAdapter;
import com.quanvu201120.goodbook.model.Sach_History;
import com.quanvu201120.goodbook.model.mHistory;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    ListviewOrderAdapter adapter;
    TextView txtName, txtAddress, txtPhone, txtDate, txtPrice;
    ArrayList<ItemCart> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listView_history_activity);
        txtName = findViewById(R.id.txt_name_history_activity);
        txtPhone = findViewById(R.id.txt_phone_history_activity);
        txtDate = findViewById(R.id.txt_date_history_activity);
        txtPrice = findViewById(R.id.txt_price_history_activity);
        txtAddress = findViewById(R.id.txt_address_history_activity);

        mHistory mHistory = (mHistory) getIntent().getSerializableExtra("DataFromHistoryFragment");

        arrayList = new ArrayList<>();

        for(Sach_History m : mHistory.getArrSach()){
            ItemCart itemCart = new ItemCart("null","null","null","null",
                    "null",m.getTenSach(),m.getGiaSach(),Integer.parseInt(m.getSoLuong()));
            arrayList.add(itemCart);
        }

        txtName.setText("Tên: " + mHistory.getTen());
        txtDate.setText("Ngày đặt hàng: " + mHistory.getNgayDatHang());
        txtPhone.setText("SĐT: " + mHistory.getSoDienThoai());
        txtAddress.setText("Địa chỉ: " + mHistory.getDiaChi());
        txtPrice.setText("Tổng thanh toán: " + mHistory.getTongTien() + " VNĐ");

        adapter = new ListviewOrderAdapter(this,R.layout.item_listview_order,arrayList);
        listView.setAdapter(adapter);
    }
}