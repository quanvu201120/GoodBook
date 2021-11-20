package com.quanvu201120.goodbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quanvu201120.goodbook.model.ItemCart;
import com.quanvu201120.goodbook.model.Sach;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ProductAcitivty extends AppCompatActivity {


    ImageView imgAvatar_product, imgTru, imgCong;
    TextView txtTenSach_Product, txtTacGia_Product,txtTheLoai_Product,txtNgonNgu_Product,
            txtGiaSach_Product,txtNhaXuatBan_Product,txtNamXuatBan_Product,txtGioiThieu_Product,
            txtTrangThai_product,txtSoTrang_Product,txtShop_Product,txtSoLuong_Product;
    Button btnTVGH_product;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> cartArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_acitivty);
        //
        txtShop_Product = findViewById(R.id.txtShop_Product);
        txtSoTrang_Product = findViewById(R.id.txtSoTrang_Product);
        imgAvatar_product = findViewById(R.id.imgAvatar_product);
        txtTrangThai_product = findViewById(R.id.txtTrangThai_Product);
        txtTenSach_Product = findViewById(R.id.txtTenSach_Product);
        txtTacGia_Product = findViewById(R.id.txtTacGia_Product);
        txtTheLoai_Product = findViewById(R.id.txtTheLoai_Product);
        txtNgonNgu_Product = findViewById(R.id.txtNgonNgu_Product);
        txtGiaSach_Product = findViewById(R.id.txtGiaSach_Product);
        txtNhaXuatBan_Product = findViewById(R.id.txtNhaXuatBan_Product);
        txtNamXuatBan_Product = findViewById(R.id.txtNamXuatBan_Product);
        txtGioiThieu_Product = findViewById(R.id.txtGioiThieu_Product);
        btnTVGH_product = findViewById(R.id.btnTVGH_product);
        imgTru = findViewById(R.id.imgTru);
        imgCong = findViewById(R.id.imgCong);
        txtSoLuong_Product = findViewById(R.id.txtSoLuong_Product);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        txtSoLuong_Product.setText("1");

        Sach sach = (Sach) getIntent().getSerializableExtra("SendBookToProduct");

        txtTenSach_Product.setText( sach.getTenSach());
        txtTacGia_Product.setText(sach.getTacGia());
        txtTheLoai_Product.setText(sach.getTheLoai());
        txtNgonNgu_Product.setText(sach.getNgonNgu());
        txtGioiThieu_Product.setText(sach.getGioiThieu());
        txtTrangThai_product.setText(sach.getTrangThai());
        txtNamXuatBan_Product.setText(sach.getNamXuatBan());
        txtNhaXuatBan_Product.setText(sach.getNhaXuatBan());
        txtSoTrang_Product.setText(sach.getSoTrang());
        txtGiaSach_Product.setText("Giá: " + EditPrice(sach.getGiaBan()) + "đ");

        cartArrayList = new ArrayList<>();

        try {
            File image_tmp  = File.createTempFile("tmp","png");
            StorageReference storageReference = firebaseStorage.getReference().child(sach.getHinhAnh());
            storageReference.getFile(image_tmp)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(image_tmp.getPath());
                            imgAvatar_product.setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(txtSoLuong_Product.getText().toString().trim());
                if (soluong>1){
                    soluong = soluong  - 1;
                    txtSoLuong_Product.setText(soluong+"");
                }
            }
        });

        imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(txtSoLuong_Product.getText().toString().trim());
                soluong = soluong  + 1;
                txtSoLuong_Product.setText(soluong+"");

            }
        });

        txtShop_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductAcitivty.this,ShopActivity.class);
                intent.putExtra("ID_SHOP",sach.getID_Shop());
                startActivity(intent);
            }
        });



        btnTVGH_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if(MainActivity.checkString(sach.getTrangThai(),"Còn hàng")){
                btnTVGH_product.setEnabled(false);


                boolean checkId =  false;
                ItemCart itemCart = null,itemCart_index=null;

                if (LoadingActivity.mUser.getUs_Cart() != null){

                    //
                    for ( ItemCart tmp : LoadingActivity.mCart){
                        if (MainActivity.checkString(tmp.getId_Sach(),sach.getID())){
                            checkId = true;
                            itemCart = tmp;
                            itemCart_index = tmp;
                            break;
                        }
                    }
                    //

                    cartArrayList = LoadingActivity.mUser.getUs_Cart();
                }



                int SoLuong  = Integer.parseInt(txtSoLuong_Product.getText().toString().trim());

                if (checkId == true){
                    itemCart.setSoLuong(itemCart.getSoLuong() + SoLuong);
                    firebaseFirestore.collection("Cart").document(itemCart.getId_Cart())
                            .update("soLuong",itemCart.getSoLuong())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProductAcitivty.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                    btnTVGH_product.setEnabled(true);
                                }
                            });
                    LoadingActivity.mCart.set(LoadingActivity.mCart.indexOf(itemCart_index),itemCart);

                }

                else{
                    Date date = new Date();
                    String ID_CART = LoadingActivity.mUser.getU_Id() + date.getTime();

                    itemCart = new ItemCart(ID_CART,sach.getID(),sach.getID_Shop(), LoadingActivity.mUser.getU_Id(), sach.getHinhAnh(),sach.getTenSach(), sach.getGiaBan(), SoLuong);

//                Log.e("ABC CART",LoadingActivity.mUser.getUs_Cart() + "");

                    cartArrayList.add(ID_CART);
                    LoadingActivity.mUser.setUs_Cart(cartArrayList);

                    firebaseFirestore.collection("Cart").document(ID_CART).set(itemCart)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id())
                                            .update("us_Cart",LoadingActivity.mUser.getUs_Cart())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    LoadingActivity.Getcart();
                                                    Toast.makeText(ProductAcitivty.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                                    btnTVGH_product.setEnabled(true);
                                                }
                                            });
                                }
                            });



                }
            }
            else{
                Toast.makeText(ProductAcitivty.this, "Hết hàng, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            }




            }
        });

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

}