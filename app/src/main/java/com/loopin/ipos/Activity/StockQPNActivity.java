package com.loopin.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.loopin.ipos.Adapter.StockQPNAdapter;
import com.loopin.ipos.Model.StockQPNModel;
import com.loopin.ipos.R;

import java.util.ArrayList;
import java.util.List;

import static com.loopin.ipos.Utils.IposConst.totalSVN;
import static com.loopin.ipos.Utils.IposConst.totalSVP;
import static com.loopin.ipos.Utils.IposConst.totalSVQ;

public class StockQPNActivity extends AppCompatActivity {

    private List<StockQPNModel> list;
    private RecyclerView qpnRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_qpn);

        list = new ArrayList<>();
        qpnRecycle = findViewById(R.id.qpnRecycle);
        totalSVQ = findViewById(R.id.totalSVQ);
        totalSVP = findViewById(R.id.totalSVP);
        totalSVN = findViewById(R.id.totalSVN);

        setQpnStock();

    }

    private void setQpnStock(){
        list.add(new StockQPNModel("AKASHI JAPANESE WHISKYL1F20131601",
                "0","23","0", "34", "324", "0", "2345", "231",
                "69", "3", "1", "0", "262545.00", "87515.00", "182640.00"));
        list.add(new StockQPNModel("AKASHI JAPANESE WHISKYL1F20131601",
                "0","23","0", "34", "324", "0", "2345", "231",
                "69", "3", "1", "0", "262545.00", "87515.00", "182640.00"));
        list.add(new StockQPNModel("AKASHI JAPANESE WHISKYL1F20131601",
                "0","23","0", "34", "324", "0", "2345", "231",
                "69", "3", "1", "0", "262545.00", "87515.00", "182640.00"));
        list.add(new StockQPNModel("AKASHI JAPANESE WHISKYL1F20131601",
                "0","23","0", "34", "324", "0", "2345", "231",
                "69", "3", "1", "0", "262545.00", "87515.00", "182640.00"));
        list.add(new StockQPNModel("AKASHI JAPANESE WHISKYL1F20131601",
                "0","23","0", "34", "324", "0", "2345", "231",
                "69", "3", "1", "0", "262545.00", "87515.00", "182640.00"));
        list.add(new StockQPNModel("AKASHI JAPANESE WHISKYL1F20131601",
                "0","23","0", "34", "324", "0", "2345", "231",
                "69", "3", "1", "0", "262545.00", "87515.00", "182640.00"));
        list.add(new StockQPNModel("AKASHI JAPANESE WHISKYL1F20131601",
                "0","23","0", "34", "324", "0", "2345", "231",
                "69", "3", "1", "0", "262545.00", "87515.00", "182640.00"));

        StockQPNAdapter adapter = new StockQPNAdapter(list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        qpnRecycle.setLayoutManager(manager);
        qpnRecycle.setAdapter(adapter);

        totalSVQ.setText("33545.45");
        totalSVP.setText("33545.45");
        totalSVN.setText("33545.45");
        totalSVP.setText("5347.87");
    }
}
