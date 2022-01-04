package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.SaleDetailReportModel;
import com.loopin.ipos.R;

import java.util.List;

public class SaleDetailReportAdapter extends RecyclerView.Adapter<SaleDetailReportAdapter.MyHolder> {
    private Context context;
    private List<SaleDetailReportModel> list;

    public SaleDetailReportAdapter(Context context, List<SaleDetailReportModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_detail_report, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        SaleDetailReportModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.sale.setText(String.valueOf(model.getQuantity()));
        holder.mrp.setText(model.getMrp());
        holder.excise.setText(model.getExcisePrice());
        holder.wsp.setText(model.getWsp());
        holder.total.setText(String.valueOf(model.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView srNo, brandName, size, sale, mrp, excise, wsp, total;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            mrp = itemView.findViewById(R.id.mrp);
            excise = itemView.findViewById(R.id.excise);
            wsp = itemView.findViewById(R.id.wsp);
            sale = itemView.findViewById(R.id.sale);
            total = itemView.findViewById(R.id.total);
        }
    }
}
