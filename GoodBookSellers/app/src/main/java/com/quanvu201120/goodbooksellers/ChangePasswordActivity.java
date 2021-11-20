package com.quanvu201120.goodbooksellers;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {


    EditText editText1, editText2;
    Button button;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editText1 = findViewById(R.id.edtNewPass1_UpdatePass);
        editText2 = findViewById(R.id.edtNewPass2_UpdatePass);
        button = findViewById(R.id.btnUpdate_UpdatePass);
        progressBar = findViewById(R.id.progressBar_UpdatePass);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String MatKhauMoi = editText1.getText().toString().trim();
                String XacNhanMK = editText2.getText().toString().trim();


                if (TextUtils.isEmpty(MatKhauMoi)){
                    editText1.setError("Vui lòng nhập thông tin");
                }
                if (TextUtils.isEmpty(XacNhanMK)){
                    editText2.setError("Vui lòng nhập thông tin");
                }

                if( !TextUtils.isEmpty(XacNhanMK) && !TextUtils.isEmpty(MatKhauMoi) && !MatKhauMoi.equals(XacNhanMK)){
                    editText2.setError("Mật khẩu không khớp");
                }
                if( !TextUtils.isEmpty(XacNhanMK) && !TextUtils.isEmpty(MatKhauMoi) && MatKhauMoi.equals(XacNhanMK)){
                    button.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseUser.updatePassword(MatKhauMoi)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangePasswordActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    button.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }

            }
        });

    }
}