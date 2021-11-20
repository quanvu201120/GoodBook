package com.quanvu201120.goodbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.quanvu201120.goodbook.model.Sach;
import com.quanvu201120.goodbook.model.SachAdapter;
import com.quanvu201120.goodbook.model.User;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity  {

    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    TextView txtAvatar_Shop, txtName_Shop;
    RecyclerView recyclerView;
    String ID_SHOP;
    User shop;
    ArrayList<String> keysDocument;
    ArrayList<Sach> sachArrayList;
    SachAdapter sachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        recyclerView = findViewById(R.id.recycleview_Shop);
        txtAvatar_Shop = findViewById(R.id.txtAvatar_Shop);
        txtName_Shop = findViewById(R.id.txtName_Shop);

        ID_SHOP = getIntent().getStringExtra("ID_SHOP");
        keysDocument = new ArrayList<>();
        sachArrayList = new ArrayList<>();
        sachAdapter = new SachAdapter(sachArrayList, new SachAdapter.onItemSachClick() {
            @Override
            public void onClickSach(Sach sach) {
                Intent intent = new Intent(ShopActivity.this,ProductAcitivty.class);
                intent.putExtra("SendBookToProduct",sach);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        firebaseFirestore.collection("User").document(ID_SHOP).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        shop = documentSnapshot.toObject(User.class);
                        txtName_Shop.setText(shop.getU_StoreName());

                        txtAvatar_Shop.setText(shop.getU_StoreName().toUpperCase().charAt(0) + "");

                        getDataRealTimeFireStore();
                        recyclerView.setAdapter(sachAdapter);

                    }
                });



    }


    void getDataRealTimeFireStore() {
        firebaseFirestore.collection("Product").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    DocumentSnapshot documentSnapshot = documentChange.getDocument();

                    if ( shop.getUs_Product() != null && shop.getUs_Product().indexOf(documentSnapshot.getId()) >= 0){
                        switch (documentChange.getType()){
                            case ADDED:
                                keysDocument.add(documentSnapshot.getId());
                                sachArrayList.add(documentSnapshot.toObject(Sach.class));
                                sachAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                if(keysDocument.indexOf(documentSnapshot.getId()) >= 0){
                                    sachArrayList.set(keysDocument.indexOf(documentSnapshot.getId()), documentSnapshot.toObject(Sach.class));
                                }
                                sachAdapter.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                if(keysDocument.indexOf(documentSnapshot.getId()) >= 0){
                                    sachArrayList.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                    keysDocument.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                }
                                sachAdapter.notifyDataSetChanged();
                                break;
                        }

                    }
                }
            }
        });
    }



}