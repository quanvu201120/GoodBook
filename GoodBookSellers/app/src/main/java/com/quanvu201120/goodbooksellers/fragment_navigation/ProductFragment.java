package com.quanvu201120.goodbooksellers.fragment_navigation;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.quanvu201120.goodbooksellers.CreateBookActivity;
import com.quanvu201120.goodbooksellers.LoadingActivity;
import com.quanvu201120.goodbooksellers.R;
import com.quanvu201120.goodbooksellers.UpdateActivity;
import com.quanvu201120.goodbooksellers.model.ProductAdapter;
import com.quanvu201120.goodbooksellers.model.Sach;

import java.util.ArrayList;


public class ProductFragment extends Fragment implements ProductAdapter.mItemProductClick{

    RecyclerView recyclerView;
    ArrayList<Sach> sachArrayList;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    ArrayList<String> keysDocument;
    ProductAdapter productAdapter;


    public static SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);


        recyclerView = view.findViewById(R.id.recyclerView_Product);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        sachArrayList = new ArrayList<>();

        keysDocument = new ArrayList<>();

        productAdapter = new ProductAdapter(sachArrayList, this );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);

        Log.e("ABc product", LoadingActivity.mUser.getUs_Product() + "");

        setHasOptionsMenu(true);
        getDataRealTimeFireStore();

        return view;
    }


    void getDataRealTimeFireStore() {
        firebaseFirestore.collection("Product").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    DocumentSnapshot documentSnapshot = documentChange.getDocument();

                    if ( LoadingActivity.mUser.getUs_Product() != null && LoadingActivity.mUser.getUs_Product().indexOf(documentSnapshot.getId()) >= 0){
                        switch (documentChange.getType()){
                            case ADDED:
                                keysDocument.add(documentSnapshot.getId());
                                sachArrayList.add(documentSnapshot.toObject(Sach.class));
                                productAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                if(keysDocument.indexOf(documentSnapshot.getId()) >= 0){
                                    sachArrayList.set(keysDocument.indexOf(documentSnapshot.getId()), documentSnapshot.toObject(Sach.class));
                                }
                                productAdapter.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                if(keysDocument.indexOf(documentSnapshot.getId()) >= 0){
                                    sachArrayList.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                    keysDocument.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                }
                                productAdapter.notifyDataSetChanged();
                                break;
                        }

                    }
                }
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu_product,menu);

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_add_product){

            if (LoadingActivity.mUser.getU_StoreName().equals("default_null")){

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_kiem_tra_thong_tin);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();

            }
            else {
                Intent intent = new Intent(getContext(), CreateBookActivity.class);
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void mClick(Sach sach) {
        Intent intent = new Intent(getContext(), UpdateActivity.class);
        intent.putExtra("ProductUpdate",sach);
        startActivity(intent);
        getActivity().finish();
    }
}