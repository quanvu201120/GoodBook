package com.quanvu201120.goodbooksellers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnDangNhap;
    TextView txtDangKy, txtQuenMK;
    ProgressBar progressBar_Login;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail_Login);
        edtPassword = findViewById(R.id.edtPassword_Login);
        btnDangNhap = findViewById(R.id.btnDangNhap_Login);
        txtDangKy = findViewById(R.id.txtDangKy_Login);
        txtQuenMK = findViewById(R.id.txtQuenMK_Login);
        progressBar_Login = findViewById(R.id.progressBar_Login);

        mAuth = FirebaseAuth.getInstance();



        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Vui lòng nhập thông tin");
                }
                if (TextUtils.isEmpty(password)){
                    edtPassword.setError("Vui lòng nhập thông tin");
                }

                if ( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    btnDangNhap.setVisibility(View.INVISIBLE);
                    progressBar_Login.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    IntentMainActivity();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    String error1 = "The email address is badly formatted.";
                                    String error2 = "There is no user record corresponding to this identifier. The user may have been deleted.";
                                    String error3 = "The password is invalid or the user does not have a password.";

                                    if (e.getMessage() == error1){
                                        Toast.makeText(LoginActivity.this, "Định dạng email không chính xác", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(e.getMessage() == error2){
                                        Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                    }
                                    btnDangNhap.setVisibility(View.VISIBLE);
                                    progressBar_Login.setVisibility(View.GONE);

                                }
                            });
                }

            }
        });

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });

        txtQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_quen_mat_khau);

                EditText edtQuenMK = dialog.findViewById(R.id.edtQuenMatKhau);
                ImageView imgQuenMK = dialog.findViewById(R.id.imgQuenMatKhau);
                Button btnQuenMK = dialog.findViewById(R.id.btnQuenMatKhau);
                ProgressBar progressBar_QuenMK = dialog.findViewById(R.id.progressBar_QuenMK);


                btnQuenMK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        String email = edtQuenMK.getText().toString().trim();

                        if (TextUtils.isEmpty(email)){
                            edtQuenMK.setError("Vui lòng nhập thông tin");
                        }
                        else {
                            btnQuenMK.setVisibility(View.INVISIBLE);
                            progressBar_QuenMK.setVisibility(View.VISIBLE);
                            mAuth.sendPasswordResetEmail(email)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra email", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            String error1 = "The email address is badly formatted.";
                                            String error2 = "There is no user record corresponding to this identifier. The user may have been deleted.";

                                            if (e.getMessage() == error1){
                                                Toast.makeText(LoginActivity.this, "Định dạng email không chính xác", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(e.getMessage() == error2){
                                                Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                            }
                                            btnQuenMK.setVisibility(View.VISIBLE);
                                            progressBar_QuenMK.setVisibility(View.GONE);
                                        }
                                    });
                        }
                        //
                    }
                });

                imgQuenMK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();

                //

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            IntentMainActivity();
        }

    }

    private void IntentMainActivity() {
        Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
        startActivity(intent);
        finish();
    }

}