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
import com.loopin.ipos.Adapter.LowInventoryAdapter;
import com.loopin.ipos.Model.BrandWiseModel;
import com.loopin.ipos.Model.LowInventoryModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.ExtraUtils;
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
import static com.loopin.ipos.Utils.IposConst.smallBold;
import static com.loopin.ipos.Utils.IposConst.subFont;
import static com.loopin.ipos.Utils.IposConst.tDate;
import static com.loopin.ipos.Utils.IposConst.totalExcise;
import static com.loopin.ipos.Utils.IposConst.totalReceive;
import static com.loopin.ipos.Utils.IposConst.totalWsp;

public class LowInventoryActivity extends AppCompatActivity {

    private static List<LowInventoryModel> list;
    private RecyclerView lowRec;
    private ImageView back, download;
    LinearLayout noDataView;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_inventory);
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        list = new ArrayList<>();
        lowRec = findViewById(R.id.lowRec);
        back = findViewById(R.id.back);
        download = findViewById(R.id.download);
        noDataView = findViewById(R.id.noDataView);

        getData(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermissions(LowInventoryActivity.this)){
                    new
                            PdfCreateTask(LowInventoryActivity.this).execute("Low Inventory Report");
                }else {
                    checkStoragePermissions(LowInventoryActivity.this);
                }
            }
        });
    }

    // get data

    private void getData(String shop_id){
        list.clear();
        IposProgress.transParentDialog(LowInventoryActivity.this);
        RetrofitController controller = new RetrofitController(this);
        Call<LowInventoryModel.Response> responseCall = controller.getLowInventory("LOW_STOCK_REPORT", shop_id);
        responseCall.enqueue(new Callback<LowInventoryModel.Response>() {
            @Override
            public void onResponse(Call<LowInventoryModel.Response> call, Response<LowInventoryModel.Response> response) {
                if (response.isSuccessful()){
                    IposProgress.stop();
                    if (response.body().getError().equals("0")){
                        for (int i=0; i<response.body().getData().size(); i++){
                            list.add(new LowInventoryModel(response.body().getData().get(i).getBrandName(),
                                    response.body().getData().get(i).getSizeValue(),
                                    response.body().getData().get(i).getQuantity(),
                                    response.body().getData().get(i).getCase()));
                        }
                        LowInventoryAdapter adapter = new LowInventoryAdapter(list);
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(LowInventoryActivity.this);
                        lowRec.setLayoutManager(manager);
                        lowRec.setAdapter(adapter);

                    }else {
                        noDataView.setVisibility(View.INVISIBLE);
                        Toast.makeText(LowInventoryActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    IposProgress.stop();
                    Toast.makeText(LowInventoryActivity.this, R.string.wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LowInventoryModel.Response> call, Throwable t) {

                noDataView.setVisibility(View.VISIBLE);
                IposProgress.stop();
                Toast.makeText(LowInventoryActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // create pdf

    public static void lowInventoryContent(Document document) throws DocumentException {
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
        Paragraph datePara = new Paragraph("Low Inventory Report",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("Date: "+ ExtraUtils.currentTime.toString().substring(0,19));
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        lowInventoryTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void lowInventoryTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{7f, 10f, 23f, 30f, 10f, 8f, 12f};
        table.setWidths(columnWidths);

        PdfPCell c1 = new PdfPCell(new Phrase("SNo.", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("VendID", catFont));
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

        c1 = new PdfPCell(new Phrase("Case", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            LowInventoryModel model = list.get(i);

            table.addCell(new PdfPCell(new Phrase(String.valueOf(i+1)))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(IposConst.sharedPreferences.getString(IposConst.Keys.VEND_ID,"")))).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(new PdfPCell(new Phrase(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""))));
            table.addCell(new PdfPCell(new Phrase(model.getBrandName()))).setPadding(8);
            table.addCell(new PdfPCell(new Phrase(model.getSizeValue().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getCase().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new PdfPCell(new Phrase(model.getQuantity().toString()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        }

        paragraph.add(table);
    }
}
