package com.quanvu201120.goodbooksellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quanvu201120.goodbooksellers.model.Order;

public class OrderConfirmActivity extends AppCompatActivity {

    TextView txtTensach, txtSoluong, txtGiasach, txtTongTien, txtTenKh, txtSdt, txtDiaChi, txtTrangthai;
    Button btnXacNhan;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        firebaseFirestore = FirebaseFirestore.getInstance();
        
        txtTensach = findViewById(R.id.txt_tensach_confirm);
        txtSoluong = findViewById(R.id.txt_soluong_confirm);
        txtGiasach = findViewById(R.id.txt_giasach_confirm);
        txtTongTien = findViewById(R.id.txt_tongtien_confirm);
        txtTenKh = findViewById(R.id.txt_tenkh_confirm);
        txtSdt = findViewById(R.id.txt_sdt_confirm);
        txtDiaChi = findViewById(R.id.txt_diachi_confirm);
        txtTrangthai = findViewById(R.id.txt_trangthai_confirm);
        btnXacNhan = findViewById(R.id.btnXacNhan_confirm);
        progressBar = findViewById(R.id.progressBar_confirm);

        Order order = (Order) getIntent().getSerializableExtra("order_data");
        int position = getIntent().getIntExtra("position",-1);

        if (!order.getTrangThai().equals("0")){
            btnXacNhan.setVisibility(View.INVISIBLE);
        }

        txtTensach.setText("Tên sách: " + order.getTenSach());
        txtSoluong.setText("Số lượng: " + order.getSoLuong());
        txtGiasach.setText("Giá sách: " + order.getGiaSach() + " VNĐ");
        txtTongTien.setText("Tổng tiền: " + Integer.parseInt(order.getGiaSach()) * Integer.parseInt(order.getSoLuong()) + " VNĐ");
        txtTenKh.setText("Tên khách hàng: " + order.getTenKH());
        txtSdt.setText("Số điện thoại: " + order.getSoDienThoai());
        txtDiaChi.setText("Địa chỉ: " + order.getDiaChi());

        String trangthai = order.getTrangThai().equals("0")?"Chưa xác nhận":"Đã xác nhận";
        txtTrangthai.setText("Trạng thái: " + trangthai);

        
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnXacNhan.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                LoadingActivity.mOrder1.remove(position);

                order.setTrangThai("1");
                LoadingActivity.mOrder2.add(order);


                firebaseFirestore.collection("Order").document(order.getId()).update("trangThai","1")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(OrderConfirmActivity.this, "Đơn hàng đã được xác nhận", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("OK",1);
                                setResult(2000,intent);
                                finish();

                            }
                        });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}