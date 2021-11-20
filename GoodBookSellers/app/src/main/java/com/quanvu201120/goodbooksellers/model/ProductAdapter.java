package com.quanvu201120.goodbooksellers.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quanvu201120.goodbooksellers.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable {

    ArrayList<Sach> sachArrayList;
    ArrayList<Sach> arrayListSearch;
    FirebaseStorage firebaseStorage;



    public interface mItemProductClick{
        void mClick(Sach sach);
    }

    mItemProductClick mItemProductClick;

    public ProductAdapter(ArrayList<Sach> arrayList, mItemProductClick mItemProductClick) {
        this.sachArrayList = arrayList;
        this.arrayListSearch = arrayList;
        this.mItemProductClick = mItemProductClick;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        firebaseStorage = FirebaseStorage.getInstance();
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);

        return new ProductHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Sach sach = sachArrayList.get(position);

        holder.textView.setText(sach.getTenSach());

        try{

            File file_tmp = File.createTempFile("image_tmp","png");
            StorageReference storageReference = firebaseStorage.getReference().child(sach.getHinhAnh());
            storageReference.getFile(file_tmp)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file_tmp.getPath());
                            holder.imageView.setImageBitmap(bitmap);
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemProductClick.mClick(sachArrayList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return sachArrayList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder  {

        ImageView imageView;
        TextView textView;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgAvatar_ItemProduct);
            textView = itemView.findViewById(R.id.txtName_ItemProduct);

        }


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String str_search = constraint.toString();

                if (str_search.isEmpty()){
                    sachArrayList = arrayListSearch;
                }
                else {

                    ArrayList<Sach> arrayList_tmp  = new ArrayList<>();
                    for (Sach sach:arrayListSearch){

                        if (sach.getTenSach().toLowerCase().contains(str_search.toLowerCase())){
                            arrayList_tmp.add(sach);
                        }

                    }
                    sachArrayList = arrayList_tmp;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = sachArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                sachArrayList = (ArrayList<Sach>) results.values;
                notifyDataSetChanged();

            }
        };
    }

}
