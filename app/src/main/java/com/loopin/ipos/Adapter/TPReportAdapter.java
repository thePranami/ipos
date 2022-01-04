package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.TPReportModel;
import com.loopin.ipos.R;

import java.util.List;

public class TPReportAdapter extends RecyclerView.Adapter<TPReportAdapter.TPHolder> {
    private Context context;
    private List<TPReportModel> list;

    public TPReportAdapter(Context context, List<TPReportModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TPHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tp_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TPHolder holder, int position) {
        TPReportModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.invoice.setText(model.getInvoiceNo());
        holder.tpNumber.setText(model.getConsignmentNo());
        holder.receivingDate.setText(model.getReceiveDate());
        holder.supplierName.setText(model.getSupName());
        holder.receiving.setText(String.valueOf(model.getTotalReceived()));
        holder.excise.setText(model.getExcise());
        holder.wsp.setText(model.getWsp());
        holder.custom.setText(model.getCustom());
        holder.vat.setText(model.getVat());
        holder.tcs.setText(model.getTcs());
        holder.total.setText(model.getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TPHolder extends RecyclerView.ViewHolder{
        TextView srNo, invoice, tpNumber, receivingDate, supplierName,
                receiving, excise, wsp, custom, vat, tcs, total;
        public TPHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            invoice = itemView.findViewById(R.id.invoice);
            tpNumber = itemView.findViewById(R.id.tpNumber);
            receiving = itemView.findViewById(R.id.receiving);
            receivingDate = itemView.findViewById(R.id.receivingDate);
            supplierName = itemView.findViewById(R.id.supplierName);
            excise = itemView.findViewById(R.id.excise);
            wsp = itemView.findViewById(R.id.wsp);
            custom = itemView.findViewById(R.id.custom);
            tcs = itemView.findViewById(R.id.tcs);
            vat = itemView.findViewById(R.id.vat);
            total = itemView.findViewById(R.id.total);
        }
    }
}
