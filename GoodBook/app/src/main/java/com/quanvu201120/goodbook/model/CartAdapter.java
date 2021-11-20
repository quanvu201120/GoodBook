package com.quanvu201120.goodbook.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quanvu201120.goodbook.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    ArrayList<ItemCart> cartArrayList;

    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;

    public CartAdapter(ArrayList<ItemCart> cartArrayList) {
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        CartHolder cartHolder = new CartHolder(convertView);




        return cartHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapter.CartHolder holder, int position) {
        ItemCart itemCart = cartArrayList.get(position);

        holder.txtTen.setText(itemCart.getTenSach());
        holder.txtGia.setText(EditPrice(itemCart.getGiaSach()) + " VNĐ");
        holder.txtSoLuong.setText(itemCart.getSoLuong()+"");
        try {
            File tmp = File.createTempFile("img","png");
            StorageReference storageReference = firebaseStorage.getReference().child(itemCart.getHinhAnh());
            storageReference.getFile(tmp)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(tmp.getPath());
                            holder.imgAvatar.setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView imgAvatar;
        TextView txtTen,txtGia,txtSoLuong;
        ConstraintLayout constraintLayout;

        public CartHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_itemCart);
            txtTen = itemView.findViewById(R.id.txtTen_itemCart);
            txtGia = itemView.findViewById(R.id.txtGia_itemCart);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong_itemCart);
            constraintLayout = itemView.findViewById(R.id.item_context);

            constraintLayout.setOnCreateContextMenuListener(this::onCreateContextMenu);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),00000,0,"Xóa");
        }
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
