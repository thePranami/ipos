package com.loopin.ipos.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static com.loopin.ipos.Utils.IposConst.bList;
import static com.loopin.ipos.Utils.IposConst.brandSpinner;
import static com.loopin.ipos.Utils.IposConst.catSpinner;
import static com.loopin.ipos.Utils.IposConst.snList;
import static com.loopin.ipos.Utils.IposConst.splrSpinner;
import static com.loopin.ipos.Utils.IposConst.typeSpinner;
import com.loopin.ipos.Fragment.SummaryFragment;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.Internet;
import com.loopin.ipos.Utils.IposConst;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


import static com.loopin.ipos.Utils.ExtraUtils.setBrandName;
import static com.loopin.ipos.Utils.ExtraUtils.setSupplierName;

public class PurchaseViewActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ImageView backFromPurchase;
    private Spinner reportSpinner, typeExcSpinner;
    private TextView shopName, shopAddress, reportName, spinnerText,
            fromDate, toDate, date, viewText, getDetail, getSummary;
    private LinearLayout fromToLine, ftDateLine, oneDate, ltcSubLine,
            typeLinear, categoryLinear, splrLinear, brandLinear, typeExcLinear;
    private RelativeLayout mainRelative;
    private CardView purchaseCard, purchaseRegisterCard;

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
        setContentView(R.layout.activity_purchase_view);

        init();
        mainRelative.setVisibility(View.GONE);
        setBrandName(this, IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""));
        setSupplierName(this, IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""));
    }

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init() {
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();

        shopName = findViewById(R.id.shopName);
        shopName.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME,""));

        shopAddress = findViewById(R.id.shopAddress);
        shopAddress.setText(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_ADDRESS,"")
                +", "+IposConst.sharedPreferences.getString(IposConst.Keys.PIN_CODE,""));

        purchaseCard = findViewById(R.id.purchaseCard);
        purchaseCard.setOnClickListener(this);
        purchaseRegisterCard = findViewById(R.id.purchaseRegisterCard);
        purchaseRegisterCard.setOnClickListener(this);

        reportSpinner = findViewById(R.id.reportSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        catSpinner = findViewById(R.id.catSpinner);
        reportName = findViewById(R.id.reportName);
        typeExcSpinner = findViewById(R.id.typeExcSpinner);

        spinnerText = findViewById(R.id.spinnerText);
        viewText = findViewById(R.id.viewText);
        getSummary = findViewById(R.id.getSummary);
        getSummary.setOnClickListener(this);
        getDetail = findViewById(R.id.getDetail);
        getDetail.setOnClickListener(this);

        splrSpinner = findViewById(R.id.splrSpinner);
        brandSpinner = findViewById(R.id.brandSpinner);

        backFromPurchase = findViewById(R.id.backFromPurchase);
        backFromPurchase.setOnClickListener(this);

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

        bList = new ArrayList<>();
        snList = new ArrayList<>();
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
            case R.id.purchaseCard:
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Purchase Report");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                splrLinear.setVisibility(View.VISIBLE);
                brandLinear.setVisibility(View.GONE);
                break;
            case R.id.purchaseRegisterCard:
                mainRelative.setVisibility(View.VISIBLE);
                viewText.setText("Purchase Register");
                fromToLine.setVisibility(View.VISIBLE);
                ftDateLine.setVisibility(View.VISIBLE);
                oneDate.setVisibility(View.GONE);
                typeLinear.setVisibility(View.GONE);
                categoryLinear.setVisibility(View.GONE);
                splrLinear.setVisibility(View.VISIBLE);
                brandLinear.setVisibility(View.GONE);
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
                    //bundle.putString("lt", typeSpinner.getSelectedItem().toString());
                    //bundle.putString("lc", catSpinner.getSelectedItem().toString());
                    bundle.putString("brand", brandSpinner.getSelectedItem().toString());
                    bundle.putString("supplier", splrSpinner.getSelectedItem().toString());
                    sf.setArguments(bundle);
                    sf.show(getSupportFragmentManager(), "fr");
                }
                break;

            case R.id.getDetail:
                if (!Internet.isNetAvailable(this)){
                    Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
                }else {
                    switch (viewText.getText().toString()){
                        case "Purchase Report":
                            intent = new Intent(PurchaseViewActivity.this, TPReportActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("supplier", splrSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Purchase Register":
                            intent = new Intent(PurchaseViewActivity.this, PurchaseRegisterActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("supplier", splrSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Supplier Wise Purchase":
                            intent = new Intent(PurchaseViewActivity.this, SupplierWiseActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("supplier", splrSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;
                        case "Brand Wise Purchase":
                            intent = new Intent(PurchaseViewActivity.this, BrandWiseActivity.class);
                            intent.putExtra("fDate",fromDate.getText().toString());
                            intent.putExtra("tDate",toDate.getText().toString());
                            intent.putExtra("brand", brandSpinner.getSelectedItem().toString());
                            startActivity(intent);
                            break;

                    }
                }
                break;
            case R.id.backFromPurchase:
                //onBackPressed();
                finish();
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
