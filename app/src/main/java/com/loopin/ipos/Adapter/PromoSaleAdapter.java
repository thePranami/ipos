package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.PromoSaleModel;
import com.loopin.ipos.R;

import java.util.List;

public class PromoSaleAdapter extends RecyclerView.Adapter<PromoSaleAdapter.MyHolder> {

    Context context;
    List<PromoSaleModel> list;

    public PromoSaleAdapter(Context context, List<PromoSaleModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        PromoSaleModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.shopName.setText(model.getShopName());
        holder.preDaySale.setText(model.getLastDaySale());
        holder.totalAmount.setText(model.getTotalSale());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView srNo, shopName, brandName, preDaySale, totalAmount ;
        public MyHolder(View view){
            super(view);
            srNo = view.findViewById(R.id.srNo);
            shopName = view.findViewById(R.id.shopName);
            preDaySale = view.findViewById(R.id.preDaySale);
            totalAmount = view.findViewById(R.id.totalAmount);
        }
    }
}
