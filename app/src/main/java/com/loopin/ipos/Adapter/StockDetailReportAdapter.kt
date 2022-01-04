package com.loopin.ipos.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loopin.ipos.Model.StockDetailReportModel
import com.loopin.ipos.R
import com.loopin.ipos.Utils.IposConst

class StockDetailReportAdapter(var list: MutableList<StockDetailReportModel>) : RecyclerView.Adapter<StockDetailReportAdapter.StockHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_detail_report, parent, false)
        return StockHolder(view)

    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: StockHolder, position: Int) {

        val model: StockDetailReportModel = list[position]

        try {
            holder.srNo.text = (position+1).toString()
            holder.vendId.text = IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,"")
            holder.shopName.text = IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,"")
            holder.brandName.text = model.brandName
            holder.size.text = model.sizeValue.toString()
            holder.storeOpening.text = model.storeOpening.toString()
            holder.counterOpening.text = model.counterOpening.toString()
            holder.opening.text = model.openingBottles.toString()
            holder.receiving.text = model.receiveBottles.toString()
            holder.totalOpening.text = model.totalOpening.toString()
            holder.breakage.text = model.damageBottles.toString()
            holder.sale.text = model.saleBottles.toString()
            holder.storeClosing.text = model.storeClosing.toString()
            holder.counterClosing.text = model.counterClosing.toString()
            holder.closingBalance.text = model.closingBalance.toString()
            holder.excise.text = model.excisePrice.toString()
            holder.wsp.text = model.wsp.toString()
            holder.mrp.text = model.mrp.toString()
            holder.total.text = model.totalAmount.toString()
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("vnhhhhh..", e.printStackTrace().toString())
        }
    }

    class StockHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var srNo:TextView =itemView.findViewById(R.id.srNo)
        var vendId:TextView = itemView.findViewById(R.id.vendId)
        var shopName:TextView = itemView.findViewById(R.id.shopName)
        var brandName:TextView = itemView.findViewById(R.id.brandName)
        var size:TextView =itemView.findViewById(R.id.size)
        var storeOpening:TextView = itemView.findViewById(R.id.storeOpening)
        var counterOpening:TextView =itemView.findViewById(R.id.counterOpening)
        var opening:TextView = itemView.findViewById(R.id.opening)
        var receiving:TextView =itemView.findViewById(R.id.receiving)
        var totalOpening:TextView = itemView.findViewById(R.id.totalOpening)
        var breakage:TextView =itemView.findViewById(R.id.breakage)
        var sale:TextView = itemView.findViewById(R.id.sale)
        var storeClosing:TextView = itemView.findViewById(R.id.storeClosing)
        var counterClosing:TextView = itemView.findViewById(R.id.counterClosing)
        var closingBalance:TextView =itemView.findViewById(R.id.closingBalance)
        var excise:TextView = itemView.findViewById(R.id.excise)
        var wsp:TextView =itemView.findViewById(R.id.wsp)
        var mrp:TextView = itemView.findViewById(R.id.mrp)
        var total:TextView = itemView.findViewById(R.id.total)
    }
}