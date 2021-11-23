package com.quanvu201120.goodbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quanvu201120.goodbook.model.Address;
import com.quanvu201120.goodbook.model.ItemCart;
import com.quanvu201120.goodbook.model.ListviewOrderAdapter;
import com.quanvu201120.goodbook.model.Order;
import com.quanvu201120.goodbook.model.Sach_History;
import com.quanvu201120.goodbook.model.User;
import com.quanvu201120.goodbook.model.mHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    ListView listView;
    TextView txtName, txtPhone, txtAddress, txtTongTien;
    Button btnDatHang;
    ProgressBar progressBarOrder;
    ListviewOrderAdapter adapter;
    ArrayList<Sach_History> arrSach;
    ArrayList<String> historyArrayList;
    ArrayList<String> orderArrayList;


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
        progressBarOrder = findViewById(R.id.progressBarOrder);

        Address address = (Address) getIntent().getSerializableExtra("AddressFromCart");
        String tongtientxt = getIntent().getStringExtra("TongTienTxtFromCart");
        String tongtien = String.valueOf(getIntent().getIntExtra("TongTienFromCart",1));

        orderArrayList = new ArrayList<>();
        arrSach = new ArrayList<>();

        txtName.setText("Tên: " + address.getName());
        txtPhone.setText("SĐT: " + address.getPhone());
        txtAddress.setText( "Địa chỉ: " + address.getAddress());
        txtTongTien.setText("Tổng tiền: " + tongtientxt);


        adapter = new ListviewOrderAdapter(OrderActivity.this,R.layout.item_listview_order,
                LoadingActivity.mCart);

        listView.setAdapter(adapter);

        historyArrayList = new ArrayList<>();
        if(LoadingActivity.mUser.getUs_History() != null){
            historyArrayList = LoadingActivity.mUser.getUs_History();
        }

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnDatHang.setVisibility(View.INVISIBLE);
                progressBarOrder.setVisibility(View.VISIBLE);

                arrSach.clear();
                for(ItemCart item : LoadingActivity.mCart){
                    arrSach.add(new Sach_History(item.getTenSach(),String.valueOf(item.getSoLuong()),item.getGiaSach()));
                }

                Timestamp timestamp = Timestamp.now();
                Date date_now = new Date(timestamp.toDate().getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                String date = sdf.format( date_now );

                Date date_id = new Date();
                String Id_history = LoadingActivity.mUser.getU_Id() + date_id.getTime();

                mHistory mHistory_new = new mHistory(Id_history,arrSach,tongtien,address.getName(),address.getPhone(),address.getAddress(),date);



                firebaseFirestore.collection("History").document(Id_history).set(mHistory_new)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                historyArrayList.add(Id_history);
                                LoadingActivity.mUser.setUs_History(historyArrayList);
                                LoadingActivity.mHistory.add(mHistory_new);

                                firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id())
                                        .update("us_History",LoadingActivity.mUser.getUs_History());

                                for(ItemCart item : LoadingActivity.mCart){
                                    orderArrayList.clear();

                                    Date date_id_order = new Date();
                                    String id_order = item.getId_Shop() + date_id_order.getTime();
                                    Log.e("abc id",id_order);

                                    Order order_new = new Order(id_order,item.getTenSach(),item.getGiaSach(),item.getSoLuong()+"",
                                            address.getName(),address.getPhone(),address.getAddress(),date,"0");

                                    firebaseFirestore.collection("Order").document(id_order).set(order_new);

                                    firebaseFirestore.collection("User").document(item.getId_Shop()).get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    User shop = documentSnapshot.toObject(User.class);

                                                    if(shop.getUs_History() != null){
                                                        orderArrayList = shop.getUs_History();
                                                    }

                                                    orderArrayList.add(id_order);
                                                    firebaseFirestore.collection("User").document(item.getId_Shop())
                                                            .update("us_Order",orderArrayList);
                                                }
                                            });
                                }

                                for(String s:LoadingActivity.mUser.getUs_Cart()){
                                    firebaseFirestore.collection("Cart").document(s).delete();
                                }

                                LoadingActivity.mUser.getUs_Cart().clear();
                                LoadingActivity.mCart.clear();

                                firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id())
                                        .update("us_Cart",LoadingActivity.mUser.getUs_Cart())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(OrderActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        });




                            }
                        });



            }
        });
    }
}