package com.loopin.ipos.Activity;

import static com.loopin.ipos.Utils.IposProgress.progressDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopin.ipos.Adapter.PromoSaleAdapter;
import com.loopin.ipos.Model.PromoSaleModel;
import com.loopin.ipos.Model.ZoneShopModel;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.ExtraUtils;
import com.loopin.ipos.Utils.Internet;
import com.loopin.ipos.Utils.IposProgress;
import com.loopin.ipos.Utils.RetrofitController;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoSaleActivity extends AppCompatActivity {

    private ImageView back;
    private RecyclerView promoRec;
    private List<PromoSaleModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_sale);

        back = findViewById(R.id.back);
        promoRec = findViewById(R.id.promoRec);
        list = new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setPromoSale();
    }

    private void setPromoSale(){
        progressDialog(this).setMessage("Data is loading...");
        list.clear();
        if (!Internet.isNetAvailable(PromoSaleActivity.this)){
            Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
        }else {
            RetrofitController retrofitController = new RetrofitController(this);
            Call<PromoSaleModel.PromoResponse> call = retrofitController.promoData("GET_COMPANY_SALE");
            call.enqueue(new Callback<PromoSaleModel.PromoResponse>() {
                @Override
                public void onResponse(@NotNull Call<PromoSaleModel.PromoResponse> call, Response<PromoSaleModel.PromoResponse> response) {
                    assert response.body() != null;
                    if (response.isSuccessful()){
                        IposProgress.stop();
                        for (int i = 0; i<response.body().getData().size(); i++){
                            list.add(new PromoSaleModel(response.body().getData().get(i).getShopName(),
                                    response.body().getData().get(i).getTotalSale(),
                                    response.body().getData().get(i).getLastDaySale()));
                        }
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(PromoSaleActivity.this);
                        PromoSaleAdapter adapter = new PromoSaleAdapter(PromoSaleActivity.this, list);
                        promoRec.setLayoutManager(manager);
                        promoRec.setAdapter(adapter);
                    }else {
                        IposProgress.stop();
                        Toast.makeText(PromoSaleActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PromoSaleModel.PromoResponse> call, Throwable t) {
                    Log.d("checkList....?", t.getMessage());
                    IposProgress.stop();
                    Toast.makeText(PromoSaleActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
