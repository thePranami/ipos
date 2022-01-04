package com.loopin.ipos.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.loopin.ipos.Activity.BrandWiseActivity;
import com.loopin.ipos.Activity.CostCardActivity;
import com.loopin.ipos.Activity.DailySaleActivity;
import com.loopin.ipos.Activity.SaleDetailReportActivity;
import com.loopin.ipos.Activity.SaleReportActivity;
import com.loopin.ipos.Activity.SupplierWiseActivity;
import com.loopin.ipos.Activity.TPReportActivity;
import com.loopin.ipos.Activity.VatReportActivity;
import com.loopin.ipos.Adapter.BrandWiseAdapter;
import com.loopin.ipos.Adapter.CostCardAdapter;
import com.loopin.ipos.Adapter.DailySaleAdapter;
import com.loopin.ipos.Adapter.SaleDetailReportAdapter;
import com.loopin.ipos.Adapter.SaleReportAdapter;
import com.loopin.ipos.Adapter.SupplierWiseAdapter;
import com.loopin.ipos.Adapter.TPReportAdapter;
import com.loopin.ipos.Adapter.VatReportAdapter;
import com.loopin.ipos.Model.BrandWiseModel;
import com.loopin.ipos.Model.BrandWiseRespone;
import com.loopin.ipos.Model.BreakageModel;
import com.loopin.ipos.Model.CostCardModel;
import com.loopin.ipos.Model.DailySaleModel;
import com.loopin.ipos.Model.DailySaleResponse;
import com.loopin.ipos.Model.IssueResponse;
import com.loopin.ipos.Model.SaleDetailReportModel;
import com.loopin.ipos.Model.SaleDetailReportResponse;
import com.loopin.ipos.Model.StockRportResponse;
import com.loopin.ipos.Model.SupplierWiseModel;
import com.loopin.ipos.Model.SupplierWiseResponse;
import com.loopin.ipos.Model.TPReportModel;
import com.loopin.ipos.Model.TPReportResponse;
import com.loopin.ipos.Model.VatReportModel;
import com.loopin.ipos.Model.VatReportResponse;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.Internet;
import com.loopin.ipos.Utils.IposConst;
import com.loopin.ipos.Utils.IposProgress;
import com.loopin.ipos.Utils.RetrofitController;
import com.loopin.ipos.Utils.VolleyController;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.loopin.ipos.Utils.IposConst.lc;
import static com.loopin.ipos.Utils.IposConst.lt;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends BottomSheetDialogFragment {

    private TextView reportName, date, fromDate, toDate,
                     totalAmt, totalQty, totalExcise, totalWsp, totalVat,
                     totalTcs, totalGross, totalReceive, totalCustom,
                     totalScrapAmount, totalCGst, totalSGst,
                     totalBreakage, totalBreakageSale, totalCase, totalIsStock, totalClosing,
                     totalCost, totalStock, totalSale, ok;
    private LinearLayout multiViewLayout, qtyLayout, saleLayout, tcsLayout, wspLayout,
            costLayout, grossLayout, stockLayout, customLayout,
              scrapLayout, cGstLayout, sGstLayout, exciseLayout,
                         vatLayout, receivingLayout, breakageLayout, breakageSaleLayout, caseLayout,
                         issueLayout, closingLayout, amtLayout,
                         cardLinear, progressLinear;
    private RelativeLayout ftRelative, dateRelative;
    private Bundle bundle;
    private ProgressBar summaryProgress;
    public SummaryFragment() {
        // Required empty public constructor
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        IposConst.sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        reportName = view.findViewById(R.id.reportName);
        date = view.findViewById(R.id.date);
        fromDate = view.findViewById(R.id.fromDate);
        toDate = view.findViewById(R.id.toDate);

        // total view
        totalAmt = view.findViewById(R.id.totalAmt);
        totalQty = view.findViewById(R.id.totalQty);
        totalSale = view.findViewById(R.id.totalSale);
        totalExcise = view.findViewById(R.id.totalExcise);
        totalBreakage = view.findViewById(R.id.totalBreakage);
        totalBreakageSale = view.findViewById(R.id.totalBreakageSale);
        totalWsp = view.findViewById(R.id.totalWsp);
        totalVat = view.findViewById(R.id.totalVat);
        totalTcs = view.findViewById(R.id.totalTcs);
        totalGross = view.findViewById(R.id.totalGross);
        totalReceive = view.findViewById(R.id.totalReceive);
        totalCustom = view.findViewById(R.id.totalCustom);
        totalScrapAmount = view.findViewById(R.id.totalScrapAmount);
        totalCGst = view.findViewById(R.id.totalCGst);
        totalSGst = view.findViewById(R.id.totalSGst);
        totalCase = view.findViewById(R.id.totalCase);
        totalIsStock = view.findViewById(R.id.totalIsStock);
        totalStock = view.findViewById(R.id.totalStock);
        totalCost = view.findViewById(R.id.totalCost);
        totalClosing = view.findViewById(R.id.totalClosing);

        // layouts
        multiViewLayout = view.findViewById(R.id.multiViewLayout);
        ftRelative = view.findViewById(R.id.ftRelative);
        dateRelative = view.findViewById(R.id.dateRelative);
        qtyLayout = view.findViewById(R.id.qtyLayout);
        vatLayout = view.findViewById(R.id.vatLayout);
        tcsLayout = view.findViewById(R.id.tcsLayout);
        wspLayout = view.findViewById(R.id.wspLayout);
        saleLayout = view.findViewById(R.id.saleLayout);
        customLayout = view.findViewById(R.id.customLayout);
        scrapLayout = view.findViewById(R.id.scrapLayout);
        cGstLayout = view.findViewById(R.id.cGstLayout);
        sGstLayout = view.findViewById(R.id.sGstLayout);
        issueLayout = view.findViewById(R.id.issueLayout);
        exciseLayout = view.findViewById(R.id.exciseLayout);
        stockLayout = view.findViewById(R.id.stockLayout);
        receivingLayout = view.findViewById(R.id.receivingLayout);
        breakageLayout = view.findViewById(R.id.breakageLayout);
        breakageSaleLayout = view.findViewById(R.id.breakageSaleLayout);
        caseLayout = view.findViewById(R.id.caseLayout);
        closingLayout = view.findViewById(R.id.closingLayout);
        grossLayout = view.findViewById(R.id.grossLayout);
        costLayout = view.findViewById(R.id.costLayout);
        amtLayout = view.findViewById(R.id.amtLayout);

        cardLinear = view.findViewById(R.id.cardLinear);
        summaryProgress = view.findViewById(R.id.summaryProgress);
        progressLinear = view.findViewById(R.id.progressLinear);

        ok = view.findViewById(R.id.ok);
        bundle = getArguments();

        ok.setOnClickListener(view1 -> dismiss());

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String rName = bundle.getString("report_name");
        String getDate = "", fDate = "", tDate = "", apiDate="", apiToDate="",
                apiFromDate="", lt="", lc="", brand = "", supplier = "";

        try {
            // date
            getDate = bundle.getString("date");
            Log.d("date...", getDate);   //  dd/mm/yyyy
            String d = getDate.substring(0,2);
            String m = getDate.substring(3,5);
            String y = getDate.substring(6,10);
            apiDate = y+"/"+m+"/"+d;
            Log.d("apiDate", apiDate);

            // from date
            fDate = bundle.getString("fDate");
            Log.d("date...", fDate);   //  dd/mm/yyyy
            String fd = fDate.substring(0,2);
            String fm = fDate.substring(3,5);
            String fy = fDate.substring(6,10);
            apiFromDate = fy+"/"+fm+"/"+fd;
            Log.d("apiFromDate", apiFromDate);

            // to date
            tDate = bundle.getString("tDate");
            Log.d("date...", tDate);   //  dd/mm/yyyy
            String td = tDate.substring(0,2);
            String tm = tDate.substring(3,5);
            String ty = tDate.substring(6,10);
            apiToDate = ty+"/"+tm+"/"+td;
            Log.d("apiToDate", apiToDate);

            // type and category

            if (bundle.getString("lt").equalsIgnoreCase("All")){
                lt = bundle.getString("lt");
            }else {
                lt = bundle.getString("lt")+"_Liquor";
            }
            lc = bundle.getString("lc");

            // brand
            brand = bundle.getString("brand");
            supplier = bundle.getString("supplier");

        }catch (Exception e){
            e.printStackTrace();
        }
        switch (rName){
            // from SaleView Activity
            case "Daily Sale Report":
                multiViewLayout.setVisibility(View.GONE);
                ftRelative.setVisibility(View.GONE);
                amtLayout.setVisibility(View.VISIBLE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                setDailySale(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""), apiDate);
                reportName.setText(rName);
                date.setText("Date: "+getDate);
                break;
            case "Date Wise Sale Report":
                ftRelative.setVisibility(View.GONE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.GONE);
                wspLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.VISIBLE);
                setDateWiseSale(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                              apiDate,apiDate, lt, lc);
                reportName.setText(rName);
                date.setText("Date: "+getDate);
                break;
            case "Sale Detail Report":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.VISIBLE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                totalAmt.setVisibility(View.VISIBLE);
                setSaleDetail(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate,apiToDate, lt, lc);
                reportName.setText(rName);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;
            case "Sale Vat Report":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.GONE);
                wspLayout.setVisibility(View.GONE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.VISIBLE);
                vatLayout.setVisibility(View.VISIBLE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.VISIBLE);
                setVatReport(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate,apiToDate, lt);
                reportName.setText(rName);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;

            // from InventoryViewActivity
            case "Cost Card Report":
                dateRelative.setVisibility(View.VISIBLE);
                ftRelative.setVisibility(View.GONE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.VISIBLE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.VISIBLE);
                costLayout.setVisibility(View.VISIBLE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.GONE);
                getCostCard(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiDate, brand);
                reportName.setText(rName);
                date.setText("Date: "+getDate);
                break;

            case "Stock Report":
                dateRelative.setVisibility(View.VISIBLE);
                ftRelative.setVisibility(View.GONE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.VISIBLE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.VISIBLE);
                amtLayout.setVisibility(View.VISIBLE);
                setStockReport(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiDate, lt);
                reportName.setText(rName);
                date.setText("Date: "+getDate);
                break;

                // from purchase report
            case "Purchase Report":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.VISIBLE);
                receivingLayout.setVisibility(View.VISIBLE);
                customLayout.setVisibility(View.VISIBLE);
                tcsLayout.setVisibility(View.VISIBLE);
                breakageLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.VISIBLE);
                setTPReport(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate, apiToDate, supplier );
                reportName.setText(rName);
                date.setText("Date: "+getDate);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;

            case "Supplier Wise Purchase":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.VISIBLE);
                customLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.GONE);
                setSWB(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate, apiToDate, supplier );
                reportName.setText(rName);
                date.setText("Date: "+getDate);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;

            case "Brand Wise Purchase":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.VISIBLE);
                customLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.GONE);
                setBWP(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate, apiToDate, supplier );
                reportName.setText(rName);
                date.setText("Date: "+getDate);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;

            case "Goods Issue Report":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.GONE);
                wspLayout.setVisibility(View.GONE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.VISIBLE);
                issueLayout.setVisibility(View.VISIBLE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.GONE);
                setIssue(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate, apiToDate, lt, lc );
                reportName.setText(rName);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;

            case "Breakage Report":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.VISIBLE);
                breakageSaleLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.VISIBLE);
                setBreakage(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate, apiToDate, lt, lc );
                reportName.setText(rName);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;

            case "Breakage Sale Report":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.GONE);
                exciseLayout.setVisibility(View.VISIBLE);
                wspLayout.setVisibility(View.VISIBLE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.GONE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.VISIBLE);
                scrapLayout.setVisibility(View.GONE);
                cGstLayout.setVisibility(View.GONE);
                sGstLayout.setVisibility(View.GONE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.VISIBLE);
                setBreakageSale(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
                        apiFromDate, apiToDate, lt, lc );
                reportName.setText(rName);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;

            case "Scrap Sale Report":
                dateRelative.setVisibility(View.GONE);
                ftRelative.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.VISIBLE);
                exciseLayout.setVisibility(View.GONE);
                wspLayout.setVisibility(View.GONE);
                saleLayout.setVisibility(View.GONE);
                grossLayout.setVisibility(View.GONE);
                vatLayout.setVisibility(View.GONE);
                receivingLayout.setVisibility(View.GONE);
                customLayout.setVisibility(View.GONE);
                tcsLayout.setVisibility(View.VISIBLE);
                breakageLayout.setVisibility(View.GONE);
                breakageSaleLayout.setVisibility(View.GONE);
                scrapLayout.setVisibility(View.VISIBLE);
                cGstLayout.setVisibility(View.VISIBLE);
                sGstLayout.setVisibility(View.VISIBLE);
                caseLayout.setVisibility(View.GONE);
                issueLayout.setVisibility(View.GONE);
                closingLayout.setVisibility(View.GONE);
                costLayout.setVisibility(View.GONE);
                stockLayout.setVisibility(View.GONE);
                amtLayout.setVisibility(View.VISIBLE);
//                setBreakageSale(IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_KEY,""),
//                        apiFromDate, apiToDate, lt, lc );
                reportName.setText(rName);
                fromDate.setText("From Date: "+fDate);
                toDate.setText("To Date: "+tDate);
                break;
        }
    }

    private void setDailySale(String shop_id, String date){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<DailySaleResponse> call = controller.getDailySale("DAILY_SALE", shop_id, date);
        call.enqueue(new Callback<DailySaleResponse>() {
            @Override
            public void onResponse(Call<DailySaleResponse> call, Response<DailySaleResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalQty.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalAmt.setText(String.valueOf(Double.valueOf(response.body().getTotalAmount())));
                    }else {
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<DailySaleResponse> call, @NotNull Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDateWiseSale(String shop_id, String fDate, String tDate, String lType, String lCat){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<DailySaleResponse> call = controller.getSale("SALE_REPORT", shop_id, fDate, tDate, lType, lCat);
        call.enqueue(new Callback<DailySaleResponse>() {
            @Override
            public void onResponse(Call<DailySaleResponse> call, Response<DailySaleResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalSale.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalAmt.setText(String.valueOf(Double.valueOf(response.body().getTotalAmount())));
                    }else {
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DailySaleResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSaleDetail(String shop_id, String fDate, String tDate, String lType, String lCat){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<SaleDetailReportResponse> call = controller.getSaleDetail("SALE_REPORT", shop_id, fDate, tDate, lType, lCat);
        call.enqueue(new Callback<SaleDetailReportResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<SaleDetailReportResponse> call, Response<SaleDetailReportResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalSale.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalAmt.setText(String.format("%.2f", response.body().getTotalAmount()));
                        totalExcise.setText(Double.toString(response.body().getTotalExcise()));
                        totalWsp.setText(Double.toString(response.body().getTotalWsp()));
                    }else {
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaleDetailReportResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVatReport(String shop_id, String fDate, String tDate, String lType){
        progressLinear.setVisibility(View.GONE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<VatReportResponse> call = controller.getVatReport("VAT_REPORT", shop_id, fDate, tDate, lType);
        call.enqueue(new Callback<VatReportResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<VatReportResponse> call, Response<VatReportResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalAmt.setText(String.format("%.2f",response.body().getSumTotal().doubleValue()));
                        totalGross.setText(String.format("%.2f",response.body().getSumGrossSale().doubleValue()));
                        totalVat.setText(String.format("%.2f",response.body().getSumVat().doubleValue()));
                    }else {
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VatReportResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCostCard(String shopId, String date, String brand){
        if (!Internet.isNetAvailable(Objects.requireNonNull(getActivity()))){
            Toast.makeText(getActivity(), R.string.internet, Toast.LENGTH_SHORT).show();
        }else {
            progressLinear.setVisibility(View.VISIBLE);
            cardLinear.setVisibility(View.GONE);
            StringRequest request = new StringRequest(Request.Method.POST, IposConst.volleyUrl, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("error").equals("0")) {
                            cardLinear.setVisibility(View.VISIBLE);
                            totalClosing.setText(String.valueOf(object.getInt("Total stock")));
                            totalExcise.setText(String.valueOf(object.getDouble("total_excise")));
                            totalWsp.setText(String.valueOf(object.getDouble("total_wsp")));
                            totalCustom.setText(String.valueOf(object.getDouble("total_custom")));
                            totalCost.setText(String.valueOf(object.getDouble("total_cost")));

                        }else {
                            Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
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
            VolleyController.getInstance(getActivity()).addToRequestQueue(request);
        }
    }

    private void setStockReport(String shopId, String date, String liqType){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<StockRportResponse> call = controller.getStockReport("STOCK_REPORT", shopId, date, liqType);
        call.enqueue(new Callback<StockRportResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<StockRportResponse> call, Response<StockRportResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);

                        totalWsp.setText(String.format("%.2f",response.body().getSumTotalWsp()));
                        totalExcise.setText(String.format("%.2f",response.body().getSumTotalExcise()));
                        totalCustom.setText(String.format("%.2f",response.body().getSumTotalCustom().doubleValue()));
                        totalBreakage.setText(response.body().getSumBreakageStock().toString());
                        totalStock.setText(response.body().getSumTotalStock().toString());
                        totalAmt.setText(String.format("%.2f",response.body().getSumTotalAmount()));
                    }else {
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StockRportResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // tp or purchase report

    private void setTPReport(String shop_id, String fDate, String tDate, String supplier){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<TPReportResponse> call = controller.getTPReport("TP_REPORT", shop_id, fDate, tDate, supplier);
        call.enqueue(new Callback<TPReportResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<TPReportResponse> call, Response<TPReportResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalReceive.setText(String.valueOf(response.body().getSumReceiving()));
                        totalExcise.setText(String.format("%.2f", response.body().getSumExcise()));
                        totalWsp.setText(String.format("%.2f", response.body().getSumWsp()));
                        totalCustom.setText(String.format("%.2f", response.body().getSumCustom()));
                        totalVat.setText(String.format("%.2f", response.body().getSumVat()));
                        totalTcs.setText(String.format("%.2f", response.body().getSumTcs()));
                        totalAmt.setText(String.format("%.2f", response.body().getSumTotalAmount()));
                    }else {
                        Toast.makeText(getContext(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TPReportResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSWB(String shop_id, String fDate, String tDate, String supplier){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<SupplierWiseResponse> call = controller.getSWB("SUPP_WISE_REPORT", shop_id, fDate, tDate, supplier);
        call.enqueue(new Callback<SupplierWiseResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<SupplierWiseResponse> call, Response<SupplierWiseResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalReceive.setText(String.valueOf(response.body().getSumReceveing()));
                        totalExcise.setText(String.format("%.2f", response.body().getSumExcise()));
                        totalWsp.setText(String.format("%.2f", response.body().getSumWsp()));
                    }else {
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SupplierWiseResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBWP(String shop_id, String fDate, String tDate, String brand){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<BrandWiseRespone> call = controller.getBWP("BRAND_WISE_REPORT", shop_id, fDate, tDate, brand);
        call.enqueue(new Callback<BrandWiseRespone>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BrandWiseRespone> call, Response<BrandWiseRespone> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalReceive.setText(String.valueOf(response.body().getSumReceveing()));
                        totalExcise.setText(String.format("%.2f", response.body().getSumExcise()));
                        totalWsp.setText(String.format("%.2f", response.body().getSumWsp()));
                    }else {
                        progressLinear.setVisibility(View.GONE);
                        cardLinear.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BrandWiseRespone> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBreakage(String shop_id, String fDate, String tDate, String lt, String lc){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<BreakageModel.BreakageResponse> call = controller.getBreakageReport("BRAKAGE_REPORT", shop_id, fDate, tDate, lt, lc);
        call.enqueue(new Callback<BreakageModel.BreakageResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BreakageModel.BreakageResponse> call, Response<BreakageModel.BreakageResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalAmt.setText(String.format("%.2f",response.body().getTotalAmount()));
                        totalBreakage.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalWsp.setText(String.format("%.2f", response.body().getTotalWsp()));
                        totalExcise.setText(String.format("%.2f", response.body().getTotalExcise()));
                    }else {
                        progressLinear.setVisibility(View.GONE);
                        cardLinear.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BreakageModel.BreakageResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setBreakageSale(String shop_id, String fDate, String tDate, String lt, String lc){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<BreakageModel.BreakageResponse> call = controller.getBreakageReport("BRAKAGE_SALE_REPORT", shop_id, fDate, tDate, lt, lc);
        call.enqueue(new Callback<BreakageModel.BreakageResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BreakageModel.BreakageResponse> call, Response<BreakageModel.BreakageResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalAmt.setText(String.format("%.2f",response.body().getTotalAmount()));
                        totalBreakageSale.setText(String.valueOf(response.body().getTotalQuantity()));
                        totalWsp.setText(String.format("%.2f", response.body().getTotalWsp()));
                        totalExcise.setText(String.format("%.2f", response.body().getTotalExcise()));
                    }else {
                        progressLinear.setVisibility(View.GONE);
                        cardLinear.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BreakageModel.BreakageResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setIssue(String shop_id, String fDate, String tDate, String lt, String lc){
        progressLinear.setVisibility(View.VISIBLE);
        cardLinear.setVisibility(View.GONE);
        RetrofitController controller = new RetrofitController(getActivity());
        Call<IssueResponse> call = controller.getIssueReport("ISSUE_REPORT", shop_id, fDate, tDate, lt, lc);
        call.enqueue(new Callback<IssueResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<IssueResponse> call, Response<IssueResponse> response) {
                if (response.isSuccessful()){
                    progressLinear.setVisibility(View.GONE);
                    cardLinear.setVisibility(View.GONE);
                    if (response.body().getError().equals("0")){
                        cardLinear.setVisibility(View.VISIBLE);
                        totalCase.setText(String.valueOf(response.body().getTotalCase()));
                        totalIsStock.setText(String.valueOf(response.body().getTotalIssue()));
                    }else {
                        progressLinear.setVisibility(View.GONE);
                        cardLinear.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<IssueResponse> call, Throwable t) {
                progressLinear.setVisibility(View.GONE);
                cardLinear.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
