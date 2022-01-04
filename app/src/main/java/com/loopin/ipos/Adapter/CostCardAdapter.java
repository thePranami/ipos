package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.CostCardModel;
import com.loopin.ipos.R;

import java.util.List;

public class CostCardAdapter extends RecyclerView.Adapter<CostCardAdapter.CostHolder> {
    private Context context;
    private List<CostCardModel> list;

    public CostCardAdapter(Context context, List<CostCardModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cost_card, parent, false);
        CostHolder costHolder = new CostHolder(view);
        return costHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CostHolder holder, int position) {
        CostCardModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.closingBalance.setText(String.valueOf(model.getTotal()));
        holder.excise.setText(String.format("%.2f",model.getExcisePrice()));
        holder.wsp.setText(String.format("%.2f",model.getWsp()));
        holder.custom.setText(String.valueOf(model.getCustomDuty()));
        holder.total.setText(String.valueOf(model.getCostPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CostHolder extends RecyclerView.ViewHolder {
        TextView srNo, brandName, size, closingBalance, excise, wsp, custom, total;
        public CostHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            closingBalance = itemView.findViewById(R.id.closingBalance);
            excise = itemView.findViewById(R.id.excise);
            wsp = itemView.findViewById(R.id.wsp);
            custom = itemView.findViewById(R.id.custom);
            total = itemView.findViewById(R.id.total);
        }
    }
}
