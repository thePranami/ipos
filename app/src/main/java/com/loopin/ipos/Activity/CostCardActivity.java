package com.loopin.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.loopin.ipos.Adapter.CostCardAdapter;
import com.loopin.ipos.Model.CostCardModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.Internet;
import com.loopin.ipos.Utils.IposConst;
import com.loopin.ipos.Utils.IposProgress;
import com.loopin.ipos.Utils.PdfCreateTask;
import com.loopin.ipos.Utils.VolleyController;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.loopin.ipos.Utils.ExtraUtils.checkStoragePermissions;
import static com.loopin.ipos.Utils.ExtraUtils.emptyLine;
import static com.loopin.ipos.Utils.IposConst.date;
import static com.loopin.ipos.Utils.IposConst.smallBold;
import static com.loopin.ipos.Utils.IposConst.subFont;
import static com.loopin.ipos.Utils.IposConst.totalAmount;
import static com.loopin.ipos.Utils.IposConst.totalClosing;
import static com.loopin.ipos.Utils.IposConst.totalCustom;
import static com.loopin.ipos.Utils.IposConst.totalExcise;
import static com.loopin.ipos.Utils.IposConst.totalWsp;

public class CostCardActivity extends AppCompatActivity {

    private static List<CostCardModel> list;
    private RecyclerView ccRecycle;
    private ImageView backFromCc, download;
    private LinearLayout noDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_card);
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        list = new ArrayList<>();
        ccRecycle = findViewById(R.id.ccRecycler);
        totalClosing = findViewById(R.id.totalClosing);
        totalCustom = findViewById(R.id.totalCustom);
        totalExcise = findViewById(R.id.totalExcise);
        totalWsp = findViewById(R.id.totalWsp);
        totalAmount = findViewById(R.id.totalAmount);
        backFromCc = findViewById(R.id.backFromCc);
        noDataView = findViewById(R.id.noDataView);
        download = findViewById(R.id.download);
        date = getIntent().getStringExtra("date");
        Log.d("date", date);   //  dd/mm/yyyy

        String d = date.substring(0,2);
        String m = date.substring(3,5);
        String y = date.substring(6,10);
        String apiDate = y+"/"+m+"/"+d;
        Log.d("apiDate", apiDate);

       // getCostCard("23", "2021-07-10", "All");

        getCostCard(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                apiDate, getIntent().getStringExtra("brand"));
        backFromCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        download.setOnClickListener(view -> {
            if (checkStoragePermissions(CostCardActivity.this)){
                new PdfCreateTask(this).execute("Cost Card Report");
            }else {
                checkStoragePermissions(CostCardActivity.this);
            }
        });

    }

    private void getCostCard(String shopId, String date, String brand){
        if (!Internet.isNetAvailable(CostCardActivity.this)){
            Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
        }else {
            list.clear();
            IposProgress.progressDialog(CostCardActivity.this).setMessage("Data is loading...");
            StringRequest request = new StringRequest(Request.Method.POST, IposConst.volleyUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    IposProgress.stop();
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("error").equals("0")) {
                            JSONArray array = object.getJSONArray("data");
                            for (int i=0; i<array.length(); i++){
                                JSONObject object1 = array.getJSONObject(i);
                                list.add(new CostCardModel(object1.getString("BRAND_NAME"),
                                        object1.getInt("SIZE_VALUE"),
                                        object1.getInt("total"),
                                        object1.getDouble("EXCISE_PRICE"),
                                        object1.getDouble("WSP"),
                                        object1.getDouble("CUSTOM_DUTY"),
                                        object1.getDouble("COST_PRICE")));
                            }
                            CostCardAdapter adapter = new CostCardAdapter(CostCardActivity.this, list);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(CostCardActivity.this);
                            ccRecycle.setLayoutManager(manager);
                            ccRecycle.setAdapter(adapter);
                            totalClosing.setText(String.valueOf(object.getInt("Total stock")));
                            totalExcise.setText(String.valueOf(object.getDouble("total_excise")));
                            totalWsp.setText(String.valueOf(object.getDouble("total_wsp")));
                            totalCustom.setText(String.valueOf(object.getDouble("total_custom")));
                            totalAmount.setText(String.valueOf(object.getDouble("total_cost")));
//                            list.add(new CostCardModel("Total",1,object.getInt("Total stock"),
//                                    object.getDouble("total_excise"),
//                                    object.getDouble("total_wsp"),
//                                    object.getDouble("total_custom"),
//                                    object.getDouble("total_cost")));

                        }else {
                            noDataView.setVisibility(View.VISIBLE);
                            download.setVisibility(View.GONE);
                            Toast.makeText(CostCardActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    IposProgress.stop();
                    download.setVisibility(View.GONE);
                    Toast.makeText(CostCardActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }){
                @NotNull
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("API", "COST_CARD_REPORT");
                    map.put("shop_id", shopId);
                    map.put("date", date);
                    map.put("brand", brand);
                    return map;
                }
            };
            VolleyController.getInstance(this).addToRequestQueue(request);
        }
    }

    // create pdf

    public static void costCardContent(Document document) throws DocumentException {
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
        Paragraph datePara = new Paragraph("Cost Card Report",smallBold);
        datePara.add(new Chunk(glue));
        datePara.add("Date: "+date);
        document.add(datePara);

        Paragraph subPara = new Paragraph("\n", subFont);
        costCardTable(subPara);
        document.add(subPara);

        Paragraph paragraph = new Paragraph();
        emptyLine(paragraph, 5);

    }
    public static void costCardTable(Paragraph paragraph)
            throws DocumentException {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        float[] columnWidths = new float[]{10f, 25f, 10f, 11f, 11f, 11f, 11f, 11f};
        table.setWidths(columnWidths);

        PdfPCell c1 = new PdfPCell(new Phrase("SNo."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Brand Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Size"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Closing Balance"));
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

        c1 = new PdfPCell(new Phrase("Cost"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i< list.size(); i++){

            CostCardModel model = list.get(i);

            table.addCell(String.valueOf(i+1));
            table.addCell(model.getBrandName());
            table.addCell(model.getSizeValue().toString());
            table.addCell(model.getTotal().toString());
            table.addCell(model.getExcisePrice().toString());
            table.addCell(model.getWsp().toString());
            table.addCell(model.getCustomDuty().toString());
            table.addCell(model.getCostPrice().toString());
        }

        table.addCell("");
        table.addCell("");
        table.addCell("Total");
        table.addCell(totalClosing.getText().toString());
        table.addCell(totalExcise.getText().toString());
        table.addCell(totalWsp.getText().toString());
        table.addCell(totalCustom.getText().toString());
        table.addCell(totalAmount.getText().toString());
        // set whole table into paragraph
        paragraph.add(table);
    }
}
