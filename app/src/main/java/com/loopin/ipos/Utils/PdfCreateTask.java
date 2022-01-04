package com.loopin.ipos.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.loopin.ipos.Activity.StockDetailReportActivity;
import com.loopin.ipos.Activity.StockReportActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.loopin.ipos.Activity.BrandWiseActivity.brandWiseContent;
import static com.loopin.ipos.Activity.BreakageActivity.breakageContent;
import static com.loopin.ipos.Activity.BreakageSaleActivity.breakageSaleContent;
import static com.loopin.ipos.Activity.CostCardActivity.costCardContent;
import static com.loopin.ipos.Activity.DailySaleActivity.dailySaleContent;
import static com.loopin.ipos.Activity.IssueActivity.issueContent;
import static com.loopin.ipos.Activity.LowInventoryActivity.lowInventoryContent;
import static com.loopin.ipos.Activity.SaleDetailReportActivity.saleDetailContent;
import static com.loopin.ipos.Activity.SaleReportActivity.dateWiseSaleContent;
import static com.loopin.ipos.Activity.SupplierWiseActivity.supplierWiseContent;
import static com.loopin.ipos.Activity.TPReportActivity.tpContent;
import static com.loopin.ipos.Activity.VatReportActivity.saleVatContent;
import static com.loopin.ipos.Utils.ExtraUtils.currentDateTime;

public class PdfCreateTask extends AsyncTask<String, Integer, Boolean> {

    private static File dir;
    Context context;
    public static ProgressDialog progressDialog;

    public PdfCreateTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Downloading?");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        //progressDialog.setProgressNumberFormat(""+IposConst.pro);
        progressDialog.show();

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        dir = new File(Environment.getExternalStorageDirectory(), "Download/IposData");

        if (!dir.exists()){
            dir.mkdirs();
        }
        publishProgress(1,5,10);
        File file = new File(dir, strings[0]+"_"+currentDateTime+".pdf");
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            switch (strings[0]){
                case "Cost Card Report":
                    costCardContent(document);
                    break;
                case "Daily Sale Report":
                    dailySaleContent(document);
                    break;
                case "Date Wise Sale Report":
                    dateWiseSaleContent(document);
                    break;
                case "Sale Detail Report":
                    saleDetailContent(document);
                    break;
                case "Sale Vat Report":
                    saleVatContent(document);
                    break;
                case "Stock Report":
                    StockReportActivity.Companion.stockReportContent(document);
                    break;
                case "Stock Detail Report":
                    StockDetailReportActivity.Companion.stockDetailReportContent(document);
                    break;
                case "Purchase Report":
                        tpContent(document);
                    break;
                case "Supplier Wise Purchase":
                    supplierWiseContent(document);
                    break;
                case "Brand Wise Purchase":
                    brandWiseContent(document);
                    break;
                case "Low Inventory Report":
                    lowInventoryContent(document);
                    break;
                case "Goods Issue Report":
                    issueContent(document);
                    break;
                case "Breakage Report":
                    breakageContent(document);
                    break;
                case "Breakage Sale Report":
                    breakageSaleContent(document);
                    break;
            }

            document.close();
            return true;
        } catch (DocumentException e) {
            e.printStackTrace();
            Log.d("fail to download...", e.getMessage());
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("fail to download.....", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        Log.d("progress...", values[0].toString());
        for (Integer value : values) {
            //progressDialog.setProgress(value);
            Log.d("progress......", value.toString());
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.setProgress(100);
        progressDialog.dismiss();
        if (aBoolean) {
            ExtraUtils.CommonDialog(context, "Open File", " OK ",
                    "Download complete!", "File has downloaded successfully into " +
                            "the IposData inside Download folder.");
            Toast.makeText(context, "Pdf downloaded successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Fail to download?", Toast.LENGTH_SHORT).show();
        }
    }
}