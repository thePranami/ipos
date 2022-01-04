package com.loopin.ipos.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import com.loopin.ipos.Adapter.StockDetailReportAdapter
import com.loopin.ipos.Model.StockDetailReportModel
import com.loopin.ipos.Model.StockDetailReportResponse
import com.loopin.ipos.Model.StockReportModel
import com.loopin.ipos.R
import com.loopin.ipos.Utils.*
import com.loopin.ipos.Utils.IposConst.lc
import com.loopin.ipos.Utils.IposConst.lt
import kotlinx.android.synthetic.main.activity_stock_detail_report.*
import kotlinx.android.synthetic.main.activity_stock_detail_report.backFromSR
import kotlinx.android.synthetic.main.activity_stock_detail_report.totalBreakage
import kotlinx.android.synthetic.main.activity_stock_detail_report.totalExcise
import kotlinx.android.synthetic.main.activity_stock_detail_report.totalWsp
import kotlinx.android.synthetic.main.activity_stock_report.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_stock_detail_report.totalAmount as totalAmount1

class StockDetailReportActivity : AppCompatActivity() {

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_detail_report)

        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, Context.MODE_PRIVATE)
        IposConst.editor = IposConst.sharedPreferences.edit()

        Companion.opening = findViewById(R.id.opening)
        Companion.storeOpening = findViewById(R.id.storeOpening)
        Companion.counterOpening = findViewById(R.id.counterOpening)
        Companion.totalOpening = findViewById(R.id.totalOpening)
        Companion.totalReceiving = findViewById(R.id.totalReceiving)
        Companion.totalBreakage = findViewById(R.id.totalBreakage)
        Companion.totalSale = findViewById(R.id.totalSale)
        Companion.storeClosing = findViewById(R.id.storeClosing)
        Companion.counterClosing = findViewById(R.id.counterClosing)
        Companion.totalAmount = findViewById(R.id.totalAmount)
        Companion.totalWsp = findViewById(R.id.totalWsp)
        Companion.totalExcise = findViewById(R.id.totalExcise)
        Companion.closingBalance = findViewById(R.id.closingBalance)

        Companion.noDataView = findViewById<LinearLayout>(R.id.noDataView)
        Companion.downloadPdf = findViewById<ImageView>(R.id.download)

        var apiToDate = ""
        var apiFromDate = ""

        // from date
        fDate = intent.getStringExtra("fDate").toString()
        Log.d("date...", fDate)   //  dd/mm/yyyy
        val fd = fDate.substring(0, 2)
        val fm = fDate.substring(3, 5)
        val fy = fDate.substring(6, 10)
        apiFromDate = "$fy/$fm/$fd"
        Log.d("apiFromDate", apiFromDate)

        // to date
        tDate = intent.getStringExtra("tDate")!!
        Log.d("date...", tDate)   //  dd/mm/yyyy
        val td = tDate.substring(0, 2)
        val tm = tDate.substring(3, 5)
        val ty = tDate.substring(6, 10)
        apiToDate = "$ty/$tm/$td"
        Log.d("apiToDate", apiToDate)

        // type and category
        lt = if (intent.getStringExtra("lt").equals("All", ignoreCase = true)) {
            intent.getStringExtra("lt")
        } else {
            intent.getStringExtra("lt") + "_Liquor"
        }
        lc = intent.getStringExtra("lc").toString()

        setStockDetailReport(this, IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY, "")!!,
                apiFromDate, apiToDate, lt, lc)

        backFromSR.setOnClickListener {
            finish()
        }

        downloadPdf.setOnClickListener {
            if (ExtraUtils.checkStoragePermissions(this)){
                PdfCreateTask(this).execute("Stock Detail Report")
            }else{
                ExtraUtils.checkStoragePermissions(this)
            }
        }

    }

    companion object {
        lateinit var fDate: String
        lateinit var tDate: String
        var list: MutableList<StockDetailReportModel> = mutableListOf()
        @SuppressLint("StaticFieldLeak")

        lateinit var storeOpening: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var counterOpening: TextView
        lateinit var opening: TextView
        lateinit var totalOpening: TextView
        lateinit var totalReceiving: TextView
        lateinit var totalBreakage: TextView
        lateinit var totalSale: TextView
        lateinit var storeClosing: TextView
        lateinit var counterClosing: TextView
        lateinit var totalAmount: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var closingBalance: TextView
        lateinit var totalExcise: TextView
        lateinit var totalWsp: TextView

        lateinit var downloadPdf: ImageView
        lateinit var noDataView: LinearLayout

        private fun setStockDetailReport(context: Activity, shop_id: String, fromDate: String, toDate: String, liqType: String, liqCat: String) {
            IposProgress.progressDialog(context).setMessage("Data is loading...")
            list.clear()
            val controller = RetrofitController(context)
            val call: Call<StockDetailReportResponse> = controller.getStockDetailReport("STOCK_DETAIL_REPORT", shop_id, fromDate, toDate, liqType, liqCat)
            call.enqueue(object : Callback<StockDetailReportResponse> {
                @SuppressLint("SetTextI18n", "DefaultLocale")
                override fun onResponse(call: Call<StockDetailReportResponse>, response: Response<StockDetailReportResponse>) {
                    if (response.isSuccessful) {
                        IposProgress.stop()
                        if (response.body()!!.error == "0") {
                            for (i in 0 until response.body()!!.data.size) {
                                list.add(StockDetailReportModel(response.body()!!.data[i].brandName,
                                        response.body()!!.data[i].sizeValue,
                                        response.body()!!.data[i].storeOpening,
                                        response.body()!!.data[i].counterOpening,
                                        response.body()!!.data[i].openingBottles,
                                        response.body()!!.data[i].receiveBottles,

                                        response.body()!!.data[i].totalOpening,
                                        response.body()!!.data[i].damageBottles,
                                        response.body()!!.data[i].saleBottles,
                                        response.body()!!.data[i].storeClosing,

                                        response.body()!!.data[i].counterClosing,
                                        response.body()!!.data[i].closingBalance,
                                        response.body()!!.data[i].excisePrice,
                                        response.body()!!.data[i].wsp,
                                        response.body()!!.data[i].customDuty,
                                        response.body()!!.data[i].mrp,
                                        response.body()!!.data[i].totalAmount))
                            }
                            val adapter = StockDetailReportAdapter(list)
                            val manager = LinearLayoutManager(context)
                            val sdrRecycler = context.findViewById<RecyclerView>(R.id.sdrRecycler)
                            sdrRecycler.layoutManager = manager
                            sdrRecycler.adapter = adapter

                            storeOpening.text = response.body()!!.totalStoreOpening.toString()
                            counterOpening.text = response.body()!!.totalCounterOpening.toString()
                            opening.text = response.body()!!.sumOpeningBottles.toString()
                            totalReceiving.text = response.body()!!.totalReceiveBottles.toString()
                            totalOpening.text = response.body()!!.totalOpeningBottles.toString()
                            totalBreakage.text = response.body()!!.totalDamageBottles.toString()
                            totalSale.text = response.body()!!.totalSaleBottles.toString()
                            storeClosing.text = response.body()!!.totalStoreClosing.toString()
                            counterClosing.text = response.body()!!.totalCounterClosing.toString()
                            closingBalance.text = response.body()!!.totalClosingBalance.toString()
                            totalExcise.text = (String.format("%.2f", response.body()!!.totalExcise.toDouble()))
                            totalWsp.text = String.format("%.2f", response.body()!!.totalWsp.toDouble())
                            //totalCustom.text = String.format("%.2f", response.body()!!.totalCustom.toDouble())
                            totalAmount.text = String.format("%.2f", response.body()!!.sumTotalAmount.toDouble())
                        } else {
                            noDataView.visibility = View.VISIBLE
                            downloadPdf.visibility = View.GONE
                            Toast.makeText(context, R.string.no_data, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<StockDetailReportResponse>, t: Throwable) {
                    IposProgress.stop()
                    downloadPdf.visibility = View.GONE
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show()
                }
            })
        }


        // create pdf

        fun stockDetailReportContent(document: Document) {
            val title = Paragraph(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME, "")
                    + "\n" + IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_ADDRESS, "")
                    + "-" + IposConst.sharedPreferences.getString(IposConst.Keys.PIN_CODE, ""),
                    IposConst.titleFont)
            title.alignment = Element.ALIGN_CENTER
            document.add(title)
            val ls = LineSeparator()
            ls.lineWidth = 2f
            document.add(Chunk(ls))
            document.add(Paragraph("\n"))

            val glue = Chunk(VerticalPositionMark())
            val datePara = Paragraph("Stock Detail Report", IposConst.smallBold)
            datePara.add(Chunk(glue))
            datePara.add("From Date: $fDate       To Date: $tDate")
            document.add(datePara)

            val subPara = Paragraph("\n", IposConst.smallBold)
            stockDetailReportTable(subPara)
            document.add(subPara)

            val paragraph = Paragraph()
            ExtraUtils.emptyLine(paragraph, 5)
        }

        @Throws(DocumentException::class)
        fun stockDetailReportTable(paragraph: Paragraph) {
            val table = PdfPTable(17)
            table.widthPercentage = 100f

            val columnWidths = floatArrayOf(7f, 15f, 7f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 10f, 10f, 10f, 10f)
            table.setWidths(columnWidths)

            var c1 = PdfPCell(Phrase("SNo.", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Brand", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Size", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Store Opening", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Counter Opening", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Opening", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Total Receiving", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Total Opening", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Total Breakage", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Total Sale", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Store Closing", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Counter Closing", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Closing Balance", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Excise", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("WSP", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("MRP", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Total", IposConst.miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            table.headerRows = 1

            for (i in list.indices) {

                val model = list[i]

                table.addCell((i + 1).toString())
                //table.addCell(model.brandName)
                table.addCell(PdfPCell(Phrase(model.brandName.toString(), IposConst.miniFont5)))
                table.addCell(PdfPCell(Phrase(model.sizeValue.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.storeOpening.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.counterOpening.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.openingBottles.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.receiveBottles.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.totalOpening.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.damageBottles.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.saleBottles.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.storeClosing.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.counterClosing.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.closingBalance.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.excisePrice.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.wsp.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.mrp.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                //table.addCell(model.totalAmount!!.toString())
                table.addCell(PdfPCell(Phrase(model.totalAmount!!.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT

                PdfCreateTask.progressDialog.progress = i
            }

            table.addCell("")
            table.addCell("")
            //table.addCell("Total")
            table.addCell(PdfPCell(Phrase("Total", IposConst.miniFont8)))
            table.addCell(PdfPCell(Phrase(storeOpening.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(counterOpening.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(opening.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalReceiving.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalOpening.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalBreakage.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalSale.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(storeClosing.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(counterClosing.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(closingBalance.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalExcise.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalWsp.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell("")
            //table.addCell(totalAmount.text.toString())
            table.addCell(PdfPCell(Phrase(totalAmount.text.toString(), IposConst.miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT

            // set whole table into paragraph
            paragraph.add(table)
        }

    }
}
