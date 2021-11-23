package com.quanvu201120.goodbook.navigation_main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quanvu201120.goodbook.AddressActivity;
import com.quanvu201120.goodbook.ChangePasswordActivity;
import com.quanvu201120.goodbook.LoadingActivity;
import com.quanvu201120.goodbook.R;


public class AccountFragment extends Fragment {


    Button btnDoiMK_Account,btnAddress_Account;
    TextView txtEmail_Account, txtName_Account, txtPhone_Account;
    ImageView imgEditName_Account, imgEditPhone_Account,imgAvatar_Account;
    ProgressBar progressBar_Account;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;
    Uri URI_IMAGE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        //


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();

        txtEmail_Account = view.findViewById(R.id.txtEmail_Account);
        imgAvatar_Account = view.findViewById(R.id.imgAvatar_Account);
        txtName_Account = view.findViewById(R.id.txtName_Account);
        txtPhone_Account = view.findViewById(R.id.txtPhone_Account);
        btnDoiMK_Account = view.findViewById(R.id.btnDoiMK_Account);
        btnAddress_Account = view.findViewById(R.id.btnAddress_Account);
        imgEditName_Account = view.findViewById(R.id.imgEditName_Account);
        imgEditPhone_Account = view.findViewById(R.id.imgEditPhone_Account);
        progressBar_Account = view.findViewById(R.id.progressBar_Account);
        

        if (LoadingActivity.mUser != null){
            txtEmail_Account.setText(LoadingActivity.mUser.getU_Email());
            txtName_Account.setText(LoadingActivity.mUser.getU_Name());
            txtPhone_Account.setText(LoadingActivity.mUser.getU_Phone());
            if (!checkString("default_null",LoadingActivity.mUser.getU_Avatar())){
                Bitmap bitmap = BitmapFactory.decodeFile(LoadingActivity.image_file.getPath());
                imgAvatar_Account.setImageBitmap(bitmap);
            }
        }


        btnAddress_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });

        btnDoiMK_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.check_password_dialog);

                EditText editText = dialog.findViewById(R.id.edtPass_CheckPassword);
                Button btnOK = dialog.findViewById(R.id.btnOK_CheckPassword);
                ImageView imgClose = dialog.findViewById(R.id.imgClose_CheckPassword_dialog);
                ProgressBar progressBar = dialog.findViewById(R.id.progressBar_CheckPassword);



                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        String str_editext = editText.getText().toString().trim();

                        if (TextUtils.isEmpty(str_editext)){
                            editText.setError("Vui lòng nhập thông tin");
                        }
                        else {
                            btnOK.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            firebaseAuth.signInWithEmailAndPassword(LoadingActivity.mUser.getU_Email(),str_editext)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            btnOK.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        //
                    }
                });

                imgClose.setOnClickListener(new View.OnClickListener() {
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


        imgEditName_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.update_user_name);

                EditText edtName = dialog.findViewById(R.id.edtName_UpdateName);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate_UpdateNamer);
                ImageView imgClose = dialog.findViewById(R.id.imgClose_UpdateName);
                ProgressBar progressBar = dialog.findViewById(R.id.progressBar_UpdateName);

                edtName.setText(LoadingActivity.mUser.getU_Name());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        String name = edtName.getText().toString().trim();

                        if (TextUtils.isEmpty(name)){
                            edtName.setError("Vui lòng nhập thông tin");
                        }
                        else {
                            btnUpdate.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id()).update("u_Name",name)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(), "Đổi tên thành công", Toast.LENGTH_SHORT).show();
                                            LoadingActivity.mUser.setU_Name(name);
                                            txtName_Account.setText(name);
                                            dialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            btnUpdate.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        //
                    }
                });

                imgClose.setOnClickListener(new View.OnClickListener() {
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

        imgEditPhone_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.update_user_phone);

                EditText edtPhone = dialog.findViewById(R.id.edtPhone_UpdatePhone);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate_UpdatePhone);
                ImageView imgClose = dialog.findViewById(R.id.imgClose_UpdatePhone);
                ProgressBar progressBar = dialog.findViewById(R.id.progressBar_UpdatePhone);

                edtPhone.setText(LoadingActivity.mUser.getU_Phone());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        String phone = edtPhone.getText().toString().trim();

                        if (TextUtils.isEmpty(phone)){
                            edtPhone.setError("Vui lòng nhập thông tin");
                        }
                        else {
                            btnUpdate.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id()).update("u_Phone",phone)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(), "Đổi số điện thoại thành công", Toast.LENGTH_SHORT).show();
                                            LoadingActivity.mUser.setU_Phone(phone);
                                            txtPhone_Account.setText(phone);
                                            dialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            btnUpdate.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        //
                    }
                });

                imgClose.setOnClickListener(new View.OnClickListener() {
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

        imgAvatar_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPermissionExternal();
            }
        });

        //
        return view;
    }

    Boolean checkString(String s1, String s2){
        if (s1.equals(s2)){
            return true;
        }
        return false;
    }

    void RequestPermissionExternal(){
        if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
            }
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                RequestPermissionExternal();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000 && resultCode == Activity.RESULT_OK){
            URI_IMAGE = data.getData();
            progressBar_Account.setVisibility(View.VISIBLE);
            String ImageName = LoadingActivity.mUser.getU_Id() + ".png";
            StorageReference storageReference = firebaseStorage.getReference().child(ImageName);
            storageReference.putFile(URI_IMAGE)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar_Account.setVisibility(View.GONE);

                            LoadingActivity.mUser.setU_Avatar(ImageName);
                            firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id()).update("avatarUser",ImageName);
                            imgAvatar_Account.setImageURI(URI_IMAGE);
                            LoadingActivity.getImage_Storage(LoadingActivity.mUser.getU_Avatar());
                        }
                    });

        }

    }

}