package com.quanvu201120.goodbooksellers;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quanvu201120.goodbooksellers.model.ItemCart;
import com.quanvu201120.goodbooksellers.model.Sach;
import com.quanvu201120.goodbooksellers.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    EditText edtTenSach_Update, edtTacgia_Update,edtNgonNgu_Update,edtNhaXuatBan_Update,edtNamXuatBan_Update,edtGiaBan_Update,edtSoTrang_Update,edtGioiThieu_Update;
    ImageView imgAvatar_Update;
    RadioGroup radioGroup_Update;
    Button btnUpdate_Update,btnCancel_Update,btnDelete_Update;
    Spinner spinner;
    ProgressBar progressBar_UpdateBook;
    RadioButton rbConHang,rbHetHang;
    String TheLoai;
    ArrayList<String> theLoaiArr;
    ArrayAdapter arrayAdapter;


    Uri URI_IMAGE;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        edtTenSach_Update = findViewById(R.id.edtTenSach_Update);
        edtTacgia_Update = findViewById(R.id.edtTacgia_Update);
        spinner = findViewById(R.id.spinner_TheLoai_Update);
        edtNgonNgu_Update = findViewById(R.id.edtNgonNgu_Update);
        edtNhaXuatBan_Update = findViewById(R.id.edtNhaXuatBan_Update);
        edtNamXuatBan_Update = findViewById(R.id.edtNamXuatBan_Update);
        edtGiaBan_Update = findViewById(R.id.edtGiaBan_Update);
        edtSoTrang_Update = findViewById(R.id.edtSoTrang_Update);
        edtGioiThieu_Update = findViewById(R.id.edtGioiThieu_Update);
        imgAvatar_Update = findViewById(R.id.imgAvatar_Update);
        radioGroup_Update = findViewById(R.id.radioGroup_Update);
        btnUpdate_Update = findViewById(R.id.btnUpdate_Update);
        btnCancel_Update = findViewById(R.id.btnCancel_Update);
        btnDelete_Update = findViewById(R.id.btnDelete_Update);
        rbConHang = findViewById(R.id.rb_ConHang_Update);
        rbHetHang = findViewById(R.id.rb_HetHang_Update);
        progressBar_UpdateBook = findViewById(R.id.progressBar_Update);

        Sach sach = (Sach) getIntent().getSerializableExtra("ProductUpdate");

        try {
            File file_image = File.createTempFile("image","png");
            StorageReference storageReference = firebaseStorage.getReference().child(sach.getHinhAnh());
            storageReference.getFile(file_image)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file_image.getPath());
                            imgAvatar_Update.setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        edtTenSach_Update.setText(sach.getTenSach());
        edtTacgia_Update.setText(sach.getTacGia());
        edtNgonNgu_Update.setText(sach.getNgonNgu());
        edtNhaXuatBan_Update.setText(sach.getNhaXuatBan());
        edtNamXuatBan_Update.setText(sach.getNamXuatBan());
        edtGiaBan_Update.setText(sach.getGiaBan());
        edtSoTrang_Update.setText(sach.getSoTrang());
        edtGioiThieu_Update.setText(sach.getGioiThieu());

        theLoaiArr = new ArrayList<>();
        theLoaiArr.add("Chính trị");
        theLoaiArr.add("Pháp luật");
        theLoaiArr.add("Khoa học công nghệ");
        theLoaiArr.add("Kinh tế");
        theLoaiArr.add("Văn hóa xã hội");
        theLoaiArr.add("Lịch sử - Địa lý");
        theLoaiArr.add("Văn học");
        theLoaiArr.add("Văn học nước ngoài");
        theLoaiArr.add("Giáo trình - SGK");
        theLoaiArr.add("Truyện - Tiểu thuyết");
        theLoaiArr.add("Tâm lý");
        theLoaiArr.add("Tâm linh - Tôn giáo");
        theLoaiArr.add("Sách thiếu nhi");
        theLoaiArr.add("Kỹ năng sống");
        theLoaiArr.add("Từ điển - Ngoại ngữ");
        theLoaiArr.add("Giáo dục");
        theLoaiArr.add("Triết học");
        theLoaiArr.add("Khác");

        theLoaiArr.set(theLoaiArr.indexOf(sach.getTheLoai()),theLoaiArr.get(0));
        theLoaiArr.set(0,sach.getTheLoai());

        arrayAdapter = new ArrayAdapter(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item,theLoaiArr);
        spinner.setAdapter(arrayAdapter);

        if (sach.getTrangThai().equals("Còn hàng")){
            rbConHang.setChecked(true);
        }
        else {
            rbHetHang.setChecked(true);
        }

        //


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TheLoai = theLoaiArr.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgAvatar_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPermissionExternal();
            }
        });

        btnCancel_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Finish();
            }
        });

        btnUpdate_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String TenSach = edtTenSach_Update.getText().toString();
                String TacGia = edtTacgia_Update.getText().toString();
                String NgonNgu = edtNgonNgu_Update.getText().toString();
                String NhaXuatBan = edtNhaXuatBan_Update.getText().toString();
                String NamXuatBan = edtNamXuatBan_Update.getText().toString();
                String GioiThieu = edtGioiThieu_Update.getText().toString();
                String GiaBan = edtGiaBan_Update.getText().toString();
                String SoTrang = edtSoTrang_Update.getText().toString();
                String TrangThai;

                if (rbConHang.isChecked()){
                    TrangThai = "Còn hàng";
                }
                else{
                    TrangThai = "Hết hàng";
                }

                CheckEmpty(edtTenSach_Update);
                CheckEmpty(edtTacgia_Update);
                CheckEmpty(edtNgonNgu_Update);
                CheckEmpty(edtNhaXuatBan_Update);
                CheckEmpty(edtNamXuatBan_Update);
                CheckEmpty(edtGiaBan_Update);
                CheckEmpty(edtGioiThieu_Update);
                CheckEmpty(edtSoTrang_Update);



                if ( !CheckEmpty(edtTenSach_Update) && !CheckEmpty(edtTacgia_Update)  && !CheckEmpty(edtNgonNgu_Update)
                        && !CheckEmpty(edtNhaXuatBan_Update) && !CheckEmpty(edtNamXuatBan_Update) && !CheckEmpty(edtGioiThieu_Update)
                        && !CheckEmpty(edtGiaBan_Update) && !CheckEmpty(edtSoTrang_Update) )
                {

                    btnUpdate_Update.setVisibility(View.INVISIBLE);
                    btnCancel_Update.setVisibility(View.INVISIBLE);
                    btnDelete_Update.setVisibility(View.INVISIBLE);
                    progressBar_UpdateBook.setVisibility(View.VISIBLE);

                    Sach sach_Update = new Sach(sach.getID(),TrangThai,TenSach,TacGia,NhaXuatBan,NamXuatBan,TheLoai,NgonNgu,sach.getHinhAnh(),
                            GiaBan,SoTrang,GioiThieu,LoadingActivity.mUser.getU_Id());

                    if(URI_IMAGE != null){
                        firebaseStorage.getReference().child(sach_Update.getHinhAnh()).putFile(URI_IMAGE);
                    }

                    firebaseFirestore.collection("Product").document(sach_Update.getID()).delete();
                    firebaseFirestore.collection("Product").document(sach_Update.getID()).set(sach_Update)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Timerrrr("Cập nhật");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    btnUpdate_Update.setVisibility(View.VISIBLE);
                                    btnCancel_Update.setVisibility(View.VISIBLE);
                                    btnDelete_Update.setVisibility(View.VISIBLE);
                                    progressBar_UpdateBook.setVisibility(View.GONE);
                                }
                            });

                }



            }
        });


        btnDelete_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setIcon(R.drawable.ic_baseline_delete_24);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /////

                        btnUpdate_Update.setVisibility(View.INVISIBLE);
                        btnCancel_Update.setVisibility(View.INVISIBLE);
                        btnDelete_Update.setVisibility(View.INVISIBLE);
                        progressBar_UpdateBook.setVisibility(View.VISIBLE);

                        ArrayList<String> product_update = LoadingActivity.mUser.getUs_Product();
                        product_update.remove(sach.getID());
                        firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id()).update("us_Product",product_update);
                        firebaseFirestore.collection("Product").document(sach.getID()).delete();
                        firebaseStorage.getReference().child(sach.getHinhAnh()).delete();
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Timerrrr("Xóa");
//                                    }
//                                });


                        firebaseFirestore.collection("Cart").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                                        ItemCart item = snapshot.toObject(ItemCart.class);
                                        if (item.getId_Sach().equals(sach.getID())){

                                            firebaseFirestore.collection("Cart").document(item.getId_Cart()).delete();
                                            firebaseFirestore.collection("User").document(item.getId_User()).get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            ArrayList<String> cart = documentSnapshot.toObject(User.class).getUs_Cart() ;
                                                            cart.remove(item.getId_Cart());
                                                            firebaseFirestore.collection("User").document(item.getId_User())
                                                                    .update("us_Cart",cart);
                                                        }
                                                    });

                                        }
                                    }
                                    Timerrrr("Xóa");
                                }
                            });

                        //////
                    }
                });
                builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });


        //////////






        //////
    }

    Boolean CheckEmpty(EditText editText){
        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text)){
            editText.setError("Vui lòng nhập thông tin");
            return true;
        }
        return false;
    }

    void RequestPermissionExternal(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3000);
            }
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,4000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                RequestPermissionExternal();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4000 && resultCode == Activity.RESULT_OK){
            URI_IMAGE = data.getData();
            imgAvatar_Update.setImageURI(URI_IMAGE);

        }

    }

    void Finish(){
        Intent intent_finish = new Intent(UpdateActivity.this,MainActivity.class);
        startActivity(intent_finish);
        finish();
    }



    void Timerrrr(String s){
        CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                Toast.makeText(UpdateActivity.this, s + " sản phẩm thành công", Toast.LENGTH_SHORT).show();
                Finish();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        Finish();
    }

}