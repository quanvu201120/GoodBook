package com.quanvu201120.goodbooksellers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.quanvu201120.goodbooksellers.fragment_navigation.AccountFragment;
import com.quanvu201120.goodbooksellers.fragment_navigation.OrderFragment;
import com.quanvu201120.goodbooksellers.fragment_navigation.ProductFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    FirebaseAuth firebaseAuth;
//    FirebaseFirestore firebaseFirestore;
//    FirebaseUser firebaseUser;
//    public static FirebaseStorage firebaseStorage;

//    public static User mUser;
//    public static File image_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//        firebaseStorage = FirebaseStorage.getInstance();

        drawerLayout = findViewById(R.id.drawerLayoutMain);
        toolbar = findViewById(R.id.toolbar_main);
        navigationView = findViewById(R.id.navigation_main);



//        firebaseFirestore.collection("User").document(firebaseUser.getUid()).get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        mUser = documentSnapshot.toObject(User.class);
//                        getImage_Storage(mUser.getU_Avatar());
//
//                        Log.e("ABC Main getProduct",mUser.getUs_Product() + "");
//                        Log.e("ABC Main user",mUser.toString());
//                        //////
//                        removeColor(navigationView);
//                        navigationView.getMenu().getItem(0).setChecked(true);
//                        showFragmentNavigation(R.id.item_SanPham);
//                        /////
//                    }
//                });

        //


            removeColor(navigationView);
            navigationView.getMenu().getItem(0).setChecked(true);
            showFragmentNavigation(R.id.item_SanPham);

        //


        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                removeColor(navigationView);
                item.setChecked(true);
                showFragmentNavigation(item.getItemId());


                return false;
            }
        });


//        getDataRealTimeFireStore_User();

    }

    private void showFragmentNavigation(@NonNull int item) {




        Fragment fragment = null;
        switch (item){
            case R.id.item_SanPham:
                fragment = new ProductFragment();
                break;
            case  R.id.item_DonHang:
                fragment = new OrderFragment();
                break;

            case  R.id.item_TaiKhoan:
                fragment = new AccountFragment();
                break;

            case  R.id.item_DangXuat:

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                firebaseAuth.signOut();
                finish();
                break;

        }



        if (fragment != null){
            drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout_main,fragment);
            fragmentTransaction.commit();

        }
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();

        }
        else if (!ProductFragment.searchView.isIconified()) {
            ProductFragment.searchView.setIconified(true);
            return;
        }
        
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Bạn có muốn thoát ứng dụng");
            builder.setIcon(R.drawable.ic_baseline_warning_24);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNeutralButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }



    }

    public static boolean checkString(String s1, String s2){
        if (s1.equals(s2)){
            return true;
        }
        return false;
    }
    private void removeColor(NavigationView view) {
        for (int i = 0; i < view.getMenu().size(); i++) {
            MenuItem item = view.getMenu().getItem(i);
            item.setChecked(false);
        }
    }

//    public static void getImage_Storage(String image){
//        if (!checkString("null",image)){
//            try {
//
//                image_file = File.createTempFile("image","png");
//                StorageReference storageReference = firebaseStorage.getReference().child(image);
//                storageReference.getFile(image_file);
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }


    //
//    private void getDataRealTimeFireStore_User() {
//        firebaseFirestore.collection("User").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                for(DocumentChange documentChange : value.getDocumentChanges()){
//                    DocumentSnapshot documentSnapshot = documentChange.getDocument();
//                    switch (documentChange.getType()){
//                        case ADDED:
//
//                            break;
//                        case MODIFIED:
//                            if(checkString(mUser.getU_Id(),documentSnapshot.toObject(User.class).getU_Id())){
//                                mUser = documentSnapshot.toObject(User.class);
//                                Log.e("abc main update user",mUser.toString());
//                            }
//                            break;
//                        case REMOVED:
//
//                            break;
//                    }
//                }
//            }
//        });
//    }
    //




}