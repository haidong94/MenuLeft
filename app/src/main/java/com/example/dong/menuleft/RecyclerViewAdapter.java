package com.example.dong.menuleft;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dong.menuleft.common.Commons;
import com.example.dong.menuleft.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DONG on 07-Feb-17.
 */
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    public List<Product> listData = new ArrayList<Product>();

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private Context context;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loading;
    private int visibleThreshold = 5;

    public RecyclerViewAdapter(final Context context, List<Product> listData, RecyclerView recyclerView) {
        this.context=context;
        this.listData = listData;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = gridLayoutManager.getItemCount();
                            lastVisibleItem = gridLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }



                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    public Product getItem(int position){
        return listData.get(position);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                      final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//        View itemView = inflater.inflate(R.layout.product_item_layout, viewGroup, false);
//        return new RecyclerViewHolder(itemView);

        if(position==VIEW_ITEM){
            View itemView = inflater.inflate(R.layout.product_item_layout, viewGroup, false);
            return new RecyclerViewHolder(itemView);
        }else{
            View itemView = inflater.inflate(R.layout.layout_loading_item, viewGroup, false);
            return new ProgressViewHolder (itemView);
        }
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof RecyclerViewHolder) {
            ((RecyclerViewHolder)viewHolder).ten.setText(listData.get(position).getName());
            ((RecyclerViewHolder)viewHolder).gia.setText(Commons.formatNumber(listData.get(position).getPrice()) + " đ");

            Glide.with(context)
                    .load(listData.get(position).getImage())
                    .override(200, 200)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((RecyclerViewHolder)viewHolder).hinhanh);
        }else {
            ((ProgressViewHolder ) viewHolder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView ten, gia;
        public ImageView hinhanh;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ten = (TextView) itemView.findViewById(R.id.txtTen);
            gia = (TextView) itemView.findViewById(R.id.txtGia);
            hinhanh = (ImageView) itemView.findViewById(R.id.imageSP);
        }

        @Override
        public void onClick(View v) {
            int id=getItem(getPosition()).getId();
            Intent intent=new Intent(v.getContext(),DetailProductActivity.class);
            intent.putExtra("id",id);
            v.getContext().startActivity(intent);

        }
    }


    interface OnLoadMoreListener{
        void onLoadMore();
    }


    public static class ProgressViewHolder  extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder (View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

}

