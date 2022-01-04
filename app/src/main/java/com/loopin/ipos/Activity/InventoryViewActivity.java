package com.loopin.ipos.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopin.ipos.Fragment.StockDetailFragment;
import com.loopin.ipos.Fragment.SummaryFragment;
import com.loopin.ipos.Model.BrandResponse;
import com.loopin.ipos.Model.CategoryResponse;
import com.loopin.ipos.Model.SupplierResponse;
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

import static com.loopin.ipos.Utils.ExtraUtils.setBrandName;
import static com.loopin.ipos.Utils.ExtraUtils.setLiquorCategory;
import static com.loopin.ipos.Utils.ExtraUtils.setLiquorType;
import static com.loopin.ipos.Utils.IposConst.bList;
import static com.loopin.ipos.Utils.IposConst.brandSpinner;
import static com.loopin.ipos.Utils.IposConst.catSpinner;
import static com.loopin.ipos.Utils.IposConst.lcList;
import static com.loopin.ipos.Utils.IposConst.typeList;
import static com.loopin.ipos.Utils.IposConst.typeSpinner;

public class InventoryViewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener {

    private CardView costCard, stockCard, stockDetailCard, stockLedgerCard, stockQPNCard;

    private ImageView backFromInventory;
    private TextView shopName, shopAddress, reportName, fromDate, toDate, date,
            viewText, getSummary, getDetail;
    private LinearLayout fromToLine, ftDateLine, oneDate, ltcSubLine,
            typeLinear, categoryLinear, brandLinear;
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
        setContentView(R.layout.activity_inventory_view);

        init();
        // LiquorType
        typeList.add("All");
        typeList.add("Indian");
        typeList.add("Foreign");

        mainRelative.setVisibility(View.GONE);
        setBrandName(this, IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""));
        setLiquorType(this);
        setLiquorCategory(this);

        Log.d("dbfryuetgyueh", IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""));

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
            case R.id.costCard:
                setBackGround("CostCard",0, Color.WHITE);
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Cost Card Report");
                fromToLine.setVisibility(View.GONE);
                ftDateLine.setVisibility(View.GONE);
                oneDate.setVisibility(View.VISIBLE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                brandLinear.setVisibility(View.VISIBLE);
                break;
            case R.id.stockCard:
                setBackGround("StockCard",0, Color.WHITE);
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Stock Report");
                fromToLine.setVisibility(View.GONE);
                ftDateLine.setVisibility(View.GONE);
                oneDate.setVisibility(View.VISIBLE);
                typeLinear.setVisibility(View.VISIBLE);
                categoryLinear.setVisibility(View.GONE);
                brandLinear.setVisibility(View.GONE);
                break;
            case R.id.stockDetailCard:
                setBackGround("StockDetailCard",0, Color.WHITE);
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Stock Detail Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.VISIBLE);
                categoryLinear.setVisibility(View.VISIBLE);
                brandLinear.setVisibility(View.GONE);
                break;
            case R.id.stockLedgerCard:
                setBackGround("StockLedgerCard",0, Color.WHITE);
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Stock Ledger Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                brandLinear.setVisibility(View.GONE);
                break;
            case R.id.stockQpnCard:
                setBackGround("StockQPNCard",0, Color.WHITE);
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Stock QPN Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                brandLinear.setVisibility(View.GONE);
                break;
            case R.id.getSummary:
                if (!Internet.isNetAvailable(this)){
                    Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
                }else if (viewText.getText().toString().equals("Stock Detail Report")){
                    StockDetailFragment sdf = new StockDetailFragment();
                    //sf.setStyle(R.style.AppTheme, R.style.Theme_Transparent);
                    Bundle bundle = new Bundle();
                    bundle.putString("report_name", viewText.getText().toString());
                    bundle.putString("date", date.getText().toString());
                    bundle.putString("fDate",fromDate.getText().toString());
                    bundle.putString("tDate",toDate.getText().toString());
                    bundle.putString("lt", typeSpinner.getSelectedItem().toString());
                    bundle.putString("lc", catSpinner.getSelectedItem().toString());
                    bundle.putString("brand", brandSpinner.getSelectedItem().toString());
                    sdf.setArguments(bundle);
                    sdf.show(getSupportFragmentManager(), "fr");
                }else {
                    SummaryFragment sf = new SummaryFragment();
                    //sf.setStyle(R.style.AppTheme, R.style.Theme_Transparent);
                    Bundle bundle = new Bundle();
                    bundle.putString("report_name", viewText.getText().toString());
                    bundle.putString("date", date.getText().toString());
                    bundle.putString("fDate",fromDate.getText().toString());
                    bundle.putString("tDate",toDate.getText().toString());
                    bundle.putString("lt", typeSpinner.getSelectedItem().toString());
                    bundle.putString("lc", catSpinner.getSelectedItem().toString());
                    bundle.putString("brand", brandSpinner.getSelectedItem().toString());
                    sf.setArguments(bundle);
                    sf.show(getSupportFragmentManager(), "fr");
                }
                break;

            case R.id.getDetail:
                if (!Internet.isNetAvailable(this)){
                    Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
                }else {
                    switch (viewText.getText().toString()){
                        case "Cost Card Report":
                            intent = new Intent(InventoryViewActivity.this, CostCardActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            intent.putExtra("brand", brandSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Stock Report":
                            intent = new Intent(InventoryViewActivity.this, StockReportActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Stock Detail Report":
                            intent = new Intent(InventoryViewActivity.this, StockDetailReportActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            intent.putExtra("lc", catSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Stock QPN Report":
                            intent = new Intent(InventoryViewActivity.this, StockQPNActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            startActivity(intent);
                            break;
                        case "Stock Ledger Report":
                            intent = new Intent(InventoryViewActivity.this, StockLedgerActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            startActivity(intent);
                            break;

                    }
                }
                break;
            case R.id.backFromInventory:
                //onBackPressed();
                finish();
                break;
        }
    }

        @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void init () {
            IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
            IposConst.editor = IposConst.sharedPreferences.edit();

            mainRelative = findViewById(R.id.mainRelative);
            costCard = findViewById(R.id.costCard);
            stockCard = findViewById(R.id.stockCard);
            stockDetailCard = findViewById(R.id.stockDetailCard);
            stockLedgerCard = findViewById(R.id.stockLedgerCard);
            stockLedgerCard.setOnClickListener(this);
            stockQPNCard = findViewById(R.id.stockQpnCard);
            stockQPNCard.setOnClickListener(this);
            costCard.setOnClickListener(this);
            stockCard.setOnClickListener(this);
            stockDetailCard.setOnClickListener(this);
            shopName = findViewById(R.id.shopName);
            shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME, ""));

            shopAddress = findViewById(R.id.shopAddress);
            shopAddress.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_ADDRESS, "")
                    + ", " + IposConst.sharedPreferences.getString(IposConst.Keys.PIN_CODE, ""));
            typeSpinner = findViewById(R.id.typeSpinner);
            catSpinner = findViewById(R.id.catSpinner);
            reportName = findViewById(R.id.reportName);

            viewText = findViewById(R.id.viewText);
            getSummary = findViewById(R.id.getSummary);
            getSummary.setOnClickListener(this);
            getDetail = findViewById(R.id.getDetail);
            getDetail.setOnClickListener(this);

            brandSpinner = findViewById(R.id.brandSpinner);

            backFromInventory = findViewById(R.id.backFromInventory);
            backFromInventory.setOnClickListener(this);

            fromToLine = findViewById(R.id.fromToLine);
            ftDateLine = findViewById(R.id.ftDateLine);
            oneDate = findViewById(R.id.oneDate);

            typeLinear = findViewById(R.id.typeLinear);
            categoryLinear = findViewById(R.id.categoryLinear);
            brandLinear = findViewById(R.id.brandLinear);
            brandLinear.setOnClickListener(this);

            fromDate = findViewById(R.id.fromDate);
            fromDate.setOnClickListener(this);
            toDate = findViewById(R.id.toDate);
            toDate.setOnClickListener(this);
            date = findViewById(R.id.date);
            date.setOnClickListener(this);

            typeList = new ArrayList<>();
            lcList = new ArrayList<>();
            bList = new ArrayList<>();
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
            String fd = "" + localDate.withDayOfMonth(1); // 2021-07-01
            String y = fd.substring(0, 4);
            String m = fd.substring(5, 7);
            String d = fd.substring(8, 10);
            Log.d("date", y + m + d);
            IposConst.firstDate = d + "/" + m + "/" + y;
            fromDate.setText(d + "/" + m + "/" + y);
        }

        private void setBackGround(String val, int clr, int empty){
            costCard.setCardBackgroundColor(empty);
            stockCard.setCardBackgroundColor(empty);
            stockDetailCard.setCardBackgroundColor(empty);
            stockLedgerCard.setCardBackgroundColor(empty);
            stockQPNCard.setCardBackgroundColor(empty);

            switch (val){
                case "CostCard":
                    costCard.setCardBackgroundColor(clr);
                    break;
                case "StockCard":
                    stockCard.setCardBackgroundColor(clr);
                    break;
                case "StockDetailCard":
                    stockDetailCard.setCardBackgroundColor(clr);
                    break;
                case "StockLedgerCard":
                    stockLedgerCard.setCardBackgroundColor(clr);
                    break;
                case "StockQPNCard":
                    stockQPNCard.setCardBackgroundColor(clr);
                    break;
            }
        }

}
