package com.quanvu201120.goodbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quanvu201120.goodbook.model.Address;



public class UpdateAddressFragment extends DialogFragment {

    EditText edtHoTen_UpdateAdress,edtSDT_UpdateAdress,edtDiaChi_1_UpdateAdress,edtDiaChi_2_UpdateAdress,edtDiaChi_3_UpdateAdress,edtDiaChi_4_UpdateAdress;
    Button btnOK_UpdateAddress,btnCancel_UpdateAddress;
    ProgressBar progressBar;
    Address address;
    FirebaseFirestore firebaseFirestore;

    public UpdateAddressFragment() {
    }

    public UpdateAddressFragment (Address address){

        this.address = address;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtHoTen_UpdateAdress = view.findViewById(R.id.edtHoTen_UpdateAdress);
        edtSDT_UpdateAdress = view.findViewById(R.id.edtSDT_UpdateAdress);
        edtDiaChi_1_UpdateAdress = view.findViewById(R.id.edtDiaChi_1_UpdateAdress);
        edtDiaChi_2_UpdateAdress = view.findViewById(R.id.edtDiaChi_2_UpdateAdress);
        edtDiaChi_3_UpdateAdress = view.findViewById(R.id.edtDiaChi_3_UpdateAdress);
        edtDiaChi_4_UpdateAdress = view.findViewById(R.id.edtDiaChi_4_UpdateAdress);
        btnOK_UpdateAddress = view.findViewById(R.id.btnOK_UpdateAddress);
        btnCancel_UpdateAddress = view.findViewById(R.id.btnCancel_UpdateAddress);
        progressBar = view.findViewById(R.id.progressBar_UpdateAddress);

        firebaseFirestore = FirebaseFirestore.getInstance();


        String[] address_mini_list = address.getAddress().split(" - ");



        edtHoTen_UpdateAdress.setText(address.getName());
        edtSDT_UpdateAdress.setText(address.getPhone());
        edtDiaChi_1_UpdateAdress.setText(address_mini_list[3]);
        edtDiaChi_2_UpdateAdress.setText(address_mini_list[2]);
        edtDiaChi_3_UpdateAdress.setText(address_mini_list[1]);
        edtDiaChi_4_UpdateAdress.setText(address_mini_list[0]);

        btnCancel_UpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnOK_UpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckEmpty(edtHoTen_UpdateAdress);
                CheckEmpty(edtSDT_UpdateAdress);
                CheckEmpty(edtDiaChi_1_UpdateAdress);
                CheckEmpty(edtDiaChi_2_UpdateAdress);
                CheckEmpty(edtDiaChi_3_UpdateAdress);
                CheckEmpty(edtDiaChi_4_UpdateAdress);

                if ( !CheckEmpty(edtHoTen_UpdateAdress) && !CheckEmpty(edtSDT_UpdateAdress) && !CheckEmpty(edtDiaChi_1_UpdateAdress)
                        && !CheckEmpty(edtDiaChi_2_UpdateAdress) && !CheckEmpty(edtDiaChi_3_UpdateAdress) && !CheckEmpty(edtDiaChi_4_UpdateAdress)){

                    progressBar.setVisibility(View.VISIBLE);
                    btnOK_UpdateAddress.setVisibility(View.INVISIBLE);
                    btnCancel_UpdateAddress.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminateDrawable(new DoubleBounce());

                    String strName = edtHoTen_UpdateAdress.getText().toString().trim();
                    String strPhone = edtSDT_UpdateAdress.getText().toString().trim();
                    String strAddress = edtDiaChi_4_UpdateAdress.getText().toString().trim() + " - " +
                            edtDiaChi_3_UpdateAdress.getText().toString().trim() + " - " +
                            edtDiaChi_2_UpdateAdress.getText().toString().trim() + " - " +
                            edtDiaChi_1_UpdateAdress.getText().toString().trim();

                    Address address_update = new Address(address.getId(),address.getIdUser(),strName,strPhone,strAddress);

                    firebaseFirestore.collection("Address").document(address.getId())
                            .set(address_update)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                    getDialog().dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    btnOK_UpdateAddress.setVisibility(View.VISIBLE);
                                    btnCancel_UpdateAddress.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });

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
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

    }
}