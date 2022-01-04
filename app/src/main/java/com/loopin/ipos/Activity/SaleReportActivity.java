package com.loopin.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.loopin.ipos.Adapter.SaleReportAdapter;
import com.loopin.ipos.Model.DailySaleModel;
import com.loopin.ipos.Model.DailySaleResponse;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.IposConst;
import com.loopin.ipos.Utils.IposProgress;
import com.loopin.ipos.Utils.PdfCreateTask;
import com.loopin.ipos.Utils.RetrofitController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.loopin.ipos.Utils.ExtraUtils.checkStoragePermissions;
import static com.loopin.ipos.Utils.ExtraUtils.emptyLine;
import static com.loopin.ipos.Utils.IposConst.catFont;
import static com.loopin.ipos.Utils.IposConst.date;
import static com.loopin.ipos.Utils.IposConst.lc;
import static com.loopin.ipos.Utils.IposConst.lt;
import static com.loopin.ipos.Utils.IposConst.miniFont10;
import static com.loopin.ipos.Utils.IposConst.miniFont8;
import static com.loopin.ipos.Utils.IposConst.smallBold;
import static com.loopin.ipos.Utils.IposConst.subFont;
import static com.loopin.ipos.Utils.IposConst.totalAmount;
import static com.loopin.ipos.Utils.IposConst.totalQuantity;
import static com.loopin.ipos.Utils.IposConst.totalSale;
import static com.loopin.ipos.Utils.IposProgress.progressDialog;

public class SaleReportActivity extends AppCompatActivity {
    private static List<DailySaleModel> list;
    private RecyclerView saleRec;
    @SuppressLint("StaticFieldLeak")
    //private static TextView ;
    private ImageView backFromSale, download;
    private LinearLayout noDataView;
   // private static String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_report);
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        saleRec = findViewById(R.id.saleRec);
        totalAmount = findViewById(R.id.totalAmount);
        totalSale = findViewById(R.id.totalSale);

        noDataView = findViewById(R.id.noDataView);

        backFromSale = findViewById(R.id.backFromSale);
        download = findViewById(R.id.download);
        list = new ArrayList<>();
        date = getIntent().getStringExtra("date");
        Log.d("date", date);   //  dd/mm/yyyy
        String d = date.substring(0,2);
        String m = date.substring(3,5);
        String y = date.substring(6,10);
        String apiDate = y+"/"+m+"/"+d;
        Log.d("apiDate", apiDate);
        if (getIntent().getStringExtra("lt").equalsIgnoreCase("All")){
            lt = getIntent().getStringExtra("lt");
        }else {
            lt = getIntent().getStringExtra("lt")+"_Liquor";
        }
        lc = getIntent().getStringExtra("lc");
        backFromSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermissions(SaleReportActivity.this)){
                    new PdfCreateTask(SaleReportActivity.this).execute("Date Wise Sale Report");
                }else {
                    checkStoragePermissions(SaleReportActivity.this);
                }
            }
        });

        setData(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""), apiDate, apiDate, lt, lc);
    }

    private void setData(String shop_id, String fDate, String tDate, String lType, String lCat){
        progressDialog(this).setMessage("Data is loading...");
        list.clear();
        RetrofitController controller = new RetrofitController(this);
        Call<DailySaleResponse> call = controller.getSale("SALE_REPORT", shop_id, fDate, tDate, lType, lCat);
        call.enqueue(new Callback<DailySaleResponse>() {
            @Override
            public void onResponse(Call<DailySaleResponse> call, Response<DailySaleResponse> response) {
                if (response.isSuccessful()){
                    IposProgress.stop();
                    if (response.body().getError().equals("0")){
                        for (int i=0; i<response.body().getData().size(); i++){
                            list.add(new DailySaleModel(response.body().getData().get(i).getBrandName(),
                                    response.body().getData().get(i).getSizeValue(),
                                    response.body().getData().get(i).getQuantity(),
                                    response.body().getData().get(i).getTotal(),
                                    response.body().getData().get(i).getMrp()));
                        }
                        SaleReportAdapter adapter = new SaleReportAdapter(SaleReportActivity.this, list);
                        LinearLayoutManager manager = new LinearLayoutManager(SaleReportActivity.this);
                        saleRec.setLayoutManager(manager);
                        saleRec.setAdapter(adapter);

                        totalSale.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalAmount.setText(String.valueOf(Double.valueOf(response.body().getTotalAmount())));
                    }else {
                        download.setVisibility(View.INVISIBLE);
                        noDataView.setVisibility(View.VISIBLE);
                        Toast.makeText(SaleReportActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DailySaleResponse> call, Throwable t) {
                IposProgress.stop();
                download.setVisibility(View.INVISIBLE);
                Toast.makeText(SaleReportActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // create pdf

    public static void dateWiseSaleContent(Document document) throws DocumentException {
        Paragraph title = new Paragraph(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,"")
                +"\n"+IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_ADDRESS,"")
                +"-"+IposConst.sharedPreferences.getString(IposConst.Keys.PIN_CODE,""),
                IposConst.titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        LineSeparator ls = new LineSeparator();
        ls.setLineWidth(2f);
        document.add(new Chunk(ls));
        document.add(new Paragraph("\n"));

        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph datePara = new Paragraph("Date Wise Sale Report",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("Date: "+date);
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        dateWiseSaleTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void dateWiseSaleTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{10f, 10f, 16f, 20f, 10f, 12f, 10f, 12f};
        table.setWidths(columnWidths);

        PdfPCell c1 = new PdfPCell(new Phrase("SNo.", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Vend ID", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Shop Name", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Brand Name", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Size", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("MRP", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Sale", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total Amount", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            DailySaleModel model = list.get(i);

            table.addCell(new PdfPCell(new Phrase(String.valueOf(i+1)))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,""), miniFont10)));
            table.addCell(new PdfPCell(new Phrase(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""), miniFont10)));
            table.addCell(new PdfPCell(new Phrase(model.getBrandName(), miniFont8))).setPadding(5);
            table.addCell(new PdfPCell(new Phrase(model.getSizeValue().toString(), miniFont10))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getQuantity().toString(), miniFont10))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getMrp().toString(), miniFont10))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getTotal().toString(), miniFont10))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        }


        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase("Total", catFont)))
                .setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(new Phrase(totalSale.getText().toString(),catFont)))
                .setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase(totalAmount.getText().toString(), catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        // set whole table into paragraph
        paragraph.add(table);
    }
}