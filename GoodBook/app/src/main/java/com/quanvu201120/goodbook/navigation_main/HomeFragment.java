package com.quanvu201120.goodbook.navigation_main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.quanvu201120.goodbook.R;
import com.quanvu201120.goodbook.model.Sach;
import com.quanvu201120.goodbook.model.SachAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    //BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    ArrayList<Sach> arrayList_HomeFragment;
    SachAdapter sachAdapter;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> keysDocument;
    public static SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //

//        bottomNavigationView = view.findViewById(R.id.bottomNavigation_home);
//
//        showFragmentBottom(R.id.item_ban);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                showFragmentBottom(item.getItemId());
//
//                return true;
//            }
//        });

        recyclerView = view.findViewById(R.id.recycleView_HomeFragment);
        arrayList_HomeFragment = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        keysDocument = new ArrayList<>();

//        Sach sach = new Sach("ID","Tasdfs","NOTFree","Tasdfs","Tasdfs","Tasdfs","Tasdfs","Tasdfs","Tasdfs","Tasdfs","23123","asf");
//        arrayList_HomeFragment.add(sach);

        sachAdapter = new SachAdapter(arrayList_HomeFragment, (SachAdapter.onItemSachClick) getContext());
        sachAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sachAdapter);
        getDataRealTimeFireStore();



        setHasOptionsMenu(true);

        //
        return view;
    }

//    private void showFragmentBottom(@NonNull int item) {
//        Fragment fragment = null;
//
//        switch (item){
//            case R.id.item_ban:
//                fragment = new BanFragment();
//                break;
//            case R.id.item_tang:
//                fragment = new TangFragment();
//                break;
//        }
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout_home,fragment);
//        fragmentTransaction.commit();
//    }

    void getDataRealTimeFireStore() {
        firebaseFirestore.collection("Product").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    DocumentSnapshot documentSnapshot = documentChange.getDocument();


                        switch (documentChange.getType()){
                            case ADDED:
                                keysDocument.add(documentSnapshot.getId());
                                arrayList_HomeFragment.add(documentSnapshot.toObject(Sach.class));
                                sachAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                if(keysDocument.indexOf(documentSnapshot.getId()) >= 0){
                                    arrayList_HomeFragment.set(keysDocument.indexOf(documentSnapshot.getId()), documentSnapshot.toObject(Sach.class));
                                }
                                sachAdapter.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                if(keysDocument.indexOf(documentSnapshot.getId()) >= 0){
                                    arrayList_HomeFragment.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                    keysDocument.remove(keysDocument.indexOf(documentSnapshot.getId()));
                                }
                                sachAdapter.notifyDataSetChanged();
                                break;
                        }


                }
            }
        });
    }


    boolean checkFree(String s1, String s2){
        if (s1.equals(s2)){
            return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sachAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sachAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


}