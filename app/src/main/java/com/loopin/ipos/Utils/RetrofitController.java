package com.loopin.ipos.Utils;

import android.content.Context;

import com.loopin.ipos.Model.BrandResponse;
import com.loopin.ipos.Model.BrandWiseRespone;
import com.loopin.ipos.Model.BreakageModel;
import com.loopin.ipos.Model.BreakageSaleResponse;
import com.loopin.ipos.Model.CategoryResponse;
import com.loopin.ipos.Model.DailySaleResponse;
import com.loopin.ipos.Model.IssueResponse;
import com.loopin.ipos.Model.LoginResponse;
import com.loopin.ipos.Model.LowInventoryModel;
import com.loopin.ipos.Model.PromoSaleModel;
import com.loopin.ipos.Model.SaleDetailReportResponse;
import com.loopin.ipos.Model.StockDetailReportResponse;
import com.loopin.ipos.Model.StockRportResponse;
import com.loopin.ipos.Model.SupplierWiseResponse;
import com.loopin.ipos.Model.SupplierResponse;
import com.loopin.ipos.Model.TPReportResponse;
import com.loopin.ipos.Model.VatReportResponse;
import com.loopin.ipos.Model.ZoneModel;
import com.loopin.ipos.Model.ZoneShopModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController {

    private ApiInterface apiInterface;
    private static RetrofitController retrofitController = null;
    private Retrofit retrofit;

    public static RetrofitController getInstance(Context context){
        if (retrofitController==null){
            retrofitController = new RetrofitController(context);
        }
        return retrofitController;
    }

    private Retrofit getRetrofit(){
        retrofit = new Retrofit.Builder().baseUrl(IposConst.retroUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

//    private Retrofit getRetrofit(){
//        retrofit = new Retrofit.Builder().baseUrl(IposConst.retroUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit;
//    }

    public RetrofitController(Context context){
        apiInterface = getRetrofit().create(ApiInterface.class);
    }

    // call brand
    public Call<BrandResponse> getBrand(String api, String shop_id){
        return apiInterface.callBrand(api, shop_id);
    }
    // call category
    public Call<CategoryResponse> getCategory(String api){
        return apiInterface.callCategory(api);
    }

    // call supplier
    public Call<SupplierResponse> getSupplier(String api, String shopId){
        return apiInterface.callSupplier(api, shopId);
    }

    // call brand
    public Call<LoginResponse> getLogin(String api, String userName, String password, String vendId) {
        return apiInterface.callLogin(api, userName, password, vendId);
    }

    // call brand
    public Call<DailySaleResponse> getDailySale(String api, String shop_id, String date) {
        return apiInterface.callDailySale(api, shop_id, date);
    }

    // call sale
    public Call<DailySaleResponse> getSale(String api, String shop_id, String from_date,
                                           String to_date, String liqType, String liqCat) {
        return apiInterface.callSale(api, shop_id, from_date, to_date, liqType, liqCat);
    }

    // call sale detail
    public Call<SaleDetailReportResponse> getSaleDetail(String api, String shop_id, String from_date,
                                                  String to_date, String liqType, String liqCat) {
        return apiInterface.callSaleDetail(api, shop_id, from_date, to_date, liqType, liqCat);
    }

    // call sale detail
    public Call<VatReportResponse> getVatReport(String api, String shop_id, String from_date,
                                                 String to_date, String liqType ) {
        return apiInterface.callVat(api, shop_id, from_date, to_date, liqType);
    }

    // call tp detail
    public Call<TPReportResponse> getTPReport(String api, String shop_id, String from_date,
                                               String to_date, String supplier ) {
        return apiInterface.callTPR(api, shop_id, from_date, to_date, supplier);
    }

    // call supplier wise brand detail
    public Call<SupplierWiseResponse> getSWB(String api, String shop_id, String from_date,
                                             String to_date, String supplier ) {
        return apiInterface.callSWB(api, shop_id, from_date, to_date, supplier);
    }

    // call brand wise purchase detail
    public Call<BrandWiseRespone> getBWP(String api, String shop_id, String from_date,
                                         String to_date, String brand ) {
        return apiInterface.callBWP(api, shop_id, from_date, to_date, brand);
    }

    // call stock report
    public Call<StockRportResponse> getStockReport(String api, String shop_id,
                                                   String date, String liqType ) {
        return apiInterface.callStock(api, shop_id, date, liqType);
    }

    // call stock detail report
    public Call<StockDetailReportResponse> getStockDetailReport(String api, String shop_id,
                                                                String from_date, String to_date,
                                                                String liqType, String liqCat ) {
        return apiInterface.callStockDetail(api, shop_id, from_date, to_date, liqType, liqCat);
    }

    // call breakage report
    public Call<BreakageModel.BreakageResponse> getBreakageReport(String api, String shop_id,
                                                    String from_date, String to_date,
                                                    String liqType, String liqCat ) {
        return apiInterface.callBreakage(api, shop_id, from_date, to_date, liqType, liqCat);
    }

    // call breakage Sale report
    public Call<BreakageSaleResponse> getBreakageSaleReport(String api, String shop_id,
                                                        String from_date, String to_date,
                                                        String liqType, String liqCat ) {
        return apiInterface.callBreakageSale(api, shop_id, from_date, to_date, liqType, liqCat);
    }

    // call low stock
    public Call<LowInventoryModel.Response> getLowInventory(String api, String shop_id){
        return apiInterface.callLowInventory(api, shop_id);
    }

    // call issue report
    public Call<IssueResponse> getIssueReport(String api, String shop_id,
                                                     String from_date, String to_date,
                                                     String liqType, String liqCat ) {
        return apiInterface.callIssue(api, shop_id, from_date, to_date, liqType, liqCat);
    }

    // admin login
    public Call<ZoneModel.ZoneResponse> zoneWiseLogin(String api, String zone, String zone_id){
        return apiInterface.callZoneLogin(api, zone, zone_id);
    }

    // admin login
    public Call<ZoneShopModel.ZoneShopResponse> zoneWiseShop(String api, String zone_id){
        return apiInterface.callZoneShop(api, zone_id);
    }

    // promo
    public Call<PromoSaleModel.PromoResponse> promoData(String api ){
        return apiInterface.callPromo(api);
    }
}
