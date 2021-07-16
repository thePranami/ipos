package com.pranami.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pranami.ipos.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private List<String> rList;
    private Spinner reportSpinner;
    private TextView reportName, spinnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        reportSpinner = findViewById(R.id.reportSpinner);
        reportName = findViewById(R.id.reportName);
        spinnerText = findViewById(R.id.spinnerText);
        rList = new ArrayList<>();
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
    }

    public void setReport(){

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, rList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        reportSpinner.setAdapter(adapter);
    }
}
