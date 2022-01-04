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
import com.loopin.ipos.Adapter.SupplierWiseAdapter;
import com.loopin.ipos.Model.SupplierWiseModel;
import com.loopin.ipos.Model.SupplierWiseResponse;
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
import static com.loopin.ipos.Utils.IposConst.firstDate;
import static com.loopin.ipos.Utils.IposConst.smallBold;
import static com.loopin.ipos.Utils.IposConst.subFont;
import static com.loopin.ipos.Utils.IposConst.tDate;
import static com.loopin.ipos.Utils.IposConst.totalAmount;
import static com.loopin.ipos.Utils.IposConst.totalExcise;
import static com.loopin.ipos.Utils.IposConst.totalQuantity;
import static com.loopin.ipos.Utils.IposConst.totalReceive;
import static com.loopin.ipos.Utils.IposConst.totalWsp;

public class SupplierWiseActivity extends AppCompatActivity {
    private static List<SupplierWiseModel> list;
    private RecyclerView swbRecycle;
   // private static TextView totalReceive, totalExcise, totalWsp;
    private ImageView backFromSwb, download;
    private LinearLayout noDataView;
   // private static String fDate, tDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supp_wise_brand);

        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        swbRecycle = findViewById(R.id.swbRecycle);
        totalReceive = findViewById(R.id.totalReceive);
        totalExcise = findViewById(R.id.totalExcise);
        totalWsp = findViewById(R.id.totalWsp);
        backFromSwb = findViewById(R.id.backFromSwb);
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
        String supplier = getIntent().getStringExtra("supplier");
        backFromSwb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermissions(SupplierWiseActivity.this)){
                    new PdfCreateTask(SupplierWiseActivity.this).execute("Supplier Wise Purchase");
                }else {
                    checkStoragePermissions(SupplierWiseActivity.this);
                }
            }
        });

        setSWB(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""), fromDate, toDate, supplier);

    }

    private void setSWB(String shop_id, String fDate, String tDate, String supplier){
        IposProgress.progressDialog(this).setMessage("Data is loading...");
        list.clear();
        RetrofitController controller = new RetrofitController(this);
        Call<SupplierWiseResponse> call = controller.getSWB("SUPP_WISE_REPORT", shop_id, fDate, tDate, supplier);
        call.enqueue(new Callback<SupplierWiseResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<SupplierWiseResponse> call, Response<SupplierWiseResponse> response) {
                if (response.isSuccessful()){
                    IposProgress.stop();
                    if (response.body().getError().equals("0")){
                        for (int i=0; i<response.body().getData().size(); i++){
                            list.add(new SupplierWiseModel(response.body().getData().get(i).getReceiveDate(),
                                    response.body().getData().get(i).getBrandName(),
                                    response.body().getData().get(i).getSizeValue(),
                                    response.body().getData().get(i).getTotal(),
                                    response.body().getData().get(i).getExcisePrice(),
                                    response.body().getData().get(i).getWsp()));
                        }
                        SupplierWiseAdapter adapter = new SupplierWiseAdapter(SupplierWiseActivity.this, list);
                        LinearLayoutManager manager = new LinearLayoutManager(SupplierWiseActivity.this);
                        swbRecycle.setLayoutManager(manager);
                        swbRecycle.setAdapter(adapter);
                        totalReceive.setText(String.valueOf(response.body().getSumReceveing()));
                        totalExcise.setText(String.format("%.2f", response.body().getSumExcise()));
                        totalWsp.setText(String.format("%.2f", response.body().getSumWsp()));
                    }else {
                        download.setVisibility(View.INVISIBLE);
                        noDataView.setVisibility(View.VISIBLE);
                        Toast.makeText(SupplierWiseActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SupplierWiseResponse> call, Throwable t) {
                IposProgress.stop();
                download.setVisibility(View.INVISIBLE);
                Toast.makeText(SupplierWiseActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // create pdf

    public static void supplierWiseContent(Document document) throws DocumentException {
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
        Paragraph datePara = new Paragraph("Supplier Wise Purchase",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("From Date: "+fDate+"      To Date: "+tDate);
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        supplierWiseTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void supplierWiseTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{7f, 15f, 27f, 8f, 13f, 15f, 15f};
        table.setWidths(columnWidths);

        PdfPCell c1 = new PdfPCell(new Phrase("SNo.", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Receive Date", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Brand Name", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Size", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Receiving", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Excise", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("WSP", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            SupplierWiseModel model = list.get(i);

            table.addCell(new PdfPCell(new Phrase(String.valueOf(i+1)))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(model.getReceiveDate());
            table.addCell(new PdfPCell(new Phrase(model.getBrandName()))).setPadding(5);
            table.addCell(new PdfPCell(new Phrase(model.getSizeValue().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getTotal().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getExcisePrice()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getWsp()))).setHorizontalAlignment(Element.ALIGN_RIGHT);

        }

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase("Total", catFont))).setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(new Phrase(totalReceive.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalExcise.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(new Phrase(totalWsp.getText().toString(),catFont))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        // set whole table into paragraph
        paragraph.add(table);
    }
}
