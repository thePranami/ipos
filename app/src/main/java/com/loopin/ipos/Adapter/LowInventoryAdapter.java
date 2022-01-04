package com.loopin.ipos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.LowInventoryModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.IposConst;

import java.util.List;

public class LowInventoryAdapter extends RecyclerView.Adapter<LowInventoryAdapter.DataHolder> {
    private List<LowInventoryModel> list;

    public LowInventoryAdapter(List<LowInventoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.low_inventory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {

        LowInventoryModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.vendId.setText(IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,""));
        holder.shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        holder.caseUnit.setText(String.valueOf(model.getCase()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        TextView srNo, brandName, size, quantity, caseUnit, vendId, shopName;
        public DataHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            caseUnit = itemView.findViewById(R.id.caseUnit);
            quantity = itemView.findViewById(R.id.quantity);
            vendId = itemView.findViewById(R.id.vendId);
            shopName = itemView.findViewById(R.id.shopName);
        }
    }
}
