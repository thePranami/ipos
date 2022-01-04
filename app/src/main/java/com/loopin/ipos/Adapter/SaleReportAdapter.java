package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.DailySaleModel;
import com.loopin.ipos.Model.TPReportModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.IposConst;

import java.util.List;

public class SaleReportAdapter extends RecyclerView.Adapter<SaleReportAdapter.MyHolder> {

    private Context context;
    private List<DailySaleModel> list;

    public SaleReportAdapter(Context context, List<DailySaleModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_report, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DailySaleModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.vendId.setText(IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,""));
        holder.shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.sale.setText(String.valueOf(model.getQuantity()));
        holder.mrp.setText(String.valueOf(model.getMrp()));
        holder.total.setText(String.valueOf(model.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView srNo, brandName, size, sale, mrp, total, vendId, shopName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            mrp = itemView.findViewById(R.id.mrp);
            sale = itemView.findViewById(R.id.sale);
            total = itemView.findViewById(R.id.total);
            vendId = itemView.findViewById(R.id.vendId);
            shopName = itemView.findViewById(R.id.shopName);
        }
    }
}
