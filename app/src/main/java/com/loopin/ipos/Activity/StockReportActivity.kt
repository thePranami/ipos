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
import com.loopin.ipos.Adapter.StockReportAdapter
import com.loopin.ipos.Model.StockReportModel
import com.loopin.ipos.Model.StockRportResponse
import com.loopin.ipos.R
import com.loopin.ipos.Utils.*
import com.loopin.ipos.Utils.ExtraUtils.checkStoragePermissions
import com.loopin.ipos.Utils.ExtraUtils.emptyLine
import com.loopin.ipos.Utils.IposConst.*
import kotlinx.android.synthetic.main.activity_stock_report.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

 class StockReportActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_report)
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, Context.MODE_PRIVATE)
        IposConst.editor = IposConst.sharedPreferences.edit()

        Companion.totalAmount = findViewById(R.id.totalAmount)
        Companion.totalBreakage = findViewById(R.id.totalBreakage)
        Companion.totalCustom = findViewById(R.id.totalCustom)
        Companion.totalWsp = findViewById(R.id.totalWsp)
        Companion.totalExcise = findViewById(R.id.totalExcise)
        Companion.totalStock = findViewById(R.id.totalStock)
        Companion.totalCost = findViewById<TextView>(R.id.totalCost)
        Companion.noDataView = findViewById<LinearLayout>(R.id.noDataView)
        Companion.download = findViewById<ImageView>(R.id.download)

        var backfromSR:ImageView = findViewById(R.id.backFromSR)

        list = ArrayList<StockReportModel>()
//        var srRecycle = findViewById<RecyclerView>(R.id.srRecycler)
//        var totalBreakage:TextView = findViewById(R.id.totalBreakage)
//        var totalStock:TextView = findViewById(R.id.totalStock)
//        var totalExcise = findViewById<TextView>(R.id.totalExcise)
//        var totalWsp = findViewById<TextView>(R.id.totalWsp)
//        var totalCustom:TextView = findViewById(R.id.totalCustom)
//        var totalAmount = findViewById<TextView>(R.id.totalAmount)

        backfromSR.setOnClickListener {
            finish()
        }
        download.setOnClickListener {
            if (checkStoragePermissions(this)){
                PdfCreateTask(this).execute("Stock Report")
            }else{
                checkStoragePermissions(this)
            }
        }

        date = intent.getStringExtra("date")!!
        Log.d("date", date)   //  dd/mm/yyyy

        val d = date.substring(0, 2)
        val m = date.substring(3, 5)
        val y = date.substring(6, 10)
        val apiDate = "$y/$m/$d"
        Log.d("apiDate...?", apiDate)

        lt = if (intent.getStringExtra("lt").equals("All", ignoreCase = true)) {
            intent.getStringExtra("lt")
        } else {
            intent.getStringExtra("lt") + "_Liquor"
        }
        lc = intent.getStringExtra("lc").toString()

        if (!Internet.isNetAvailable(applicationContext)){
            finish()
            Toast.makeText(applicationContext, R.string.internet, Toast.LENGTH_SHORT).show()
        }else{
            setStockReport(this,IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY, "")!!,
                    apiDate, lt)
        }
    }

    companion object{

        lateinit var date:String
        var list: MutableList<StockReportModel> = mutableListOf()
        @SuppressLint("StaticFieldLeak")
        lateinit var totalAmount: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var totalCustom: TextView
        lateinit var totalCost:TextView
        lateinit var totalBreakage: TextView
        lateinit var totalExcise: TextView
        lateinit var totalWsp: TextView
        lateinit var totalStock:TextView
        lateinit var download:ImageView
        lateinit var noDataView:LinearLayout
         fun setStockReport(application: Activity, shop_id: String, date: String, liqType: String) {
            IposProgress.progressDialog(application).setMessage("Data is loading...")
            list.clear()
            val controller = RetrofitController(application)
            val call:Call<StockRportResponse> = controller.getStockReport("STOCK_REPORT", shop_id, date, liqType)
            call.enqueue(object : Callback<StockRportResponse> {
                @SuppressLint("SetTextI18n", "DefaultLocale")
                override fun onResponse(call: Call<StockRportResponse>, response: Response<StockRportResponse>) {
                    if (response.isSuccessful) {
                        IposProgress.stop()
                        if (response.body()!!.error == "0") {
                            for (i in 0 until response.body()!!.data.size) {
                                list.add(StockReportModel(response.body()!!.data[i].brandName,
                                        response.body()!!.data[i].sizeValue,
                                        response.body()!!.data[i].storeOpening,
                                        response.body()!!.data[i].counterOpening,
                                        response.body()!!.data[i].breakage,
                                        response.body()!!.data[i].closingBalance,
                                        response.body()!!.data[i].excisePrice,
                                        response.body()!!.data[i].wsp,
                                        response.body()!!.data[i].customDuty,
                                        response.body()!!.data[i].mrp,
                                        response.body()!!.data[i].totalAmount))
                            }
                            val adapter = StockReportAdapter(list)
                            val manager = LinearLayoutManager(application)
                            val srRecycler:RecyclerView = application.findViewById(R.id.srRecycler)
                            srRecycler.layoutManager = manager
                            srRecycler.adapter = adapter

                            //totalBreakage.setText(response.body()!!.sumBreakageStock)
                            totalBreakage.text = (response.body()!!.sumBreakageStock).toString()
                            totalExcise.text=(String.format("%.2f", response.body()!!.sumTotalExcise!!))
                            totalStock.text = (response.body()!!.sumTotalStock).toString()
                            //totalCustom.setText("0")
                            totalCost.text = "46643.46"
                            totalWsp.text = String.format("%.2f", response.body()!!.sumTotalWsp!!)
                            totalCustom.text = (response.body()!!.sumTotalCustom).toString()
                            totalAmount.text = String.format("%.2f", response.body()!!.sumTotalAmount!!)
                        } else {
                            noDataView.visibility = View.VISIBLE
                            download.visibility = View.GONE
                            Toast.makeText(application, R.string.no_data, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<StockRportResponse>, t: Throwable) {
                    IposProgress.stop()
                    download.visibility = View.GONE
                    Log.d("vbgfrtyugbu", t.message!!)
                    Toast.makeText(application, R.string.server_error, Toast.LENGTH_SHORT).show()
                }
            })

        }

        // create pdf

        fun stockReportContent(document: Document) {
            val title = Paragraph(IposConst.sharedPreferences.getString(Keys.SHOP_NAME, "")
                    + "\n" + IposConst.sharedPreferences.getString(Keys.SHOP_ADDRESS, "")
                    + "-" + IposConst.sharedPreferences.getString(Keys.PIN_CODE, ""),
                    titleFont)
            title.alignment = Element.ALIGN_CENTER
            document.add(title)
            val ls = LineSeparator()
            ls.lineWidth = 2f
            document.add(Chunk(ls))
            document.add(Paragraph("\n"))

            val glue = Chunk(VerticalPositionMark())
            val datePara = Paragraph("Stock Report", smallBold)
            datePara.add(Chunk(glue))
            datePara.add("Date: ${Companion.date}")
            document.add(datePara)

            val subPara = Paragraph("\n", subFont)
            stockReportTable(subPara)
            document.add(subPara)

            val paragraph = Paragraph()
            emptyLine(paragraph, 5)

        }

        @Throws(DocumentException::class)
        fun stockReportTable(paragraph: Paragraph) {
            val table = PdfPTable(12)
            table.widthPercentage = 100f

            val columnWidths = floatArrayOf(5f, 20f, 8f, 7f, 7f, 5f, 7f, 10f, 7f, 7f, 7f, 10f)
            table.setWidths(columnWidths)

            var c1 = PdfPCell(Phrase("SNo.", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Brand Name", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Size", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Store Closing", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Counter Closing", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Breakage", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Stock", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Excise", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("WSP", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Custom", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("MRP", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            c1 = PdfPCell(Phrase("Total", miniFont8))
            c1.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(c1)

            table.headerRows = 1

            for (i in list.indices) {

                val model = list[i]

                table.addCell((i + 1).toString())
                table.addCell(PdfPCell(Phrase(model.brandName.toString(), miniFont8)))
                table.addCell(PdfPCell(Phrase(model.sizeValue.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.storeOpening.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.counterOpening.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.breakage.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.closingBalance.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.excisePrice.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.wsp.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.customDuty.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.mrp.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
                table.addCell(PdfPCell(Phrase(model.totalAmount.toString(), miniFont8))).horizontalAlignment = Element.ALIGN_RIGHT
            }

            table.addCell("")
            table.addCell("")
            table.addCell(PdfPCell(Phrase("Total", miniFont10))).horizontalAlignment = Element.ALIGN_MIDDLE
            table.addCell("")
            table.addCell("")
            table.addCell(PdfPCell(Phrase(totalBreakage.text.toString(), miniFont10))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalStock.text.toString(), miniFont10))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalExcise.text.toString(), miniFont10))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalWsp.text.toString(), miniFont10))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell(PdfPCell(Phrase(totalCustom.text.toString(), miniFont10))).horizontalAlignment = Element.ALIGN_RIGHT
            table.addCell("")
            table.addCell(PdfPCell(Phrase(totalAmount.text.toString(), miniFont10))).horizontalAlignment = Element.ALIGN_RIGHT

            // set whole table into paragraph
            paragraph.add(table)
        }
    }
}


