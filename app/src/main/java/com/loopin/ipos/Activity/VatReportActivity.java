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
import com.loopin.ipos.Adapter.VatReportAdapter;
import com.loopin.ipos.Model.DailySaleModel;
import com.loopin.ipos.Model.VatReportModel;
import com.loopin.ipos.Model.VatReportResponse;
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
import static com.loopin.ipos.Utils.IposConst.lt;
import static com.loopin.ipos.Utils.IposConst.smallBold;
import static com.loopin.ipos.Utils.IposConst.subFont;
import static com.loopin.ipos.Utils.IposConst.tDate;
import static com.loopin.ipos.Utils.IposConst.totalAmount;
import static com.loopin.ipos.Utils.IposConst.totalGross;
import static com.loopin.ipos.Utils.IposConst.totalQuantity;
import static com.loopin.ipos.Utils.IposConst.totalVat;

public class VatReportActivity extends AppCompatActivity {
    private RecyclerView vatRecycler;
    private static List<VatReportModel> list;
    private ImageView backFromVat, download;
    private LinearLayout noDataView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_report);
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        vatRecycler = findViewById(R.id.vatRecycler);
        totalGross = findViewById(R.id.totalGross);
        totalVat = findViewById(R.id.totalVat);
        IposConst.totalAmount = findViewById(R.id.totalAmount);
        backFromVat = findViewById(R.id.backFromVat);
        download = findViewById(R.id.download);
        list = new ArrayList<>();
        noDataView = findViewById(R.id.noDataView);
        backFromVat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermissions(VatReportActivity.this)){
                    new PdfCreateTask(VatReportActivity.this).execute("Sale Vat Report");
                }else {
                    checkStoragePermissions(VatReportActivity.this);
                }
            }
        });

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

        setVatReport(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""), fromDate, toDate, lt);

    }

    private void setVatReport(String shop_id, String fDate, String tDate, String lType){
        IposProgress.progressDialog(this).setMessage("Data is loading...");
        list.clear();
        RetrofitController controller = new RetrofitController(this);
        Call<VatReportResponse> call = controller.getVatReport("VAT_REPORT", shop_id, fDate, tDate, lType);
        call.enqueue(new Callback<VatReportResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<VatReportResponse> call, Response<VatReportResponse> response) {
                if (response.isSuccessful()){
                    IposProgress.stop();
                    if (response.body().getError().equals("0")){
                        for (int i=0; i<response.body().getData().size(); i++){
                            list.add(new VatReportModel(response.body().getData().get(i).getInvDate(),
                                    response.body().getData().get(i).getLiquorType(),
                                    response.body().getData().get(i).getGrossSale(),
                                    response.body().getData().get(i).getVat(),
                                    response.body().getData().get(i).getTotSale()));
                        }
                        VatReportAdapter adapter = new VatReportAdapter(VatReportActivity.this, list);
                        LinearLayoutManager manager = new LinearLayoutManager(VatReportActivity.this);
                        vatRecycler.setLayoutManager(manager);
                        vatRecycler.setAdapter(adapter);
                        totalAmount.setText(String.format("%.2f",response.body().getSumTotal().doubleValue()));
                        totalGross.setText(String.format("%.2f",response.body().getSumGrossSale().doubleValue()));
                        totalVat.setText(String.format("%.2f",response.body().getSumVat().doubleValue()));
                    }else {
                        download.setVisibility(View.INVISIBLE);
                        noDataView.setVisibility(View.VISIBLE);
                        Toast.makeText(VatReportActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VatReportResponse> call, Throwable t) {
                IposProgress.stop();
                download.setVisibility(View.INVISIBLE);
                Toast.makeText(VatReportActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // create pdf

    public static void saleVatContent(Document document) throws DocumentException {
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
        Paragraph datePara = new Paragraph("Sale Vat Report",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("From Date: "+fDate+"     "+"To Date: "+tDate);
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        saleVatTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void saleVatTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{10f, 20f, 25f, 15f, 15f, 15f};
        table.setWidths(columnWidths);

        PdfPCell c1 = new PdfPCell(new Phrase("SNo.", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Invoice Date", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Liquor Type", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Gross Sale", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("VAT", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            VatReportModel model = list.get(i);

            table.addCell(new PdfPCell(new Phrase(String.valueOf(i+1)))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getInvDate()))).setPadding(5);
            table.addCell(new PdfPCell(new Phrase(model.getLiquorType()))).setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(new Phrase(model.getGrossSale()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getVat()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getTotSale()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        }

        table.addCell("");
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase("Total", catFont))).setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(new Phrase(totalGross.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalVat.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalAmount.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        // set whole table into paragraph
        paragraph.add(table);
    }
}
