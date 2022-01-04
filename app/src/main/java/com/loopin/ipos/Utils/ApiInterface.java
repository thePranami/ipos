package com.loopin.ipos.Utils;

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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    // Brand
    @POST("index.php")
    @FormUrlEncoded
    Call<BrandResponse> callBrand(@Field("API") String api,
                                           @Field("shop_id") String shop_id);
    // Category
    @POST("index.php")
    @FormUrlEncoded
    Call<CategoryResponse> callCategory(@Field("API") String api );

    // Supplier
    @POST("index.php")
    @FormUrlEncoded
    Call<SupplierResponse> callSupplier(@Field("API") String api,
                                        @Field("shop_id") String shop_id);

    // Login
    @POST("index.php")
    @FormUrlEncoded
    Call<LoginResponse> callLogin(@Field("API") String api,
                                  @Field("username") String username,
                                  @Field("password") String password,
                                  @Field("vend_id") String vend_id);

    // get Daily Sale
    @POST("index.php")
    @FormUrlEncoded
    Call<DailySaleResponse> callDailySale(@Field("API") String api,
                                          @Field("shop_id") String shop_id,
                                          @Field("date") String date);

    // get Sale report
    @POST("index.php")
    @FormUrlEncoded
    Call<DailySaleResponse> callSale(@Field("API") String api,
                                          @Field("shop_id") String shop_id,
                                          @Field("from_date") String fromDate,
                                     @Field("to_date") String toDate,
                                     @Field("liqType") String liqType,
                                     @Field("liqCat") String liqCat);

    // get Sale detail report
    @POST("index.php")
    @FormUrlEncoded
    Call<SaleDetailReportResponse> callSaleDetail(@Field("API") String api,
                                            @Field("shop_id") String shop_id,
                                            @Field("from_date") String fromDate,
                                            @Field("to_date") String toDate,
                                            @Field("liqType") String liqType,
                                            @Field("liqCat") String liqCat);

    // get vat report
    @POST("index.php")
    @FormUrlEncoded
    Call<VatReportResponse> callVat(@Field("API") String api,
                                                  @Field("shop_id") String shop_id,
                                                  @Field("from_date") String fromDate,
                                                  @Field("to_date") String toDate,
                                                  @Field("liqType") String liqType);

    // get tp report
    @POST("index.php")
    @FormUrlEncoded
    Call<TPReportResponse> callTPR(@Field("API") String api,
                                   @Field("shop_id") String shop_id,
                                   @Field("from_date") String fromDate,
                                   @Field("to_date") String toDate,
                                   @Field("supplier") String supplier);

    // get supplier wise brand report
    @POST("index.php")
    @FormUrlEncoded
    Call<SupplierWiseResponse> callSWB(@Field("API") String api,
                                       @Field("shop_id") String shop_id,
                                       @Field("from_date") String fromDate,
                                       @Field("to_date") String toDate,
                                       @Field("supplier") String supplier);

    // get brand wise purchase
    @POST("index.php")
    @FormUrlEncoded
    Call<BrandWiseRespone> callBWP(@Field("API") String api,
                                   @Field("shop_id") String shop_id,
                                   @Field("from_date") String fromDate,
                                   @Field("to_date") String toDate,
                                   @Field("brand") String supplier);
    // get stock report
    @POST("index.php")
    @FormUrlEncoded
    Call<StockRportResponse> callStock(@Field("API") String api,
                                     @Field("shop_id") String shop_id,
                                     @Field("date") String date,
                                     @Field("liqType") String liqType);

    // get stock detail report
    @POST("index.php")
    @FormUrlEncoded
    Call<StockDetailReportResponse> callStockDetail(@Field("API") String api,
                                              @Field("shop_id") String shop_id,
                                                    @Field("from_date") String fromDate,
                                                    @Field("to_date") String toDate,
                                              @Field("liqType") String liqType,
                                                    @Field("liqCat") String liqCat);

    // get breakage report
    @POST("index.php")
    @FormUrlEncoded
    Call<BreakageModel.BreakageResponse> callBreakage(@Field("API") String api,
                                        @Field("shop_id") String shop_id,
                                        @Field("from_date") String fromDate,
                                        @Field("to_date") String toDate,
                                        @Field("liqType") String liqType,
                                        @Field("liqCat") String liqCat);

    // get breakage sale report
    @POST("index.php")
    @FormUrlEncoded
    Call<BreakageSaleResponse> callBreakageSale(@Field("API") String api,
                                            @Field("shop_id") String shop_id,
                                            @Field("from_date") String fromDate,
                                            @Field("to_date") String toDate,
                                            @Field("liqType") String liqType,
                                            @Field("liqCat") String liqCat);

    // get low stock
    @POST("index.php")
    @FormUrlEncoded
    Call<LowInventoryModel.Response> callLowInventory(@Field("API") String api,
                                                       @Field("shop_id") String shop_id);


    // get breakage sale report
    @POST("index.php")
    @FormUrlEncoded
    Call<IssueResponse> callIssue(@Field("API") String api,
                                         @Field("shop_id") String shop_id,
                                         @Field("from_date") String fromDate,
                                         @Field("to_date") String toDate,
                                         @Field("liqType") String liqType,
                                         @Field("liqCat") String liqCat);

    // Admin Login
    @POST("index.php")
    @FormUrlEncoded
    Call<ZoneModel.ZoneResponse> callZoneLogin(@Field("API") String api,
                                           @Field("zone") String zone,
                                           @Field("password") String password);

    // get zone shop
    @POST("index.php")
    @FormUrlEncoded
    Call<ZoneShopModel.ZoneShopResponse> callZoneShop(@Field("API") String api,
                                         @Field("zone_id") String zone_id);

    // Promo
    @POST("index.php")
    @FormUrlEncoded
    Call<PromoSaleModel.PromoResponse> callPromo(@Field("API") String api );

}
