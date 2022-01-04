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
import com.loopin.ipos.Adapter.BreakageAdapter;
import com.loopin.ipos.Adapter.BreakageSaleAdapter;
import com.loopin.ipos.Model.BreakageModel;
import com.loopin.ipos.Model.BreakageSaleModel;
import com.loopin.ipos.Model.BreakageSaleResponse;
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
import static com.loopin.ipos.Utils.IposConst.totalBreakage;
import static com.loopin.ipos.Utils.IposConst.totalBreakageSale;
import static com.loopin.ipos.Utils.IposConst.totalExcise;
import static com.loopin.ipos.Utils.IposConst.totalWsp;

public class BreakageSaleActivity extends AppCompatActivity {

    private static List<BreakageSaleModel> list;
    private RecyclerView breakageSaleRec;
    private ImageView download;
    private LinearLayout noDataView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakage_sale);

        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        breakageSaleRec = findViewById(R.id.breakageSaleRec);
        totalAmount = findViewById(R.id.totalAmount);
        totalBreakageSale = findViewById(R.id.totalBreakageSale);
        totalWsp = findViewById(R.id.totalWsp);
        totalExcise = findViewById(R.id.totalExcise);
        ImageView backFromBreakage = findViewById(R.id.backFromBreakage);
        noDataView = findViewById(R.id.noDataView);
        download = findViewById(R.id.download);
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

        setBreakageSale(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                fromDate, toDate, lt, lc);

        backFromBreakage.setOnClickListener(view -> finish());
        download.setOnClickListener(view -> {
            if (checkStoragePermissions(BreakageSaleActivity.this)){
                new PdfCreateTask(BreakageSaleActivity.this).execute("Breakage Sale Report");
            }else {
                checkStoragePermissions(BreakageSaleActivity.this);
            }

        });

    }


    public void setBreakageSale(String shop_id, String fromDate, String toDate, String lType, String lCat){
        IposProgress.progressDialog(this).setMessage("Data is loading...");
        list.clear();
        RetrofitController controller = new RetrofitController(this);
        Call<BreakageSaleResponse> call = controller.getBreakageSaleReport("BRAKAGE_SALE_REPORT", shop_id, fromDate, toDate, lType, lCat);
        call.enqueue(new Callback<BreakageSaleResponse>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<BreakageSaleResponse> call, Response<BreakageSaleResponse> response) {
                if (response.isSuccessful()){
                    IposProgress.stop();
                    if (response.body().getError().equals("0")){
                        for (int i=0; i<response.body().getData().size(); i++){
                            list.add(new BreakageSaleModel(response.body().getData().get(i).getBrandName(),
                                    response.body().getData().get(i).getSizeValue(),
                                    response.body().getData().get(i).getBrakageSale(),
                                    response.body().getData().get(i).getTotal(),
                                    response.body().getData().get(i).getMrp(),
                                    response.body().getData().get(i).getExcisePrice(),
                                    response.body().getData().get(i).getWsp()));

                        }
                        BreakageSaleAdapter adapter = new BreakageSaleAdapter();
                        adapter.setBreakageSale(list);
                        LinearLayoutManager manager = new LinearLayoutManager(BreakageSaleActivity.this);
                        breakageSaleRec.setLayoutManager(manager);
                        breakageSaleRec.setAdapter(adapter);
                        totalBreakageSale.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalExcise.setText(String.format("%.2f", response.body().getTotalExcise()));
                        totalWsp.setText(String.format("%.2f", response.body().getTotalWsp()));
                        totalAmount.setText(String.format("%.2f", response.body().getTotalAmount()));
                    }else {
                        download.setVisibility(View.INVISIBLE);
                        noDataView.setVisibility(View.VISIBLE);
                        Toast.makeText(BreakageSaleActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BreakageSaleResponse> call, Throwable t) {
                IposProgress.stop();
                download.setVisibility(View.INVISIBLE);
                Toast.makeText(BreakageSaleActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // create pdf

    public static void breakageSaleContent(Document document) throws DocumentException {
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
        Paragraph datePara = new Paragraph("Breakage Sale Report",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("From Date: "+fDate+"      To Date: "+tDate);
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        breakageSaleTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void breakageSaleTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{8f,26f, 8f, 10f, 12f, 12f, 12f, 12f};
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

        c1 = new PdfPCell(new Phrase("MRP", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Breakage Sale", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Excise", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("WSP", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            BreakageSaleModel model = list.get(i);
            table.addCell(new PdfPCell(new Phrase(String.valueOf(i+1)))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getBrandName()))).setPadding(5);
            table.addCell(new PdfPCell(new Phrase(model.getSizeValue().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getMrp()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getBrakageSale().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getExcisePrice()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getWsp()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getTotal().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        }

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase("Total", catFont))).setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(new Phrase(totalBreakageSale.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalExcise.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalWsp.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalAmount.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        // set whole table into paragraph
        paragraph.add(table);
    }


}
