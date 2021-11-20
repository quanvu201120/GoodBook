package com.quanvu201120.goodbook.model;

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
import com.quanvu201120.goodbook.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachHolder> implements Filterable {

    ArrayList<Sach> arrayList;
    ArrayList<Sach> arrayListSearch;
    FirebaseStorage firebaseStorage;



    public interface onItemSachClick{
        void onClickSach(Sach sach);
    }
    onItemSachClick onItemSachClick;

    public SachAdapter(ArrayList<Sach> arrayList, SachAdapter.onItemSachClick onItemSachClick) {
        this.arrayList = arrayList;
        this.arrayListSearch = arrayList;
        this.onItemSachClick = onItemSachClick;
    }

    @NonNull
    @Override
    public SachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
        firebaseStorage = FirebaseStorage.getInstance();

        return new SachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachHolder holder, int position) {
        Sach sach = arrayList.get(position);
        holder.textView.setText(sach.getTenSach());

        if (sach.getGiaBan().equals("Miễn phí")){
            holder.textView2.setText(sach.getGiaBan());
        }
        else {
            holder.textView2.setText(EditPrice(sach.getGiaBan()) + " VNĐ");
        }

        holder.itemView.setOnClickListener(v -> {
            onItemSachClick.onClickSach(arrayList.get(position));
        });

        if (!checkImage("null",sach.getHinhAnh())){
            try {
                File image_tmp  = File.createTempFile("tmp","png");
                StorageReference storageReference = firebaseStorage.getReference().child(sach.getHinhAnh());
                storageReference.getFile(image_tmp)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(image_tmp.getPath());
                                holder.imageView.setImageBitmap(bitmap);
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SachHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,textView2;
        public SachHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgAvatar_ItemSach);
            textView = itemView.findViewById(R.id.txtTenSach_ItemSach);
            textView2 = itemView.findViewById(R.id.txtGiaBan_ItemSach);
        }
    }

    Boolean checkImage(String s1, String s2){
        if (s1.equals(s2)){
            return true;
        }
        return false;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String strSearch =  constraint.toString();
                if (strSearch.isEmpty()){
                    arrayList = arrayListSearch;
                }
                else {
                    ArrayList<Sach> arrTmp = new ArrayList<>();
                    for (Sach sach: arrayListSearch){
                        if (sach.getTenSach().toLowerCase().contains(strSearch.toLowerCase())){
                            arrTmp.add(sach);
                        }
                    }
                    arrayList  = arrTmp;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<Sach>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    String EditPrice(String s){
        StringBuilder tmp = new StringBuilder();
        int a=3, b = 0;
        char x = '.';
        for(int i= s.length()-1; i>=0; i--){
            b++;
            tmp.append(s.charAt(i));
            if (b==a && i!=0){
                tmp.append(x);
                b = 0;
            }
        }

        return tmp.reverse().toString();
    }


}
