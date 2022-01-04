package com.loopin.ipos.Utils;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Paragraph;
import com.loopin.ipos.Activity.PurchaseViewActivity;
import com.loopin.ipos.Model.BrandResponse;
import com.loopin.ipos.Model.CategoryResponse;
import com.loopin.ipos.Model.SupplierResponse;
import com.loopin.ipos.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.loopin.ipos.Utils.IposConst.bList;
import static com.loopin.ipos.Utils.IposConst.brandSpinner;
import static com.loopin.ipos.Utils.IposConst.catSpinner;
import static com.loopin.ipos.Utils.IposConst.lcList;
import static com.loopin.ipos.Utils.IposConst.snList;
import static com.loopin.ipos.Utils.IposConst.splrSpinner;
import static com.loopin.ipos.Utils.IposConst.typeList;
import static com.loopin.ipos.Utils.IposConst.typeSpinner;
public class ExtraUtils {

    public static Date currentTime = Calendar.getInstance().getTime();

    public static SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault());
    public static String currentDateTime = sdf.format(new Date());

    public static void emptyLine(Paragraph paragraph, int n) {
        for (int i = 0; i < n; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static void appUpdateMethod(Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        assert packageInfo != null;
        String currentVersion = packageInfo.versionName;
        new AppUpdateAsync(currentVersion, context).execute();
    }


    public static boolean checkStoragePermissions(Activity context) {
        String[] storagePerm = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int result;
        List<String> permList = new ArrayList<>();
        for (String p : storagePerm) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permList.add(p);
            }
        }
        if (!permList.isEmpty()) {
            ActivityCompat.requestPermissions(context, permList.toArray(new String[0]), IposConst.storageReqCode);
            return false;
        }
        return true;
    }


    static void CommonDialog(Context context, String yes, String no,
                             String title, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                yes,
                (dialog, id) -> {

                    String path = Environment.getExternalStorageDirectory() + "/" + "Download/IposData" + "/";
                    Uri uri = Uri.parse(path);
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setDataAndType(uri, "*/*");
                    context.startActivity(Intent.createChooser(intent, "Select File Manager"));
                });

        builder1.setNegativeButton(
                no,
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public static void setBrandName(Activity context, String shop_id) {
        bList.clear();
        IposProgress.transParentDialog(context);
        RetrofitController retrofitController = new RetrofitController(context);
        Call<BrandResponse> call = retrofitController.getBrand("GET_BRAND", shop_id);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()){
                    if (response.body().getError().equals("0")){
                        bList.add("All");
                        for (int i = 0; i<response.body().getData().size(); i++){
                            bList.add(response.body().getData().get(i).getBrandName());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.custom_spinner, bList);
                        adapter.setDropDownViewResource(R.layout.drop_down_list);
                        brandSpinner.setAdapter(adapter);
                        IposProgress.stop();
                    }else {
                        IposProgress.stop();
                        Toast.makeText(context, "Data loading error please try again!", Toast.LENGTH_SHORT).show();
                        //context.finish();

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BrandResponse> call, @NotNull Throwable t) {
                Log.d("checkList....?", t.getMessage());
                IposProgress.stop();
            }
        });
    }


    public static void setSupplierName(Activity context, String shopId) {
        snList.clear();
        IposProgress.transParentDialog(context);
        RetrofitController retrofitController = new RetrofitController(context);
        Call<SupplierResponse> call = retrofitController.getSupplier("GET_SUPPLIER", shopId);
        call.enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(@NotNull Call<SupplierResponse> call, Response<SupplierResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()){
                    if (response.body().getError().equals("0")){
                        IposProgress.stop();
                        snList.add("All");
                        for (int i = 0; i<response.body().getData().size(); i++){
                            snList.add(response.body().getData().get(i).getSuppName());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.custom_spinner, snList);
                        adapter.setDropDownViewResource(R.layout.drop_down_list);
                        splrSpinner.setAdapter(adapter);
                    }else {
                        IposProgress.stop();
                        Toast.makeText(context, "Data loading error please try again!", Toast.LENGTH_SHORT).show();
                        //context.finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                IposProgress.stop();
                Log.d("checkList....?", t.getMessage());
                Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setLiquorType(Activity context) {
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.custom_spinner, typeList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        typeSpinner.setAdapter(adapter);
    }

    public static void setLiquorCategory(Activity context) {
        lcList.clear();
        IposProgress.transParentDialog(context);
        RetrofitController retrofitController = new RetrofitController(context);
        Call<CategoryResponse> call = retrofitController.getCategory("GET_LIQUOR_TYPE");
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()){
                    if (response.body().getError().equals("0")){
                        IposProgress.stop();
                        lcList.add("All");
                        for (int i = 0; i<response.body().getData().size(); i++){
                            lcList.add(response.body().getData().get(i).getLookupValue());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.custom_spinner, lcList);
                        adapter.setDropDownViewResource(R.layout.drop_down_list);
                        catSpinner.setAdapter(adapter);
                    }else {
                        IposProgress.stop();
                        Toast.makeText(context, "Data loading error please try again!", Toast.LENGTH_SHORT).show();
                        context.finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                IposProgress.stop();
                Log.d("checkList....?", t.getMessage());
            }
        });
    }




}
