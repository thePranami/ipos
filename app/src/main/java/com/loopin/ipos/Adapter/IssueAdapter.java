package com.loopin.ipos.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Model.IssueModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.IposConst;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueHolder> {

    private List<IssueModel> list;

    public IssueAdapter(List<IssueModel> list){
        this.list = list;
    }
    @NonNull
    @Override
    public IssueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_report, parent, false);
        return new IssueHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IssueHolder holder, int position) {
        IssueModel model = list.get(position);
        holder.srNo.setText(String.valueOf(position+1));
        holder.vendId.setText(IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,""));
        holder.shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""));
        holder.brandName.setText(model.getBrandName());
        holder.size.setText(String.valueOf(model.getSizeValue()));
        holder.packSize.setText(String.valueOf(model.getPackSize()));
        holder.caseUnit.setText(model.getCase().toString());
        holder.issueStock.setText(model.getIssueStcok().toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class IssueHolder extends RecyclerView.ViewHolder {
        TextView srNo, brandName, size, packSize, caseUnit, issueStock, vendId, shopName;
        public IssueHolder(View view){
            super(view);
            srNo = view.findViewById(R.id.srNo);
            brandName = view.findViewById(R.id.brandName);
            size = view.findViewById(R.id.size);
            packSize = view.findViewById(R.id.packSize);
            caseUnit = view.findViewById(R.id.caseUnit);
            issueStock = view.findViewById(R.id.issueStock);
            vendId = view.findViewById(R.id.vendId);
            shopName = view.findViewById(R.id.shopName);
        }
    }
}
