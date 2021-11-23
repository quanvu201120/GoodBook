package com.quanvu201120.goodbook;

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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.quanvu201120.goodbook.model.Sach;
import com.quanvu201120.goodbook.model.SachAdapter;
import com.quanvu201120.goodbook.navigation_main.AccountFragment;
import com.quanvu201120.goodbook.navigation_main.CartFragment;
import com.quanvu201120.goodbook.navigation_main.HistoryFragment;
import com.quanvu201120.goodbook.navigation_main.HomeFragment;

public class MainActivity extends AppCompatActivity implements SachAdapter.onItemSachClick {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayoutMain);
        toolbar = findViewById(R.id.toolbar_main);
        navigationView = findViewById(R.id.navigation_main);

        firebaseAuth = FirebaseAuth.getInstance();
//

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);


        showFragmentNavigation(R.id.item_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                showFragmentNavigation(item.getItemId());

                return false;
            }
        });




    }

    private void showFragmentNavigation(@NonNull int item) {
        RemoveColor(navigationView);
        Fragment fragment = null;
        switch (item){
            case R.id.item_home:

                navigationView.getMenu().getItem(0).setChecked(true);


                fragment = new HomeFragment();
                break;
            case  R.id.item_account:

                navigationView.getMenu().getItem(1).setChecked(true);

                fragment = new AccountFragment();
                break;

            case  R.id.item_cart:

                navigationView.getMenu().getItem(2).setChecked(true);
                fragment = new CartFragment();

                break;

            case  R.id.item_history:

                navigationView.getMenu().getItem(3).setChecked(true);
                fragment = new HistoryFragment();

                break;


            case  R.id.item_logout:

                navigationView.getMenu().getItem(4).setChecked(true);

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

        if (!HomeFragment.searchView.isIconified()){
            HomeFragment.searchView.setIconified(true);
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

    @Override
    public void onClickSach(Sach sach) {
        Intent intent = new Intent(MainActivity.this,ProductAcitivty.class);
        intent.putExtra("SendBookToProduct",sach);
        startActivity(intent);
    }

    public static boolean checkString(String s1, String s2){
        if (s1.equals(s2)){
            return true;
        }
        return false;
    }

    void RemoveColor(NavigationView view){
        for (int i=0; i<view.getMenu().size(); i++){
            MenuItem item = view.getMenu().getItem(i);
            item.setChecked(false);
        }
    }


}