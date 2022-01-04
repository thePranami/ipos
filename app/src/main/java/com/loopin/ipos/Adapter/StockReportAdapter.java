package com.loopin.ipos.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.StockReportModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.IposConst;

import java.util.List;

public class StockReportAdapter extends RecyclerView.Adapter<StockReportAdapter.StockHolder> {

    List<StockReportModel> list;

    public StockReportAdapter(List<StockReportModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StockHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        StockReportModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.vendId.setText(IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,""));
        holder.shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.storeClosing.setText(String.valueOf(model.getStoreOpening()));
        holder.counterClosing.setText(String.valueOf(model.getCounterOpening()));
        holder.breakage.setText(String.valueOf(model.getBreakage()));
        Log.d("testBr", String.valueOf(model.getBreakage()));
        holder.stock.setText(String.valueOf(model.getClosingBalance()));
        Log.d("testBrc", String.valueOf(model.getClosingBalance()));
        holder.excise.setText(String.valueOf(model.getExcisePrice()));
        holder.wsp.setText(String.valueOf(model.getWsp()));
        holder.custom.setText(String.valueOf(model.getCustomDuty()));
        holder.cost.setText("6746.45");
        holder.mrp.setText(String.valueOf(model.getMrp()));
        holder.total.setText(String.valueOf(model.getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StockHolder extends RecyclerView.ViewHolder{
        TextView srNo, vendId, shopName, brandName, size, storeClosing, counterClosing, breakage,
                 stock, excise, wsp, custom, cost, mrp, total;
        public StockHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            vendId = itemView.findViewById(R.id.vendId);
            shopName = itemView.findViewById(R.id.shopName);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            storeClosing = itemView.findViewById(R.id.storeClosing);
            counterClosing = itemView.findViewById(R.id.counterClosing);
            breakage = itemView.findViewById(R.id.breakage);
            stock = itemView.findViewById(R.id.stock);
            excise = itemView.findViewById(R.id.excise);
            wsp = itemView.findViewById(R.id.wsp);
            custom = itemView.findViewById(R.id.custom);
            cost = itemView.findViewById(R.id.cost);
            mrp = itemView.findViewById(R.id.mrp);
            total = itemView.findViewById(R.id.total);
        }
    }
}
