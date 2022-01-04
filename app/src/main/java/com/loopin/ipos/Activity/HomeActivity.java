package com.loopin.ipos.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopin.ipos.Model.BrandResponse;
import com.loopin.ipos.Model.CategoryResponse;
import com.loopin.ipos.Model.SupplierResponse;
import com.loopin.ipos.Model.ZoneShopModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.Internet;
import com.loopin.ipos.Utils.IposConst;
import com.loopin.ipos.Utils.IposProgress;
import com.loopin.ipos.Utils.RetrofitController;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private List<String> rList;
    private List<String> tExcList;
    private List<String> typeList;
    private List<String> lcList;
    private List<String> bList;
    private List<String> snList;
    private List<String> zoneShopList;
    private ImageView dotMenu, backFromStore;
    private PopupMenu popupMenu;
    private Spinner reportSpinner, zoneShopSpinner, typeSpinner, catSpinner, splrSpinner, brandSpinner,
            typeExcSpinner;
    private TextView shopName, shopAddress, reportName, spinnerText, fromDate, toDate, date, viewText, submit;
    private LinearLayout fromToLine, ftDateLine, oneDate, ltcSubLine,
            typeLinear, categoryLinear, splrLinear, brandLinear, typeExcLinear;
    private RelativeLayout mainRelative;

    private int day;
    private int month;
    private int year;
    private Calendar calendar;
    private DatePickerDialog datePicker, fromDatePicker, toDatePicker;
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        //ReportList
        rList.add("Select report"); // 0 position
        rList.add("Daily Sale");
        rList.add("Sale Report");
        rList.add("Sale Detail Report");
        rList.add("Sale Vat Report");
        rList.add("TP Report");
        rList.add("Supplier Wise Brand");  // 6th position
        rList.add("Brand Wise Purchase");
        rList.add("Cost Card Report");
        rList.add("Stock Report");
        rList.add("Stock Detail Report");
        rList.add("Issue Report");         // 11th position
        rList.add("Stock Register");
        rList.add("Excise Register");
        rList.add("Breakage Report");
        rList.add("Breakage Detail Report");
        rList.add("Apply PO");               // 16th position
        // LiquorType
        typeList.add("All");
        typeList.add("Indian Liquor");
        typeList.add("Foreign Liquor");
        // Excise
        tExcList.add("BEER");
        tExcList.add("QPN");

        setReport();
        setZoneShop(IposConst.sharedPreferences.getString(IposConst.Keys.ZONE_ID,""));
        setLiquorType();
        setLiquorCategory();
        setSupplierName(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""));
        setBrandName(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""));
        setExciseType();

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getSelectedItem().toString()){
                    case "Select report":
                        mainRelative.setVisibility(View.GONE);
                        break;
                    case "Daily Sale": // daily sale report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        oneDate.setVisibility(View.VISIBLE);
                        typeLinear.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Sale Report": // sale report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        oneDate.setVisibility(View.VISIBLE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Sale Detail Report":  // sale detail report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Sale Vat Report":  // sale vat report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "TP Report":  // tp report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.VISIBLE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Supplier Wise Brand":  // supplier wise brand
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.VISIBLE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Brand Wise Purchase":  // brand wise purchase
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.VISIBLE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Cost Card Report": // cost card report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.VISIBLE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Stock Report":  // stock report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Stock Detail Report":  // stock detail report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Issue Report":  // issue report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Stock Register":  // stock register
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.GONE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Excise Register":  // excise register
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.VISIBLE);
                        break;
                    case "Breakage Report":  // breakage report
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Breakage Detail Report":  // breakage  detail
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        fromDate.setText(IposConst.firstDate);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                    case "Apply PO":  //apply po
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        typeExcLinear.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init() {
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();

        shopName = findViewById(R.id.shopName);
        shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.ZONE_NAME,""));

        shopAddress = findViewById(R.id.shopAddress);
        shopAddress.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_ADDRESS,"")
                +", "+IposConst.sharedPreferences.getString(IposConst.Keys.PIN_CODE,""));
        reportSpinner = findViewById(R.id.reportSpinner);
        zoneShopSpinner = findViewById(R.id.zoneShopSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        catSpinner = findViewById(R.id.catSpinner);
        reportName = findViewById(R.id.reportName);
        typeExcSpinner = findViewById(R.id.typeExcSpinner);

        spinnerText = findViewById(R.id.spinnerText);
        viewText = findViewById(R.id.viewText);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        splrSpinner = findViewById(R.id.splrSpinner);
        brandSpinner = findViewById(R.id.brandSpinner);
        dotMenu = findViewById(R.id.dotMenu);
        dotMenu.setOnClickListener(this);
        backFromStore = findViewById(R.id.backFromStore);
        backFromStore.setOnClickListener(this);

        fromToLine = findViewById(R.id.fromToLine);
        ftDateLine = findViewById(R.id.ftDateLine);
        oneDate = findViewById(R.id.oneDate);

        typeLinear = findViewById(R.id.typeLinear);
        categoryLinear = findViewById(R.id.categoryLinear);
        splrLinear = findViewById(R.id.splrLinear);
        brandLinear = findViewById(R.id.brandLinear);
        typeExcLinear = findViewById(R.id.typeExcLinear);

        mainRelative = findViewById(R.id.mainRelative);
        fromDate = findViewById(R.id.fromDate);
        fromDate.setOnClickListener(this);
        toDate = findViewById(R.id.toDate);
        toDate.setOnClickListener(this);
        date = findViewById(R.id.date);
        date.setOnClickListener(this);

        rList = new ArrayList<>();
        typeList = new ArrayList<>();
        tExcList = new ArrayList<>();
        lcList = new ArrayList<>();
        bList = new ArrayList<>();
        snList = new ArrayList<>();
        zoneShopList = new ArrayList<>();
        calendar = Calendar.getInstance(TimeZone.getDefault());

        datePicker = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        fromDatePicker = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        toDatePicker = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        date.setText(currentDate);
        toDate.setText(currentDate);
        LocalDate localDate = LocalDate.now();
        String fd = ""+localDate.withDayOfMonth(1); // 2021-07-01
        String y = fd.substring(0, 4);
        String m = fd.substring(5,7);
        String d = fd.substring(8,10);
        Log.d("date", y+m+d);
        IposConst.firstDate = d+"/"+m+"/"+y;
        fromDate.setText(d+"/"+m+"/"+y);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fromDate:
                fromDatePicker.show();
                IposConst.ftdId = "fdId";
                break;
            case R.id.toDate:
                toDatePicker.show();
                IposConst.ftdId = "tdId";
                break;
            case R.id.date:
                datePicker.show();
                IposConst.ftdId = "dId";
                break;
            case R.id.submit:
                if (!Internet.isNetAvailable(this)){
                    Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
                }else {
                    switch (reportSpinner.getSelectedItem().toString()){
                        case "Daily Sale":
                            intent = new Intent(HomeActivity.this, DailySaleActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            startActivity(intent);
                            break;
                        case "Sale Report":
                            intent = new Intent(HomeActivity.this, SaleReportActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            intent.putExtra("lc", catSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Sale Detail Report":
                            intent = new Intent(HomeActivity.this, SaleDetailReportActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            intent.putExtra("lc", catSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Sale Vat Report":
                            intent = new Intent(HomeActivity.this, VatReportActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "TP Report":
                            intent = new Intent(HomeActivity.this, TPReportActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("supplier", splrSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Supplier Wise Brand":
                            intent = new Intent(HomeActivity.this, SupplierWiseActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("supplier", splrSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Brand Wise Purchase":
                            intent = new Intent(HomeActivity.this, BrandWiseActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("brand", brandSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Cost Card Report":
                            intent = new Intent(HomeActivity.this, CostCardActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            intent.putExtra("brand",brandSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Stock Report":
                            intent = new Intent(HomeActivity.this, StockReportActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;

                    }
                }
                break;
            case R.id.backFromStore:
                //onBackPressed();
                finish();
                break;
            case R.id.dotMenu:
                popupMenu = new PopupMenu(HomeActivity.this, dotMenu);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.logout:
                                Toast.makeText(HomeActivity.this, "Download Working!", Toast.LENGTH_SHORT).show();
                                return true;
                                //break;
                            case R.id.exit:
                                finish();
                                return true;
                                //break;
                        }return true;
                    }
                });
                popupMenu.show();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        if (IposConst.ftdId.equals("fdId")){
            if (day < 10 && (month + 1) < 10) {
                fromDate.setText("0" + day + "/0" + (month + 1) + "/" + year);
            } else if (day < 10 && (month + 1) > 9) {
                fromDate.setText("0" + day + "/" + (month + 1) + "/" + year);
            } else if (day > 9 && (month + 1) < 10) {
                fromDate.setText(day + "/0" + (month + 1) + "/" + year);
            } else {
                fromDate.setText(day + "/" + (month + 1) + "/" + year);
            }
        }else if (IposConst.ftdId.equals("tdId")){
            if (day < 10 && (month + 1) < 10) {
                toDate.setText("0" + day + "/0" + (month + 1) + "/" + year);
            } else if (day < 10 && (month + 1) > 9) {
                toDate.setText("0" + day + "/" + (month + 1) + "/" + year);
            } else if (day > 9 && (month + 1) < 10) {
                toDate.setText(day + "/0" + (month + 1) + "/" + year);
            } else {
                toDate.setText(day + "/" + (month + 1) + "/" + year);
            }
        }else if (IposConst.ftdId.equals("dId")){
            if (day < 10 && (month + 1) < 10) {
                date.setText("0" + day + "/0" + (month + 1) + "/" + year);
            } else if (day < 10 && (month + 1) > 9) {
                date.setText("0" + day + "/" + (month + 1) + "/" + year);
            } else if (day > 9 && (month + 1) < 10) {
                date.setText(day + "/0" + (month + 1) + "/" + year);
            } else {
                date.setText(day + "/" + (month + 1) + "/" + year);
            }
        }
    }

    // get all spinner value
    public void setReport() {

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        reportSpinner.setAdapter(adapter);
    }

    public void setLiquorType() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, typeList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        typeSpinner.setAdapter(adapter);
    }

    public void setLiquorCategory() {
        lcList.clear();
        RetrofitController retrofitController = new RetrofitController(this);
        Call<CategoryResponse> call = retrofitController.getCategory("GET_LIQUOR_TYPE");
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()){
                    IposProgress.stop();
                    lcList.add("All");
                    for (int i = 0; i<response.body().getData().size(); i++){
                        lcList.add(response.body().getData().get(i).getLookupValue());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, R.layout.custom_spinner, lcList);
                    adapter.setDropDownViewResource(R.layout.drop_down_list);
                    catSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.d("checkList....?", t.getMessage());
            }
        });
    }

    public void setSupplierName(String shopId) {
        snList.clear();
        RetrofitController retrofitController = new RetrofitController(this);
        Call<SupplierResponse> call = retrofitController.getSupplier("GET_SUPPLIER", shopId);
        call.enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(@NotNull Call<SupplierResponse> call, Response<SupplierResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()){
                    IposProgress.stop();
                    snList.add("All");
                    for (int i = 0; i<response.body().getData().size(); i++){
                        snList.add(response.body().getData().get(i).getSuppName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, R.layout.custom_spinner, snList);
                    adapter.setDropDownViewResource(R.layout.drop_down_list);
                    splrSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                Log.d("checkList....?", t.getMessage());
            }
        });
    }
    public void setBrandName(String shop_id) {
        bList.clear();
        IposProgress.progressDialog(this).setMessage("Data is loading...");
        RetrofitController retrofitController = new RetrofitController(this);
        Call<BrandResponse> call = retrofitController.getBrand("GET_BRAND", shop_id);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()){
                    IposProgress.stop();
                    bList.add("All");
                    for (int i = 0; i<response.body().getData().size(); i++){
                        bList.add(response.body().getData().get(i).getBrandName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, R.layout.custom_spinner, bList);
                    adapter.setDropDownViewResource(R.layout.drop_down_list);
                    brandSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<BrandResponse> call, @NotNull Throwable t) {
                Log.d("checkList....?", t.getMessage());
            }
        });
    }

    public void setExciseType() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, tExcList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        typeExcSpinner.setAdapter(adapter);
    }

    private void setZoneShop(String zoneId){
        zoneShopList.clear();
        RetrofitController retrofitController = new RetrofitController(this);
        Call<ZoneShopModel.ZoneShopResponse> call = retrofitController.zoneWiseShop("ZONE_SHOP", zoneId);
        call.enqueue(new Callback<ZoneShopModel.ZoneShopResponse>() {
            @Override
            public void onResponse(@NotNull Call<ZoneShopModel.ZoneShopResponse> call, Response<ZoneShopModel.ZoneShopResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()){
                    IposProgress.stop();
                    zoneShopList.add("Select Shop");
                    for (int i = 0; i<response.body().getData().size(); i++){
                        zoneShopList.add(response.body().getData().get(i).getShopName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, R.layout.custom_spinner, zoneShopList);
                    adapter.setDropDownViewResource(R.layout.drop_down_list);
                    zoneShopSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ZoneShopModel.ZoneShopResponse> call, Throwable t) {
                Log.d("checkList....?", t.getMessage());
            }
        });
    }
}
