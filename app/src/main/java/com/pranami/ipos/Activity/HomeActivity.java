package com.pranami.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pranami.ipos.R;
import com.pranami.ipos.Utils.IposConst;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private List<String> rList;
    private List<String> typeList;
    private Spinner reportSpinner, typeSpinner, catSpinner, splrSpinner, brandSpinner;
    private TextView reportName, spinnerText, fromDate, toDate, date, viewText;
    private LinearLayout fromToLine, ftDateLine, oneDate, ltcSubLine,
            typeLinear, categoryLinear, splrLinear, brandLinear;
    private RelativeLayout mainRelative;

    private int day;
    private int month;
    private int year;
    private Calendar calendar;
    private DatePickerDialog datePicker, fromDatePicker, toDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        //ReportList
        rList.add("Select report");
        rList.add("Sale Report");
        rList.add("Sale Detail Report");
        rList.add("Sale Vat Report");
        rList.add("TP Report");
        rList.add("Supplier Wise Brand");
        rList.add("Cost Card Report");
        rList.add("Stock Report");
        rList.add("Stock Detail Report");
        rList.add("Issue Report");
        rList.add("Stock Register");
        rList.add("Excise Register");
        rList.add("Breakage Report");
        rList.add("Breakage Detail Report");
        rList.add("Apply PO");
        // LiquorType
        typeList.add("All");
        typeList.add("Indian Liquor");
        typeList.add("Foreign Liquor");

        setReport();
        setLiquorType();
        setLiquorCategory();
        setSupplierName();
        setBrandName();

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        mainRelative.setVisibility(View.GONE);
                        break;
                    case 1:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        oneDate.setVisibility(View.VISIBLE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 2:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 3:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 4:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.VISIBLE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 5:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.VISIBLE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 6:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.GONE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.GONE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.GONE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 9:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 10:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.VISIBLE);
                        ftDateLine.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 11:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 12:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 13:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                    case 14:
                        viewText.setText(adapterView.getSelectedItem().toString());
                        mainRelative.setVisibility(View.VISIBLE);
                        fromToLine.setVisibility(View.GONE);
                        ftDateLine.setVisibility(View.GONE);
                        typeLinear.setVisibility(View.VISIBLE);
                        oneDate.setVisibility(View.VISIBLE);
                        categoryLinear.setVisibility(View.VISIBLE);
                        splrLinear.setVisibility(View.GONE);
                        brandLinear.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init() {
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        reportSpinner = findViewById(R.id.reportSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        catSpinner = findViewById(R.id.catSpinner);
        reportName = findViewById(R.id.reportName);
        spinnerText = findViewById(R.id.spinnerText);
        viewText = findViewById(R.id.viewText);

        splrSpinner = findViewById(R.id.splrSpinner);
        brandSpinner = findViewById(R.id.brandSpinner);

        fromToLine = findViewById(R.id.fromToLine);
        ftDateLine = findViewById(R.id.ftDateLine);
        oneDate = findViewById(R.id.oneDate);

        typeLinear = findViewById(R.id.typeLinear);
        categoryLinear = findViewById(R.id.categoryLinear);
        splrLinear = findViewById(R.id.splrLinear);
        brandLinear = findViewById(R.id.brandLinear);

        mainRelative = findViewById(R.id.mainRelative);

        fromDate = findViewById(R.id.fromDate);
        fromDate.setOnClickListener(this);
        toDate = findViewById(R.id.toDate);
        toDate.setOnClickListener(this);
        date = findViewById(R.id.date);
        date.setOnClickListener(this);

        rList = new ArrayList<>();
        typeList = new ArrayList<>();
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
    }

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
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        catSpinner.setAdapter(adapter);
    }

    public void setSupplierName() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        splrSpinner.setAdapter(adapter);
    }

    public void setBrandName() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        brandSpinner.setAdapter(adapter);
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
        }
    }

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
