package com.quanvu201120.goodbook;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quanvu201120.goodbook.model.User;

public class SignUpActivity extends AppCompatActivity {

    EditText edtEmail_SignUp,edtName_SignUp,edtPhone_SignUp,edtPass2_SignUp,edtPass_SignUp;
    Button btnCancel_SignUp,btnDangKy_SignUp;
    ProgressBar progressBar_SignUp;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        edtEmail_SignUp = findViewById(R.id.edtEmail_SignUp);
        edtName_SignUp = findViewById(R.id.edtName_SignUp);
        edtPhone_SignUp = findViewById(R.id.edtPhone_SignUp);
        edtPass2_SignUp = findViewById(R.id.edtPass2_SignUp);
        edtPass_SignUp = findViewById(R.id.edtPass_SignUp);
        btnCancel_SignUp = findViewById(R.id.btnCancel_SignUp);
        btnDangKy_SignUp = findViewById(R.id.btnDangKy_SignUp);
        progressBar_SignUp = findViewById(R.id.progressBar_SignUp);

        btnCancel_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDangKy_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String email = edtEmail_SignUp.getText().toString().trim();
                String pass1 = edtPass_SignUp.getText().toString().trim();
                String pass2 = edtPass2_SignUp.getText().toString().trim();
                String name = edtName_SignUp.getText().toString().trim();
                String phone = edtPhone_SignUp.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                    edtEmail_SignUp.setError("Vui lòng nhập thông tin");
                if (TextUtils.isEmpty(pass1))
                    edtPass_SignUp.setError("Vui lòng nhập thông tin");
                if (TextUtils.isEmpty(pass2))
                    edtPass2_SignUp.setError("Vui lòng nhập thông tin");
                if (TextUtils.isEmpty(name))
                    edtName_SignUp.setError("Vui lòng nhập thông tin");
                if (TextUtils.isEmpty(phone))
                    edtPhone_SignUp.setError("Vui lòng nhập thông tin");
                if ( !TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(pass2) && !CheckMK(pass1,pass2))
                    edtPass2_SignUp.setError("Mật khẩu không khớp");
                if (pass1.length() < 6)
                    edtPass_SignUp.setError("Mật khẩu trên 6 ký tự");
                if (pass2.length() < 6)
                    edtPass2_SignUp.setError("Mật khẩu trên 6 ký tự");



                if ( !TextUtils.isEmpty(email) && CheckMK(pass1,pass2) && !TextUtils.isEmpty(pass1)
                        && !TextUtils.isEmpty(pass2) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)){

                    progressBar_SignUp.setVisibility(View.VISIBLE);
                    btnDangKy_SignUp.setVisibility(View.INVISIBLE);
                    btnCancel_SignUp.setVisibility(View.INVISIBLE);

                    mAuth.createUserWithEmailAndPassword(email,pass1)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    String UserID = authResult.getUser().getUid();
                                    User user = new User(UserID,email,name,phone,"default_null","default_null",null,null,null,null,null);
                                    firestore.collection("User").document(UserID).set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = "The email address is already in use by another account.";
                                    if (e.getMessage() == error){
                                        Toast.makeText(SignUpActivity.this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, "Định dạng email không chính xác", Toast.LENGTH_SHORT).show();
                                    }
                                    btnDangKy_SignUp.setVisibility(View.VISIBLE);
                                    btnCancel_SignUp.setVisibility(View.VISIBLE);
                                    progressBar_SignUp.setVisibility(View.GONE);
                                }
                            });

                }
                //
            }
        });

    }

    Boolean CheckMK(String s1, String s2){
        if (s1.equals(s2)){
            return true;
        }
        return false;
    }
}