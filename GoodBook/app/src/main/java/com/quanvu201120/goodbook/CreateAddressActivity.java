package com.quanvu201120.goodbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quanvu201120.goodbook.model.Address;

import java.util.ArrayList;
import java.util.Date;

public class CreateAddressActivity extends AppCompatActivity {

    EditText edtHoTen_CreateAdress,edtSDT_CreateAdress,edtDiaChi_1_CreateAdress,edtDiaChi_2_CreateAdress,edtDiaChi_3_CreateAdress,edtDiaChi_4_CreateAdress;
    Button btnOK_CreateAddress,btnCancel_CreateAddress;
    ProgressBar progressBar;

    FirebaseFirestore firebaseFirestore;
    ArrayList<String> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);

        edtHoTen_CreateAdress = findViewById(R.id.edtHoTen_CreateAdress);
        edtSDT_CreateAdress = findViewById(R.id.edtSDT_CreateAdress);
        edtDiaChi_1_CreateAdress = findViewById(R.id.edtDiaChi_1_CreateAdress);
        edtDiaChi_2_CreateAdress = findViewById(R.id.edtDiaChi_2_CreateAdress);
        edtDiaChi_3_CreateAdress = findViewById(R.id.edtDiaChi_3_CreateAdress);
        edtDiaChi_4_CreateAdress = findViewById(R.id.edtDiaChi_4_CreateAdress);
        btnOK_CreateAddress = findViewById(R.id.btnOK_CreateAddress);
        btnCancel_CreateAddress = findViewById(R.id.btnCancel_CreateAddress);
        progressBar = findViewById(R.id.progressBar_CreateAddress);

        firebaseFirestore = FirebaseFirestore.getInstance();

        addressList = new ArrayList<>();


        if (LoadingActivity.mUser.getUs_Address() != null){
            addressList = LoadingActivity.mUser.getUs_Address();
        }

        btnOK_CreateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CheckEmpty(edtHoTen_CreateAdress);
                CheckEmpty(edtSDT_CreateAdress);
                CheckEmpty(edtDiaChi_1_CreateAdress);
                CheckEmpty(edtDiaChi_2_CreateAdress);
                CheckEmpty(edtDiaChi_3_CreateAdress);
                CheckEmpty(edtDiaChi_4_CreateAdress);

                if ( !CheckEmpty(edtHoTen_CreateAdress) && !CheckEmpty(edtSDT_CreateAdress) && !CheckEmpty(edtDiaChi_1_CreateAdress)
                        && !CheckEmpty(edtDiaChi_2_CreateAdress) && !CheckEmpty(edtDiaChi_3_CreateAdress) && !CheckEmpty(edtDiaChi_4_CreateAdress)){

                    progressBar.setVisibility(View.VISIBLE);
                    btnOK_CreateAddress.setVisibility(View.INVISIBLE);
                    btnCancel_CreateAddress.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminateDrawable(new DoubleBounce());

                    String strName = edtHoTen_CreateAdress.getText().toString().trim();
                    String strPhone = edtSDT_CreateAdress.getText().toString().trim();
                    String strAddress = edtDiaChi_4_CreateAdress.getText().toString().trim() + " - " +
                                        edtDiaChi_3_CreateAdress.getText().toString().trim() + " - " +
                                        edtDiaChi_2_CreateAdress.getText().toString().trim() + " - " +
                                        edtDiaChi_1_CreateAdress.getText().toString().trim();


                    Date date = new Date();
                    String ID_Address = LoadingActivity.mUser.getU_Id() + date.getTime();
                    Address address = new Address(ID_Address,LoadingActivity.mUser.getU_Id(),strName,strPhone,strAddress);

                    firebaseFirestore.collection("Address").document(ID_Address)
                            .set(address)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    addressList.add(ID_Address);
                                    LoadingActivity.mUser.setUs_Address(addressList);
                                    firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id())
                                            .update("us_Address",LoadingActivity.mUser.getUs_Address())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(CreateAddressActivity.this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                                    Intent_finish();
                                                }
                                            });



                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    btnOK_CreateAddress.setVisibility(View.VISIBLE);
                                    btnCancel_CreateAddress.setVisibility(View.VISIBLE);
                                    Toast.makeText(CreateAddressActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }

            }
        });


        btnCancel_CreateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent_finish();
            }
        });

    }

    private void Intent_finish() {
        Intent intent = new Intent(CreateAddressActivity.this, AddressActivity.class);
        startActivity(intent);
        finish();
    }

    boolean CheckEmpty(EditText editText){
        String s = editText.getText().toString();
        if (s.isEmpty()){
            editText.setError("Vui lòng nhập thông tin");
            return true;
        }
        else return false;
    }

    @Override
    public void onBackPressed() {

        Intent_finish();

    }
}