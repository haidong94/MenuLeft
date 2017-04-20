package com.example.dong.menuleft;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dong.menuleft.common.Commons;
import com.example.dong.menuleft.model.DetailOrders;

import java.util.List;

/**
 * Created by DONG on 13-Feb-17.
 */

public class RecyclerViewAdapterCart extends
        RecyclerView.Adapter<RecyclerViewAdapterCart.RecyclerViewHolder_Cart> {

    private List<DetailOrders> listData;
    private Context context;

    public RecyclerViewAdapterCart(Context context, List<DetailOrders> listData) {
        this.context=context;
        this.listData = listData;
    }



    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public RecyclerViewHolder_Cart onCreateViewHolder(ViewGroup viewGroup,
                                                 int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.product_item_cart_layout, viewGroup, false);
        return new RecyclerViewHolder_Cart(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(RecyclerViewHolder_Cart viewHolder, int position) {
        viewHolder.ten.setText(""+listData.get(position).getProduct().getName());
        viewHolder.gia.setText(Commons.formatNumber(listData.get(position).getProduct().getPrice())+"*"+listData.get(position).getNumber());
        viewHolder.tongTien.setText(Commons.formatNumber(listData.get(position).getProduct().getPrice()*listData.get(position).getNumber())+" đ");


        Glide.with(context)
                .load(listData.get(position).getProduct().getImage())
                .override(200, 200)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.hinhanh);
    }


    public class RecyclerViewHolder_Cart extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ten,gia,tongTien;
        public ImageView hinhanh;

        public RecyclerViewHolder_Cart(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ten = (TextView) itemView.findViewById(R.id.txtTen);
            gia=(TextView) itemView.findViewById(R.id.txtGia);
            hinhanh = (ImageView) itemView.findViewById(R.id.imageSP);
            tongTien=(TextView) itemView.findViewById(R.id.txtTongTien);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        }
    }

}

