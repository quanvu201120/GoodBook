package com.quanvu201120.goodbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.quanvu201120.goodbook.model.Address;
import com.quanvu201120.goodbook.model.AddressAdapter;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {


    ListView listView;
    Button btnAdd_Address;
    AddressAdapter addressAdapter;
    ArrayList<Address> addressArrayList;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> arrayList ;
    ArrayList<String> keysDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        listView = findViewById(R.id.listView_Address);
        btnAdd_Address = findViewById(R.id.btnAdd_Address);

        firebaseFirestore = FirebaseFirestore.getInstance();

        addressArrayList = new ArrayList<>();
        keysDocument = new ArrayList<>();
        arrayList =  new ArrayList<>();

        addressAdapter = new AddressAdapter(AddressActivity.this,R.layout.item_listview_address,addressArrayList);
        listView.setAdapter(addressAdapter);

        btnAdd_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this,CreateAddressActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getDataRealTimeFireStore();

        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_address,menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()){
            case R.id.item_update_address:

                UpdateAddressFragment updateAddressFragment = new UpdateAddressFragment(addressArrayList.get(position));
                updateAddressFragment.show(getSupportFragmentManager(),null);

                break;
            case R.id.item_delete_address:

                DeleteAddress( addressArrayList.get(position) );

                break;
        }

        return super.onContextItemSelected(item);
    }


    void DeleteAddress(Address address){


        AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
        builder.setTitle("Xóa địa chỉ");
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                arrayList = LoadingActivity.mUser.getUs_Address();
                arrayList.remove(address.getId());
                LoadingActivity.mUser.setUs_Address(arrayList);


                firebaseFirestore.collection("Address").document(address.getId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                firebaseFirestore.collection("User").document(LoadingActivity.mUser.getU_Id())
                                        .update("us_Address",LoadingActivity.mUser.getUs_Address())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddressActivity.this, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                                addressArrayList.remove(address);
                                                keysDocument.remove(address.getId());
                                                addressAdapter.notifyDataSetChanged();
                                            }
                                        });

                            }
                        });



            }
        });
        builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }



    void getDataRealTimeFireStore() {
        firebaseFirestore.collection("Address").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    DocumentSnapshot documentSnapshot = documentChange.getDocument();

                    if(LoadingActivity.mUser.getUs_Address() != null && LoadingActivity.mUser.getUs_Address().indexOf(documentSnapshot.toObject(Address.class).getId()) >= 0) {

                        switch (documentChange.getType()) {
                            case ADDED:
                                keysDocument.add(documentSnapshot.getId());
                                addressArrayList.add(documentSnapshot.toObject(Address.class));
                                addressAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                if (keysDocument.indexOf(documentSnapshot.getId()) >= 0) {
                                    addressArrayList.set(keysDocument.indexOf(documentSnapshot.getId()), documentSnapshot.toObject(Address.class));
                                }
                                addressAdapter.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                if (keysDocument.indexOf(documentSnapshot.getId()) >= 0) {
                                    addressArrayList.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                    keysDocument.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                }
                                addressAdapter.notifyDataSetChanged();
                                break;
                        }
                    }

                }
            }
        });
    }


}