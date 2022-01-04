package com.loopin.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopin.ipos.Model.LoginResponse;
import com.loopin.ipos.R;
import com.loopin.ipos.Utils.Internet;
import com.loopin.ipos.Utils.IposConst;
import com.loopin.ipos.Utils.IposProgress;
import com.loopin.ipos.Utils.RetrofitController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView login, adminLogin, loginType;
    private EditText userName, password, vendId;
    private ImageView backFromLogin, animImage;
    private Spinner loginAsSpinner;
    private List<String> lsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        setLoginAs();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.repeat);
        animImage.startAnimation(animation);
        //
        loginAsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String viewSpin = adapterView.getSelectedItem().toString();
                if (viewSpin.equalsIgnoreCase("TECHNICIAN")
                        ||viewSpin.equalsIgnoreCase("SHOP_ADMIN")
                        ||viewSpin.equalsIgnoreCase("USER")
                        ||viewSpin.equalsIgnoreCase("SHOP_SELLER")){
                    loginType.setText("Enter Vend Id*");
                }else if (viewSpin.equalsIgnoreCase("ZONE_ADMIN")){
                    loginType.setText("Enter Zone Number*");
                }else if (viewSpin.equalsIgnoreCase("ZONE_ADMIN")
                ||viewSpin.equalsIgnoreCase("ACCOUNT_ADMIN")){
                    loginType.setText("Enter Company Code*");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setEnabled(false);
                login.setAlpha(0.5f);
                if (userName.getText().toString().isEmpty()||password.getText().toString().isEmpty()
                ||vendId.getText().toString().isEmpty()){
                    login.setEnabled(true);
                    login.setAlpha(1);
                    Toast.makeText(LoginActivity.this, "Enter required field!", Toast.LENGTH_SHORT).show();
                }else {
                    userLogin("LOGIN", userName.getText().toString(),
                            password.getText().toString(), vendId.getText().toString());
                }
            }
        });
        backFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PromoLoginActivity.class));
            }
        });
    }

    @SuppressLint("CommitPrefEdits")
    public void init(){
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, MODE_PRIVATE);
        IposConst.editor = IposConst.sharedPreferences.edit();
        login = findViewById(R.id.login);
        adminLogin = findViewById(R.id.groupAdmin);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        vendId = findViewById(R.id.vendId);
        backFromLogin = findViewById(R.id.backFromLogin);
        animImage = findViewById(R.id.animImage);
        loginAsSpinner = findViewById(R.id.loginAsSpinner);
        lsList = new ArrayList<>();
        lsList.add("TECHNICIAN");
        lsList.add("ZONE_ADMIN");
        lsList.add("COMPANY_ADMIN");
        lsList.add("SHOP_ADMIN");
        lsList.add("USER");
        lsList.add("SHOP_SELLER");
        lsList.add("ACCOUNT_ADMIN");
        loginType = findViewById(R.id.loginType);
    }

    @Override
    protected void onStop() {
        super.onStop();
        login.setEnabled(true);
        login.setAlpha(1);
    }

    public void userLogin(String api, String userName, String password, String vendId){
        if (!Internet.isNetAvailable(LoginActivity.this)){
            login.setEnabled(true);
            login.setAlpha(1);
            Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show();
        }else {
            IposProgress.progressDialog(LoginActivity.this).setMessage("Please wait...");
            RetrofitController controller = new RetrofitController(LoginActivity.this);
            Call<LoginResponse> call = controller.getLogin(api, userName, password, vendId);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()){
                        IposProgress.stop();
                        assert response.body() != null;
                        if (response.body().getError().equals("0")){
                            IposConst.editor.putString(IposConst.Keys.SHOP_KEY, response.body().getData().getShopKey().toString());
                            IposConst.editor.putString(IposConst.Keys.SHOP_NAME, response.body().getData().getShopName());
                            IposConst.editor.putString(IposConst.Keys.SHOP_ADDRESS, response.body().getData().getShopAddress());
                            IposConst.editor.putString(IposConst.Keys.PIN_CODE, response.body().getData().getPinCode());
                            IposConst.editor.putString(IposConst.Keys.USER_NAME, response.body().getData().getUsername());
                            IposConst.editor.putString(IposConst.Keys.USER_TYPE, response.body().getData().getUserType());
                            IposConst.editor.putString(IposConst.Keys.VEND_ID, response.body().getData().getVendId());
                            IposConst.editor.putString(IposConst.Keys.PHONE, response.body().getData().getPhone());
                            IposConst.editor.commit();
                           // Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else {
                            login.setEnabled(true);
                            login.setAlpha(1);
                            Toast.makeText(LoginActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        IposProgress.stop();
                        login.setEnabled(true);
                        login.setAlpha(1);
                        Toast.makeText(LoginActivity.this, R.string.wrong, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("error", t.getMessage());
                    IposProgress.stop();
                    login.setEnabled(true);
                    login.setAlpha(1);
                    Toast.makeText(LoginActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // get all spinner value
    public void setLoginAs() {

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, lsList);
        adapter.setDropDownViewResource(R.layout.drop_down_list);
        loginAsSpinner.setAdapter(adapter);
    }
}
