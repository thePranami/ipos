package com.loopin.ipos.Fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loopin.ipos.Model.StockDetailReportResponse

import com.loopin.ipos.R
import com.loopin.ipos.Utils.IposConst
import com.loopin.ipos.Utils.RetrofitController
import kotlinx.android.synthetic.main.activity_stock_report.*
import kotlinx.android.synthetic.main.fragment_stock_detail.*
import kotlinx.android.synthetic.main.fragment_stock_detail.totalBreakage
import kotlinx.android.synthetic.main.fragment_stock_detail.totalExcise
import kotlinx.android.synthetic.main.fragment_stock_detail.totalWsp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlinx.android.synthetic.main.fragment_stock_detail.totalCustom as totalCustom1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class StockDetailFragment : BottomSheetDialogFragment() {

    lateinit var bundle:Bundle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_detail, container, false)
        IposConst.sharedPreferences = Objects.requireNonNull<FragmentActivity>(activity).getSharedPreferences(IposConst.sp_name, MODE_PRIVATE)
        IposConst.editor = IposConst.sharedPreferences.edit()


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateRelative.visibility = View.GONE
        ok.setOnClickListener {
            dismiss()
        }

        bundle = this.arguments!!
        var rName = bundle.getString("report_name")
        var getDate = ""
        var fDate = ""
        var tDate = ""
        var apiDate = ""
        var apiToDate = ""
        var apiFromDate = ""
        var lt = ""
        var lc = ""
        var brand = ""

        // from date
        fDate = bundle.getString("fDate").toString()
        Log.d("date...", fDate)   //  dd/mm/yyyy
        val fd = fDate.substring(0, 2)
        val fm = fDate.substring(3, 5)
        val fy = fDate.substring(6, 10)
        apiFromDate = "$fy/$fm/$fd"
        Log.d("apiFromDate", apiFromDate)

        // to date
        tDate = bundle.getString("tDate").toString()
        Log.d("date...", tDate)   //  dd/mm/yyyy
        val td = tDate.substring(0, 2)
        val tm = tDate.substring(3, 5)
        val ty = tDate.substring(6, 10)
        apiToDate = "$ty/$tm/$td"
        Log.d("apiToDate", apiToDate)

        // type and category

        lt = if (bundle.getString("lt").toString().equals("All", ignoreCase = true)) {
            bundle.getString("lt").toString()
        } else {
            bundle.getString("lt").toString() + "_Liquor"
        }
        lc = bundle.getString("lc").toString()

        reportName.text = rName
        fromDate.text = "From Date: $fDate"
        toDate.text = "ToDate: $tDate"
        setStockDetailReport(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY, "")!!,
                apiFromDate, apiToDate, lt, lc)
    }


    private fun setStockDetailReport(shop_id: String, fromDate: String, toDate:String, liqType: String, liqCat: String) {
        progressLinear.visibility = View.VISIBLE
        cardLinear.visibility = View.GONE
        val controller = RetrofitController(activity)
        val call: Call<StockDetailReportResponse> = controller.getStockDetailReport("STOCK_DETAIL_REPORT", shop_id, fromDate, toDate, liqType, liqCat)
        call.enqueue(object : Callback<StockDetailReportResponse> {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onResponse(call: Call<StockDetailReportResponse>, response: Response<StockDetailReportResponse>) {
                if (response.isSuccessful) {
                    progressLinear.visibility = View.GONE
                    cardLinear.visibility = View.GONE
                    if (response.body()!!.error == "0") {
                        cardLinear.visibility = View.VISIBLE

                        opening.text = response.body()!!.sumOpeningBottles.toString()
                        receiving.text = response.body()!!.totalReceiveBottles.toString()
                        totalOpening.text = response.body()!!.totalOpeningBottles.toString()
                        totalBreakage.text = response.body()!!.totalDamageBottles.toString()
                        totalSale.text = response.body()!!.totalSaleBottles.toString()
                        totalClosing.text = response.body()!!.totalClosingBalance.toString()
                        totalExcise.text=(String.format("%.2f", response.body()!!.totalExcise.toDouble()))
                        totalWsp.text = String.format("%.2f", response.body()!!.totalWsp.toDouble())
                        totalCustom.text = String.format("%.2f", response.body()!!.totalCustom.toDouble())
                        totalAmt.text = String.format("%.2f", response.body()!!.sumTotalAmount.toDouble())
                    } else {
                        Toast.makeText(activity, R.string.no_data, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<StockDetailReportResponse>, t: Throwable) {
                progressLinear.visibility = View.GONE
                cardLinear.visibility = View.GONE
                Toast.makeText(activity, R.string.server_error, Toast.LENGTH_SHORT).show()
            }
        })

    }

}
