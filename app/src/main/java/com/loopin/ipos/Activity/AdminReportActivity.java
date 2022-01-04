package com.loopin.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopin.ipos.R;

import java.util.ArrayList;
import java.util.List;

public class AdminReportActivity extends AppCompatActivity {

    private Spinner zoneSpinner;
    private List<String> zoneList;
    private CardView companyRepCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);
        init();
        setZone();
    }

    public void init(){
        zoneSpinner = findViewById(R.id.zoneSpinner);
        zoneList = new ArrayList<>();
        zoneList.add("ZONE1");
        zoneList.add("ZONE2");
        zoneList.add("ZONE3");
        zoneList.add("ZONE2");
    }

    // get all spinner value
    public void setZone() {

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, zoneList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        zoneSpinner.setAdapter(adapter);
    }
}
