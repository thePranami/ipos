package com.loopin.ipos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.BreakageSaleModel;
import com.loopin.ipos.R;

import java.util.List;

public class BreakageSaleAdapter extends RecyclerView.Adapter<BreakageSaleAdapter.BreakageSaleHolder> {

    List<BreakageSaleModel> list;

    public void setBreakageSale(List<BreakageSaleModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public BreakageSaleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.breakage_sale_report, parent, false);
        return new BreakageSaleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakageSaleHolder holder, int position) {
        BreakageSaleModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.breakageSale.setText(String.valueOf(model.getBrakageSale()));
        holder.excise.setText(model.getExcisePrice());
        holder.mrp.setText(model.getMrp());
        holder.wsp.setText(model.getWsp());
        holder.total.setText(String.valueOf(model.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BreakageSaleHolder extends RecyclerView.ViewHolder{
        TextView srNo, brandName,size, breakageSale, mrp, excise, wsp, total;
         BreakageSaleHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            excise= itemView.findViewById(R.id.excise);
            breakageSale = itemView.findViewById(R.id.breakageSale);
            wsp = itemView.findViewById(R.id.wsp);
            mrp = itemView.findViewById(R.id.mrp);
            total = itemView.findViewById(R.id.total);
        }
    }
}
