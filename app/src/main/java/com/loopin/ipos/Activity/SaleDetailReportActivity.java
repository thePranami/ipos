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
import com.loopin.ipos.Adapter.SaleDetailReportAdapter;
import com.loopin.ipos.Model.CostCardModel;
import com.loopin.ipos.Model.SaleDetailReportModel;
import com.loopin.ipos.Model.SaleDetailReportResponse;
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
import static com.loopin.ipos.Utils.IposConst.fDate;
import static com.loopin.ipos.Utils.IposConst.lc;
import static com.loopin.ipos.Utils.IposConst.lt;
import static com.loopin.ipos.Utils.IposConst.smallBold;
import static com.loopin.ipos.Utils.IposConst.subFont;
import static com.loopin.ipos.Utils.IposConst.tDate;
import static com.loopin.ipos.Utils.IposConst.totalAmount;
import static com.loopin.ipos.Utils.IposConst.totalExcise;
import static com.loopin.ipos.Utils.IposConst.totalSale;
import static com.loopin.ipos.Utils.IposConst.totalWsp;

public class SaleDetailReportActivity extends AppCompatActivity {
    private static List<SaleDetailReportModel> list;
    private RecyclerView saleDetRec;
    private ImageView backFromSaleDetail, download;
    private LinearLayout noDataView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail_report);

        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        saleDetRec = findViewById(R.id.saleDetRec);
        totalAmount = findViewById(R.id.totalAmount);
        totalSale = findViewById(R.id.totalSale);
        totalExcise = findViewById(R.id.totalExcise);
        totalWsp = findViewById(R.id.totalWsp);
        backFromSaleDetail = findViewById(R.id.backFromSaleDetail);
        download = findViewById(R.id.download);
        noDataView = findViewById(R.id.noDataView);
        list = new ArrayList<>();
        fDate = getIntent().getStringExtra("fDate");
        Log.d("date", fDate);   //  dd/mm/yyyy
        String fd = fDate.substring(0,2);
        String fm = fDate.substring(3,5);
        String fy = fDate.substring(6,10);
        String fromDate = fy+"/"+fm+"/"+fd;
        tDate = getIntent().getStringExtra("tDate");
        Log.d("date", fDate);   //  dd/mm/yyyy
        String td = tDate.substring(0,2);
        String tm = tDate.substring(3,5);
        String ty = tDate.substring(6,10);
        String toDate = ty+"/"+tm+"/"+td;
        if (getIntent().getStringExtra("lt").equalsIgnoreCase("All")){
            lt = getIntent().getStringExtra("lt");
        }else {
            lt = getIntent().getStringExtra("lt")+"_Liquor";
        }
        lc = getIntent().getStringExtra("lc");
        backFromSaleDetail.setOnClickListener(view -> finish());
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermissions(SaleDetailReportActivity.this)){
                    new PdfCreateTask(SaleDetailReportActivity.this).execute("Sale Detail Report");
                }else {
                    checkStoragePermissions(SaleDetailReportActivity.this);
                }
            }
        });

        setSaleDetRep(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""), fromDate, toDate, lt, lc);

    }

    // api name same as sale report
    private void setSaleDetRep(String shop_id, String fDate, String tDate, String lType, String lCat){
        IposProgress.progressDialog(this).setMessage("Data is loading...");
        list.clear();
        RetrofitController controller = new RetrofitController(this);
        Call<SaleDetailReportResponse> call = controller.getSaleDetail("SALE_REPORT", shop_id, fDate, tDate, lType, lCat);
        call.enqueue(new Callback<SaleDetailReportResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<SaleDetailReportResponse> call, Response<SaleDetailReportResponse> response) {
                if (response.isSuccessful()){
                    IposProgress.stop();
                    if (response.body().getError().equals("0")){
                        for (int i=0; i<response.body().getData().size(); i++){
                            list.add(new SaleDetailReportModel(response.body().getData().get(i).getBrandName(),
                                    response.body().getData().get(i).getSizeValue(),
                                    response.body().getData().get(i).getQuantity(),
                                    response.body().getData().get(i).getMrp(),
                                    response.body().getData().get(i).getExcisePrice(),
                                    response.body().getData().get(i).getWsp(),
                                    response.body().getData().get(i).getTotal()));
                        }
                        SaleDetailReportAdapter adapter = new SaleDetailReportAdapter(SaleDetailReportActivity.this, list);
                        LinearLayoutManager manager = new LinearLayoutManager(SaleDetailReportActivity.this);
                        saleDetRec.setLayoutManager(manager);
                        saleDetRec.setAdapter(adapter);
                        totalSale.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalAmount.setText(String.format("%.2f", response.body().getTotalAmount()));
                        //totalAmount.setText(String.valueOf(Double.valueOf(response.body().getTotalAmount())));
                        totalExcise.setText(Double.toString(response.body().getTotalExcise()));
                        totalWsp.setText(Double.toString(response.body().getTotalWsp()));
                    }else {
                        noDataView.setVisibility(View.VISIBLE);
                        download.setVisibility(View.INVISIBLE);
                        Toast.makeText(SaleDetailReportActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaleDetailReportResponse> call, Throwable t) {
                IposProgress.stop();
                download.setVisibility(View.INVISIBLE);
                Toast.makeText(SaleDetailReportActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // create pdf

    public static void saleDetailContent(Document document) throws DocumentException {
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
        Paragraph datePara = new Paragraph("Sale Detail Report",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("From Date: "+fDate+"     "+"To Date: "+tDate);
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        saleDetailTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void saleDetailTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{10f, 25f, 10f, 11f, 11f, 11f, 11f, 11f};
        table.setWidths(columnWidths);

        PdfPCell c1 = new PdfPCell(new Phrase("SNo.", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Brand Name", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Size", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Sale", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("MRP", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Excise", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("WSP", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total Amount", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            SaleDetailReportModel model = list.get(i);

            table.addCell(new PdfPCell(new Phrase(String.valueOf(i+1)))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getBrandName()))).setPadding(5);
            table.addCell(new PdfPCell(new Phrase(model.getSizeValue().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getQuantity().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getMrp()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getExcisePrice()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getWsp()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getTotal().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);

//            table.addCell(String.valueOf(i+1));
//            table.addCell(model.getBrandName());
//            table.addCell(model.getSizeValue().toString());
//            table.addCell(model.getQuantity().toString());
//            table.addCell(model.getMrp());
//            table.addCell(model.getExcisePrice());
//            table.addCell(model.getWsp());
//            table.addCell(model.getTotal().toString());
        }

        /* work fine
        table.addCell("");
        table.addCell("");
        table.addCell("Total");
        table.addCell(totalSale.getText().toString());
        table.addCell("");
        table.addCell(totalExcise.getText().toString());
        table.addCell(totalWsp.getText().toString());
        table.addCell(totalAmount.getText().toString());
        */
        table.addCell("");
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase("Total", catFont)))
                .setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(new Phrase(totalSale.getText().toString(), catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase(totalExcise.getText().toString(), catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalWsp.getText().toString(), catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalAmount.getText().toString(), catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        // set whole table into paragraph
        paragraph.add(table);
    }
}
