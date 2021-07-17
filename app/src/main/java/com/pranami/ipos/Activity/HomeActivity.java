package com.pranami.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pranami.ipos.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private List<String> rList;
    private Spinner reportSpinner, typeSpinner, catSpinner, onlyTypeSpinner,
            onlyCatSpinner, splrSpinner, brandSpinner;
    private TextView reportName, spinnerText, fromDate, toDate, date;
    private LinearLayout fromToLine, ftDateLine, oneDate, ltcLine, ltcSubLine,
            onlyType, onlyCategory, splrLine, brandLine;

    private int day;
    private int month;
    private int year;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    static final int fromDateId = 1;
    static final int toDateId = 2;
    static final int dateId = 3;
    int cur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        rList.add("Select report");
        rList.add("Daily Sale");
        rList.add("Daily Sale");
        rList.add("Daily Sale");
        rList.add("New Sale");
        rList.add("Daily Sale");
        rList.add("Daily Sale");
        rList.add("Daily Sale");
        rList.add("New Sale");

        setReport();
        setLiquorType();
        setLiquorCategory();
        setOnlyLiquorType();
        setOnlyLiquorCategory();
        setSupplierName();
        setBrandName();
    }

    private void init() {
        reportSpinner = findViewById(R.id.reportSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        catSpinner = findViewById(R.id.catSpinner);
        reportName = findViewById(R.id.reportName);
        spinnerText = findViewById(R.id.spinnerText);

        onlyCatSpinner = findViewById(R.id.onlyCatSpinner);
        onlyTypeSpinner = findViewById(R.id.onlyTypeSpinner);
        splrSpinner = findViewById(R.id.splrSpinner);
        brandSpinner = findViewById(R.id.brandSpinner);

        fromToLine = findViewById(R.id.fromToLine);
        ftDateLine = findViewById(R.id.ftDateLine);
        oneDate = findViewById(R.id.oneDate);
        ltcLine = findViewById(R.id.ltcLine);
        ltcSubLine = findViewById(R.id.ltcSubLine);

        onlyType = findViewById(R.id.onlyType);
        onlyCategory = findViewById(R.id.onlyCategory);
        splrLine = findViewById(R.id.splrLine);
        brandLine = findViewById(R.id.brandLine);

        fromDate = findViewById(R.id.fromDate);
        fromDate.setOnClickListener(this);
        toDate = findViewById(R.id.toDate);
        toDate.setOnClickListener(this);
        date = findViewById(R.id.date);
        date.setOnClickListener(this);

        rList = new ArrayList<>();
        calendar = Calendar.getInstance(TimeZone.getDefault());

        datePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setReport() {

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        reportSpinner.setAdapter(adapter);
    }

    public void setLiquorType() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        typeSpinner.setAdapter(adapter);
    }

    public void setLiquorCategory() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        catSpinner.setAdapter(adapter);
    }

    public void setOnlyLiquorType() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        onlyTypeSpinner.setAdapter(adapter);
    }

    public void setOnlyLiquorCategory() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        onlyCatSpinner.setAdapter(adapter);
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
                datePickerDialog.show();
                break;
            case R.id.toDate:
                datePickerDialog.show();
                break;
            case R.id.date:
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        if (day < 10 && (month + 1) < 10) {
            fromDate.setText("0" + day + "/0" + (month + 1) + "/" + year);
        } else if (day < 10 && (month + 1) > 9) {
            fromDate.setText("0" + day + "/" + (month + 1) + "/" + year);
        } else if (day > 9 && (month + 1) < 10) {
            fromDate.setText(day + "/0" + (month + 1) + "/" + year);
        } else {
            fromDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }
}
