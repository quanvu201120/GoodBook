package com.quanvu201120.goodbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quanvu201120.goodbook.model.ItemCart;
import com.quanvu201120.goodbook.model.User;
import com.quanvu201120.goodbook.model.mHistory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseUser firebaseUser;
    public static FirebaseFirestore firebaseFirestore;
    public static FirebaseStorage firebaseStorage;

    public static User mUser;
    public static File image_file;
    public static ArrayList<ItemCart> mCart;
    public static ArrayList<mHistory> mHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        progressBar = findViewById(R.id.progressBar_loading);
        progressBar.setIndeterminateDrawable(new WanderingCubes());

        firebaseFirestore.collection("User").document(firebaseUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        mUser = documentSnapshot.toObject(User.class);
                        getImage_Storage(mUser.getU_Avatar());

                        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);


                        getDataRealTimeFireStore_User();

                        mCart = new ArrayList<>();
                        if (mUser.getUs_Cart()!=null){
                            Getcart();
                        }

                        mHistory = new ArrayList<>();
                        if (mUser.getUs_History()!=null){
                            GetHistory();
                        }

                        startActivity(intent);
                    }
                });



    }

    public static void getImage_Storage(String image){
        if (!MainActivity.checkString("default_null",image)){
            try {

                image_file = File.createTempFile("image","png");
                StorageReference storageReference = firebaseStorage.getReference().child(image);
                storageReference.getFile(image_file);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void getDataRealTimeFireStore_User() {
        firebaseFirestore.collection("User").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    DocumentSnapshot documentSnapshot = documentChange.getDocument();
                    switch (documentChange.getType()){
                        case ADDED:

                            break;
                        case MODIFIED:
                            if(MainActivity.checkString(mUser.getU_Id(),documentSnapshot.toObject(User.class).getU_Id())){
                                mUser = documentSnapshot.toObject(User.class);
                                Log.e("abc main update user",mUser.toString());
                            }
                            break;
                        case REMOVED:

                            break;
                    }
                }
            }
        });
    }



    public static void Getcart(){
        mCart.clear();
        for (String s : mUser.getUs_Cart()){
            firebaseFirestore.collection("Cart").document(s).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            mCart.add(documentSnapshot.toObject(ItemCart.class));
//                            Log.e("ABC test",documentSnapshot.toObject(ItemCart.class).toString());
                        }
                    });
        }

    }

    public static void GetHistory(){
        mHistory.clear();
        for (String s : mUser.getUs_History()){
            firebaseFirestore.collection("History").document(s).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            mHistory.add(documentSnapshot.toObject(mHistory.class));
                        }
                    });
        }

    }

}