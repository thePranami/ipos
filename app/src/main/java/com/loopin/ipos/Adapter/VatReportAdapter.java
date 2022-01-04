package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.VatReportModel;
import com.loopin.ipos.R;

import java.util.List;

public class VatReportAdapter extends RecyclerView.Adapter<VatReportAdapter.VatHolder> {
    private Context context;
    private List<VatReportModel> list;

    public VatReportAdapter(Context context, List<VatReportModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vat_report, parent, false);
        return new VatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VatHolder holder, int position) {
        VatReportModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.invDate.setText(model.getInvDate());
        holder.liqType.setText(model.getLiquorType());
        holder.grossSale.setText(model.getGrossSale());
        holder.vat.setText(model.getVat());
        holder.total.setText(model.getTotSale());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VatHolder extends RecyclerView.ViewHolder{
        TextView srNo, invDate, liqType, grossSale, vat, total;
        public VatHolder(@NonNull View view){
            super(view);
            srNo = view.findViewById(R.id.srNo);
            invDate = view.findViewById(R.id.invoiceDate);
            liqType = view.findViewById(R.id.liquorType);
            grossSale = view.findViewById(R.id.grossSale);
            vat = view.findViewById(R.id.vat);
            total = view.findViewById(R.id.total);
        }
    }
}
