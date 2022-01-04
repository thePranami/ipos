package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.BrandWiseModel;
import com.loopin.ipos.R;

import java.util.List;

public class BrandWiseAdapter extends RecyclerView.Adapter<BrandWiseAdapter.BwpHolder> {
    private Context context;
    private List<BrandWiseModel> list;

    public BrandWiseAdapter(Context context, List<BrandWiseModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BrandWiseAdapter.BwpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_wise, parent, false);
        return new BrandWiseAdapter.BwpHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandWiseAdapter.BwpHolder holder, int position) {
        BrandWiseModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.supplierName.setText(String.valueOf(model.getSuppName()));
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

    public class BwpHolder extends RecyclerView.ViewHolder{
        TextView srNo, supplierName, brandName, size, receiving, excise, wsp;
        public BwpHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            supplierName = itemView.findViewById(R.id.supplierName);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            excise = itemView.findViewById(R.id.excise);
            wsp = itemView.findViewById(R.id.wsp);
            receiving = itemView.findViewById(R.id.receiving);
        }
    }
}
