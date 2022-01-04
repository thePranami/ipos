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

import static com.loopin.ipos.Utils.ExtraUtils.setLiquorCategory;
import static com.loopin.ipos.Utils.ExtraUtils.setLiquorType;
import static com.loopin.ipos.Utils.IposConst.catSpinner;
import static com.loopin.ipos.Utils.IposConst.lcList;
import static com.loopin.ipos.Utils.IposConst.typeList;
import static com.loopin.ipos.Utils.IposConst.typeSpinner;

public class SaleViewActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private CardView dailySaleCard, saleCard, saleDetailCard, saleVatCard,
            saleBookCard, modVatCard, scrapSaleCard;

    private ImageView backFromSale;
    private TextView shopName, shopAddress, reportName, spinnerText, fromDate, toDate, date,
            viewText, getSummary, getDetail;
    private LinearLayout fromToLine, ftDateLine, oneDate, ltcSubLine,
            typeLinear, categoryLinear;
    private RelativeLayout mainRelative;

    Intent intent;

    private int day;
    private int month;
    private int year;
    private Calendar calendar;
    private DatePickerDialog datePicker, fromDatePicker, toDatePicker;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_view);

        init();

        // LiquorType
        typeList.add("All");
        typeList.add("Indian");
        typeList.add("Foreign");


        setLiquorType(this);
        setLiquorCategory(this);
        mainRelative.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init(){
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        dailySaleCard = findViewById(R.id.dailySaleCard);
        dailySaleCard.setOnClickListener(this);
        saleCard = findViewById(R.id.saleCard);
        saleCard.setOnClickListener(this);
        saleDetailCard = findViewById(R.id.saleDetailsCard);
        saleDetailCard.setOnClickListener(this);
        saleVatCard = findViewById(R.id.saleVatCard);
        saleVatCard.setOnClickListener(this);
        modVatCard = findViewById(R.id.modVatCard);
        modVatCard.setOnClickListener(this);
        saleBookCard = findViewById(R.id.saleBookCard);
        saleBookCard.setOnClickListener(this);
        scrapSaleCard = findViewById(R.id.scrapSaleCard);
        scrapSaleCard.setOnClickListener(this);
        mainRelative = findViewById(R.id.mainRelative);
        shopName = findViewById(R.id.shopName);
        shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""));

        shopAddress = findViewById(R.id.shopAddress);
        shopAddress.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_ADDRESS,"")
                +", "+IposConst.sharedPreferences.getString(IposConst.Keys.PIN_CODE,""));
        typeSpinner = findViewById(R.id.typeSpinner);
        catSpinner = findViewById(R.id.catSpinner);
        reportName = findViewById(R.id.reportName);

        spinnerText = findViewById(R.id.spinnerText);
        viewText = findViewById(R.id.viewText);
        getSummary = findViewById(R.id.getSummary);
        getSummary.setOnClickListener(this);
        getDetail = findViewById(R.id.getDetail);
        getDetail.setOnClickListener(this);

        backFromSale = findViewById(R.id.backFromSale);
        backFromSale.setOnClickListener(this);

        fromToLine = findViewById(R.id.fromToLine);
        ftDateLine = findViewById(R.id.ftDateLine);
        oneDate = findViewById(R.id.oneDate);

        typeLinear = findViewById(R.id.typeLinear);
        categoryLinear = findViewById(R.id.categoryLinear);

        mainRelative = findViewById(R.id.mainRelative);
        fromDate = findViewById(R.id.fromDate);
        fromDate.setOnClickListener(this);
        toDate = findViewById(R.id.toDate);
        toDate.setOnClickListener(this);
        date = findViewById(R.id.date);
        date.setOnClickListener(this);

        typeList = new ArrayList<>();
        lcList = new ArrayList<>();
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

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
            case R.id.getSummary:
                if (!Internet.isNetAvailable(this)){
                    Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
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
                    sf.setArguments(bundle);
                    sf.show(getSupportFragmentManager(), "fr");
                }
                break;
            case R.id.getDetail:
                if (!Internet.isNetAvailable(this)){
                    Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
                }else {
                    switch (viewText.getText().toString()){
                        case "Daily Sale Report":
                            intent = new Intent(SaleViewActivity.this, DailySaleActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            startActivity(intent);
                            break;
                        case "Date Wise Sale Report":
                            intent = new Intent(SaleViewActivity.this, SaleReportActivity.class);
                            intent.putExtra("date",date.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            intent.putExtra("lc", catSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Sale Detail Report":
                            intent = new Intent(SaleViewActivity.this, SaleDetailReportActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            intent.putExtra("lc", catSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Sale Vat Report":
                            intent = new Intent(SaleViewActivity.this, VatReportActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("lt", typeSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Scrap Sale Report":
                            intent = new Intent(SaleViewActivity.this, ScrapSaleActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            startActivity(intent);
                            break;

                    }
                }
                break;
            case R.id.backFromSale:
                //onBackPressed();
                finish();
                break;

            case R.id.dailySaleCard:
//                saleCard.setCardBackgroundColor(Color.WHITE);
//                dailySaleCard.setCardBackgroundColor(R.color.colorPrimary);
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Daily Sale Report");
                fromToLine.setVisibility(View.GONE);
                ftDateLine.setVisibility(View.GONE);
                oneDate.setVisibility(View.VISIBLE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                break;
            case R.id.saleCard:
//                saleCard.setCardBackgroundColor(R.color.colorPrimary);
//                dailySaleCard.setCardBackgroundColor(Color.WHITE);
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Date Wise Sale Report");
                fromToLine.setVisibility(View.GONE);
                ftDateLine.setVisibility(View.GONE);
                oneDate.setVisibility(View.VISIBLE);
                typeLinear.setVisibility(View.VISIBLE);
                categoryLinear.setVisibility(View.VISIBLE);
                break;
            case R.id.saleDetailsCard:
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Sale Detail Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.VISIBLE);
                categoryLinear.setVisibility(View.VISIBLE);
                break;
            case R.id.saleVatCard:
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Sale Vat Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.VISIBLE);
                categoryLinear.setVisibility(View.GONE);
                break;
            case R.id.modVatCard:
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("ModVat Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                break;
            case R.id.saleBookCard:
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Sale Book Report");
                fromToLine.setVisibility(View.GONE);
                ftDateLine.setVisibility(View.GONE);
                oneDate.setVisibility(View.VISIBLE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                break;
            case R.id.scrapSaleCard:
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Scrap Sale Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
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
}
