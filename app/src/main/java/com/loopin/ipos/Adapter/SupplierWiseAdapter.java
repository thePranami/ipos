package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.SupplierWiseModel;
import com.loopin.ipos.R;

import java.util.List;

public class SupplierWiseAdapter extends RecyclerView.Adapter<SupplierWiseAdapter.SwbHolder> {

    private Context context;
    private List<SupplierWiseModel> list;

    public SupplierWiseAdapter(Context context, List<SupplierWiseModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SwbHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supp_wise_brand, parent, false);
        return new SwbHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwbHolder holder, int position) {
        SupplierWiseModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.receiveDate.setText(String.valueOf(model.getReceiveDate()));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.receiving.setText(String.valueOf(model.getTotal()));
        holder.excise.setText(model.getExcisePrice());
        holder.wsp.setText(model.getWsp());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SwbHolder extends RecyclerView.ViewHolder{
        TextView srNo, receiveDate, brandName, size, receiving, excise, wsp;
        public SwbHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            receiveDate = itemView.findViewById(R.id.receiveDate);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            excise = itemView.findViewById(R.id.excise);
            wsp = itemView.findViewById(R.id.wsp);
            receiving = itemView.findViewById(R.id.receiving);
        }
    }
}
