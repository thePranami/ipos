package com.loopin.ipos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.BreakageModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.IposConst;

import java.util.List;

public class BreakageAdapter extends RecyclerView.Adapter<BreakageAdapter.BreakageHolder> {

    private Context context;
    private List<BreakageModel> list;

    public BreakageAdapter(Context context, List<BreakageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BreakageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.breakage_report, parent, false);
        return new BreakageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakageHolder holder, int position) {

        BreakageModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.vendId.setText(IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,""));
        holder.shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.breakage.setText(String.valueOf(model.getBrakage()));
        holder.excise.setText(String.valueOf(model.getExcisePrice()));
        holder.mrp.setText(String.valueOf(model.getMrp()));
        holder.wsp.setText(String.valueOf(model.getWsp()));
        holder.total.setText(String.valueOf(model.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BreakageHolder extends RecyclerView.ViewHolder{
        TextView srNo, brandName,size, breakage, mrp, excise, wsp, total, vendId, shopName;
        public BreakageHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            brandName = itemView.findViewById(R.id.brandName);
            size = itemView.findViewById(R.id.size);
            excise= itemView.findViewById(R.id.excise);
            breakage = itemView.findViewById(R.id.breakage);
            wsp = itemView.findViewById(R.id.wsp);
            mrp = itemView.findViewById(R.id.mrp);
            total = itemView.findViewById(R.id.total);
            vendId = itemView.findViewById(R.id.vendId);
            shopName = itemView.findViewById(R.id.shopName);
        }
    }
}
