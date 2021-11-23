package com.quanvu201120.goodbook.navigation_main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quanvu201120.goodbook.LoadingActivity;
import com.quanvu201120.goodbook.OrderActivity;
import com.quanvu201120.goodbook.R;
import com.quanvu201120.goodbook.model.Address;
import com.quanvu201120.goodbook.model.CartAdapter;
import com.quanvu201120.goodbook.model.ItemCart;
import com.quanvu201120.goodbook.model.SelectAddressAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    TextView txtTongTien;
    Button btnTiepTuc;
    CartAdapter cartAdapter;
    int TongTien;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Address> addressArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_Cart);
        txtTongTien = view.findViewById(R.id.txtTongTien_Cart);
        btnTiepTuc = view.findViewById(R.id.btnTiepTuc_Cart);

        firebaseFirestore = FirebaseFirestore.getInstance();

        addressArrayList = new ArrayList<>();

        if(LoadingActivity.mUser.getUs_Address() != null){
            getAddress();
        }


        if (LoadingActivity.mUser.getUs_Cart()!=null) {
            TongTien();
            btnTiepTuc.setEnabled(true);
        }
        else{

            txtTongTien.setText("0");
            btnTiepTuc.setEnabled(false);
        }

        enableBtnTiepTuc();


        cartAdapter = new CartAdapter(LoadingActivity.mCart);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);


        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if(LoadingActivity.mUser.getUs_Address() != null ){

                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.select_address);

                        ListView listView = dialog.findViewById(R.id.listview_select_address);

                        SelectAddressAdapter adapter = new SelectAddressAdapter(getContext(),R.id.listview_select_address, addressArrayList);
                        listView.setAdapter(adapter);


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Address addressClick = addressArrayList.get(position);
                                Intent intent = new Intent(getContext(), OrderActivity.class);
                                intent.putExtra("AddressFromCart",addressClick);
                                intent.putExtra("TongTienTxtFromCart",txtTongTien.getText().toString());
                                intent.putExtra("TongTienFromCart",TongTien);
                                startActivity(intent);

                                dialog.dismiss();
                            }
                        });

                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();

                    }
                    else{
                        Toast.makeText(getContext(), "Vui lòng cập nhật thông tin địa chỉ", Toast.LENGTH_SHORT).show();
                    }


            }
        });


        return view;
    }

    private void enableBtnTiepTuc() {
        if (TongTien != 0) {
            btnTiepTuc.setEnabled(true);
        }
        else{
            btnTiepTuc.setEnabled(false);
        }
    }

    void TongTien() {

        TongTien = 0;
        for(ItemCart itemCart: LoadingActivity.mCart){
            TongTien+=Integer.parseInt(itemCart.getGiaSach())*itemCart.getSoLuong();
        }

        txtTongTien.setText( EditPrice(TongTien+"") + " VNĐ");
    }


    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {

        if (item.getItemId() == 00000){
            ItemCart itemCart = LoadingActivity.mCart.get(item.getGroupId());
            String ID_delete = itemCart.getId_Cart();
            LoadingActivity.mUser.getUs_Cart().remove(ID_delete);
            firebaseFirestore.collection("Cart").document(ID_delete).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id())
                                    .update("us_Cart",LoadingActivity.mUser.getUs_Cart())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                                            LoadingActivity.mCart.remove(itemCart);
                                            cartAdapter.notifyDataSetChanged();
                                            TongTien();
                                            enableBtnTiepTuc();
                                        }
                                    });
                        }
                    });




        }

        return super.onContextItemSelected(item);
    }

    String EditPrice(String s){
        StringBuilder tmp = new StringBuilder();
        int a=3, b = 0;
        char x = '.';
        for(int i= s.length()-1; i>=0; i--){
            b++;
            tmp.append(s.charAt(i));
            if (b==a && i!=0){
                tmp.append(x);
                b = 0;
            }
        }

        return tmp.reverse().toString();
    }

    void getAddress(){
        for(String tmp : LoadingActivity.mUser.getUs_Address())
        firebaseFirestore.collection("Address").document(tmp).get()
        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                addressArrayList.add(documentSnapshot.toObject(Address.class));
            }
        });

    }


}