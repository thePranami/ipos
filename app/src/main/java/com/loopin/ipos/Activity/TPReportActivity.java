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
import com.loopin.ipos.Adapter.TPReportAdapter;
import com.loopin.ipos.Model.CostCardModel;
import com.loopin.ipos.Model.TPReportModel;
import com.loopin.ipos.Model.TPReportResponse;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.IposConst;
import com.loopin.ipos.Utils.IposProgress;
import com.loopin.ipos.Utils.PdfCreateTask;
import com.loopin.ipos.Utils.RetrofitController;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.loopin.ipos.Utils.ExtraUtils.checkStoragePermissions;
import static com.loopin.ipos.Utils.ExtraUtils.emptyLine;
import static com.loopin.ipos.Utils.IposConst.fDate;
import static com.loopin.ipos.Utils.IposConst.smallBold;
import static com.loopin.ipos.Utils.IposConst.subFont;
import static com.loopin.ipos.Utils.IposConst.tDate;
import static com.loopin.ipos.Utils.IposConst.totalAmount;
import static com.loopin.ipos.Utils.IposConst.totalCustom;
import static com.loopin.ipos.Utils.IposConst.totalExcise;
import static com.loopin.ipos.Utils.IposConst.totalReceiving;
import static com.loopin.ipos.Utils.IposConst.totalTcs;
import static com.loopin.ipos.Utils.IposConst.totalVat;
import static com.loopin.ipos.Utils.IposConst.totalWsp;

public class TPReportActivity extends AppCompatActivity {

    private static List<TPReportModel> list;
    private RecyclerView tpRecycle;
    private ImageView backFromTP, download;
    private LinearLayout noDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpreport);
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        totalReceiving = findViewById(R.id.totalReceiving);
        totalExcise = findViewById(R.id.totalExcise);
        totalWsp = findViewById(R.id.totalWsp);
        totalCustom = findViewById(R.id.totalCustom);
        totalVat = findViewById(R.id.totalVat);
        totalTcs = findViewById(R.id.totalTcs);
        totalAmount = findViewById(R.id.totalAmount);
        list = new ArrayList<>();
        tpRecycle = findViewById(R.id.tpRecycler);
        backFromTP = findViewById(R.id.backFromTP);
        noDataView = findViewById(R.id.noDataView);
        download = findViewById(R.id.download);
        backFromTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        download.setOnClickListener(view -> {
            if (checkStoragePermissions(TPReportActivity.this)){
                new PdfCreateTask(this).execute("Purchase Report");
            }else {
                checkStoragePermissions(TPReportActivity.this);
            }
        });

        // from home activity
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
        String supplier = getIntent().getStringExtra("supplier");

        setTPReport(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""), fromDate, toDate, supplier);

    }
    private void setTPReport(String shop_id, String fDate, String tDate, String supplier){
        IposProgress.progressDialog(this).setMessage("Data is loading...");
        list.clear();
        RetrofitController controller = new RetrofitController(this);
        Call<TPReportResponse> call = controller.getTPReport("TP_REPORT", shop_id, fDate, tDate, supplier);
        call.enqueue(new Callback<TPReportResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<TPReportResponse> call, Response<TPReportResponse> response) {
                if (response.isSuccessful()){
                    IposProgress.stop();
                    if (response.body().getError().equals("0")){
                        for (int i=0; i<response.body().getData().size(); i++){
                            list.add(new TPReportModel(response.body().getData().get(i).getReceiveDate(),
                                    response.body().getData().get(i).getInvoiceNo(),
                                    response.body().getData().get(i).getConsignmentNo(),
                                    response.body().getData().get(i).getSupName(),
                                    response.body().getData().get(i).getTotalReceived(),
                                    response.body().getData().get(i).getWsp(),
                                    response.body().getData().get(i).getExcise(),
                                    response.body().getData().get(i).getCustom(),
                                    response.body().getData().get(i).getVat(),
                                    response.body().getData().get(i).getTcs(),
                                    response.body().getData().get(i).getTotal()));
                        }
                        TPReportAdapter adapter = new TPReportAdapter(TPReportActivity.this, list);
                        LinearLayoutManager manager = new LinearLayoutManager(TPReportActivity.this);
                        tpRecycle.setLayoutManager(manager);
                        tpRecycle.setAdapter(adapter);

                        totalReceiving.setText(String.valueOf(response.body().getSumReceiving()));
                        totalExcise.setText(String.format("%.2f", response.body().getSumExcise()));
                        totalWsp.setText(String.format("%.2f", response.body().getSumWsp()));
                        totalCustom.setText(String.format("%.2f", response.body().getSumCustom()));
                        totalVat.setText(String.format("%.2f", response.body().getSumVat()));
                        totalTcs.setText(String.format("%.2f", response.body().getSumTcs()));
                        totalAmount.setText(String.format("%.2f", response.body().getSumTotalAmount()));
                    }else {
                        download.setVisibility(View.INVISIBLE);
                        noDataView.setVisibility(View.VISIBLE);
                        Toast.makeText(TPReportActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<TPReportResponse> call, @NotNull Throwable t) {
                IposProgress.stop();
                download.setVisibility(View.INVISIBLE);
                Toast.makeText(TPReportActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // create pdf

    public static void tpContent(Document document) throws DocumentException {
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
        Paragraph datePara = new Paragraph("Purchase Report",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("From Date: "+fDate+"      To Date: "+tDate);
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        tpTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void tpTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(12);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{5f, 8f, 10f, 5f, 12f, 6f, 10f, 10f, 8f, 8f, 8f, 8f};
        table.setWidths(columnWidths);

        PdfPCell c1 = new PdfPCell(new Phrase("SNo."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Invoice"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("TP Number"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Receive Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Supplier Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Receiving"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Excise"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("WSP"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Custom"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("VAT"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("TCS"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("Total"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            TPReportModel model = list.get(i);

            table.addCell(String.valueOf(i+1));
            table.addCell(model.getReceiveDate());
            table.addCell(model.getInvoiceNo());
            table.addCell(model.getConsignmentNo());
            table.addCell(model.getSupName());
            table.addCell(model.getTotalReceived().toString());
            table.addCell(model.getWsp());
            table.addCell(model.getExcise());
            table.addCell(model.getCustom());
            table.addCell(model.getVat());
            table.addCell(model.getTcs());
            table.addCell(model.getTotal());
        }

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Total");
        table.addCell(totalReceiving.getText().toString());
        table.addCell(totalExcise.getText().toString());
        table.addCell(totalWsp.getText().toString());
        table.addCell(totalCustom.getText().toString());
        table.addCell(totalVat.getText().toString());
        table.addCell(totalTcs.getText().toString());
        table.addCell(totalAmount.getText().toString());
        // set whole table into paragraph
        paragraph.add(table);
    }

}
