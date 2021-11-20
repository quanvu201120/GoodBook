package com.quanvu201120.goodbooksellers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quanvu201120.goodbooksellers.model.Sach;

import java.util.ArrayList;
import java.util.Date;

public class CreateBookActivity extends AppCompatActivity {


    EditText edtTenSach_Create, edtTacgia_Create,edtNgonNgu_Create,edtNhaXuatBan_Create,edtNamXuatBan_Create,edtGiaBan_Create,edtSoTrang_Create,edtGioiThieu_Create;
    ImageView imgAvatar_Create;
    RadioGroup radioGroup_Create;
    Button btnCreate_Create,btnCancel_Create;
    Spinner spinner;
    ProgressBar progressBar_CreateBook;
    RadioButton rbConHang;
    String TheLoai;
    ArrayAdapter arrayAdapter;
    ArrayList<String> theLoaiArr;

    Uri URI_IMAGE;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        //

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        edtTenSach_Create = findViewById(R.id.edtTenSach_Create);
        edtTacgia_Create = findViewById(R.id.edtTacgia_Create);
        spinner = findViewById(R.id.spinner_TheLoai_Create);
        edtNgonNgu_Create = findViewById(R.id.edtNgonNgu_Create);
        edtNhaXuatBan_Create = findViewById(R.id.edtNhaXuatBan_Create);
        edtNamXuatBan_Create = findViewById(R.id.edtNamXuatBan_Create);
        edtGiaBan_Create = findViewById(R.id.edtGiaBan_Create);
        edtSoTrang_Create = findViewById(R.id.edtSoTrang_Create);
        edtGioiThieu_Create = findViewById(R.id.edtGioiThieu_Create);
        imgAvatar_Create = findViewById(R.id.imgAvatar_Create);
        radioGroup_Create = findViewById(R.id.radioGroup_Create);
        btnCreate_Create = findViewById(R.id.btnCreate_Create);
        btnCancel_Create = findViewById(R.id.btnCancel_Create);
        rbConHang = findViewById(R.id.rb_ConHang);
        progressBar_CreateBook = findViewById(R.id.progressBar_CreateBook);

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

        arrayAdapter = new ArrayAdapter(CreateBookActivity.this, android.R.layout.simple_spinner_dropdown_item,theLoaiArr);
        spinner.setAdapter(arrayAdapter);

        rbConHang.setChecked(true);


        imgAvatar_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPermissionExternal();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TheLoai = theLoaiArr.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCreate_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String TenSach = edtTenSach_Create.getText().toString();
                String TacGia = edtTacgia_Create.getText().toString();
                String NgonNgu = edtNgonNgu_Create.getText().toString();
                String NhaXuatBan = edtNhaXuatBan_Create.getText().toString();
                String NamXuatBan = edtNamXuatBan_Create.getText().toString();
                String GioiThieu = edtGioiThieu_Create.getText().toString();
                String GiaBan = edtGiaBan_Create.getText().toString();
                String SoTrang = edtSoTrang_Create.getText().toString();
                String TrangThai;



                CheckEmpty(edtTenSach_Create);
                CheckEmpty(edtTacgia_Create);
                CheckEmpty(edtNgonNgu_Create);
                CheckEmpty(edtNhaXuatBan_Create);
                CheckEmpty(edtNamXuatBan_Create);
                CheckEmpty(edtGiaBan_Create);
                CheckEmpty(edtGioiThieu_Create);
                CheckEmpty(edtSoTrang_Create);


                if (rbConHang.isChecked()){
                    TrangThai = "Còn hàng";
                }
                else {
                    TrangThai = "Hết hàng";
                }

                if(URI_IMAGE == null){
                    Toast.makeText(CreateBookActivity.this, "Vui lòng chọn hình", Toast.LENGTH_SHORT).show();
                }

                if ( !CheckEmpty(edtTenSach_Create) && !CheckEmpty(edtTacgia_Create)  && !CheckEmpty(edtNgonNgu_Create)
                        && !CheckEmpty(edtNhaXuatBan_Create) && !CheckEmpty(edtNamXuatBan_Create) && !CheckEmpty(edtGioiThieu_Create) && URI_IMAGE!=null
                        && !CheckEmpty(edtGiaBan_Create) && !CheckEmpty(edtSoTrang_Create) )
                {

                    btnCreate_Create.setVisibility(View.INVISIBLE);
                    btnCancel_Create.setVisibility(View.INVISIBLE);
                    progressBar_CreateBook.setVisibility(View.VISIBLE);

                    //upload image book
                    Date date = new Date();
                    String Book_ID = LoadingActivity.mUser.getU_Id() + date.getTime();
                    String  ImageName = Book_ID + ".png";



                    Sach sach = new Sach(Book_ID,TrangThai,TenSach,TacGia,NhaXuatBan,NamXuatBan,TheLoai,NgonNgu,ImageName,
                            GiaBan,SoTrang,GioiThieu,LoadingActivity.mUser.getU_Id());


                    ArrayList<String> productList = new ArrayList<>();
                    if (LoadingActivity.mUser.getUs_Product() != null){
                        productList.addAll(LoadingActivity.mUser.getUs_Product());
                    }
                    productList.add(Book_ID);

                    LoadingActivity.mUser.setUs_Product(productList);

                    firebaseFirestore.collection("Product").document(Book_ID).set(sach);

                    firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id()).update("us_Product",LoadingActivity.mUser.getUs_Product());

                    StorageReference storageReference = firebaseStorage.getReference().child(ImageName);
                    storageReference.putFile(URI_IMAGE).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.e("ABC Product create book",LoadingActivity.mUser.getUs_Product().toString());
                            Timerrrr();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            btnCreate_Create.setVisibility(View.VISIBLE);
                            btnCancel_Create.setVisibility(View.VISIBLE);
                            progressBar_CreateBook.setVisibility(View.GONE);
                        }
                    });




                }


                //
            }
        });


        btnCancel_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
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
            imgAvatar_Create.setImageURI(URI_IMAGE);

        }

    }





    void Timerrrr(){
        CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                Toast.makeText(CreateBookActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        countDownTimer.start();
    }

}