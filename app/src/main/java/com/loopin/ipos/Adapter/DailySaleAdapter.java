package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.DailySaleModel;
import com.loopin.ipos.R;

import java.util.List;

public class DailySaleAdapter extends RecyclerView.Adapter<DailySaleAdapter.MyHolder> {

    private Context context;
    private List<DailySaleModel> list;

    public DailySaleAdapter(Context context, List<DailySaleModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_sale, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DailySaleModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        holder.mrp.setText(String.valueOf(model.getMrp()));
        holder.total.setText(String.valueOf(model.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView srNo, brandName,size, quantity, mrp, total;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            mrp = itemView.findViewById(R.id.mrp);
            quantity = itemView.findViewById(R.id.quantity);
            total = itemView.findViewById(R.id.total);
        }
    }
}
