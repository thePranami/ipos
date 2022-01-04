package com.loopin.ipos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopin.ipos.Activity.StockQPNActivity;
import com.loopin.ipos.Model.StockQPNModel;
import com.loopin.ipos.R;

import java.util.List;

public class StockQPNAdapter extends RecyclerView.Adapter<StockQPNAdapter.QpnHolder> {

    private List<StockQPNModel> qpnList;

    public StockQPNAdapter(List<StockQPNModel> qpnList){
        this.qpnList = qpnList;
    }

    @NonNull
    @Override
    public QpnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_qpn, parent, false);
        return new QpnHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QpnHolder holder, int position) {
        StockQPNModel model = qpnList.get(position);
        holder.brandName.setText(model.getBrandName());
        holder.OQ.setText(model.getOQ());
        holder.OP.setText(model.getOP());
        holder.ON.setText(model.getON());
        holder.RQ.setText(model.getRQ());
        holder.RP.setText(model.getRP());
        holder.RN.setText(model.getRN());
        holder.SQ.setText(model.getSQ());
        holder.SP.setText(model.getSP());
        holder.SN.setText(model.getSN());
        holder.CQ.setText(model.getCQ());
        holder.CP.setText(model.getCP());
        holder.CN.setText(model.getCN());
        holder.SVQ.setText(model.getSVQ());
        holder.SVP.setText(model.getSVP());
        holder.SVN.setText(model.getSVN());
    }

    @Override
    public int getItemCount() {
        return qpnList.size();
    }

    public class QpnHolder extends RecyclerView.ViewHolder{
        TextView srNo, vendId, shopName, brandName, OQ, OP, ON, RQ, RP, RN, SQ,
                 SP, SN, CQ, CP, CN, SVQ, SVP, SVN;
        public QpnHolder(View itemView){
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            vendId = itemView.findViewById(R.id.vendId);
            shopName = itemView.findViewById(R.id.shopName);
            brandName = itemView.findViewById(R.id.brandName);
            OQ = itemView.findViewById(R.id.OQ);
            OP = itemView.findViewById(R.id.OP);
            ON = itemView.findViewById(R.id.ON);
            RQ = itemView.findViewById(R.id.RQ);
            RP = itemView.findViewById(R.id.RP);
            RN = itemView.findViewById(R.id.RN);
            SQ = itemView.findViewById(R.id.SQ);
            SP = itemView.findViewById(R.id.SP);
            SN = itemView.findViewById(R.id.SN);
            CQ = itemView.findViewById(R.id.CQ);
            CP = itemView.findViewById(R.id.CP);
            CN = itemView.findViewById(R.id.CN);
            SVQ  = itemView.findViewById(R.id.SVQ);
            SVP = itemView.findViewById(R.id.SVP);
            SVN = itemView.findViewById(R.id.SVN);
        }
    }
}
