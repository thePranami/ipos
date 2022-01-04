package com.loopin.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopin.ipos.R;

public class PromoLoginActivity extends AppCompatActivity {

    private TextView login;
    private EditText companyCode, password;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_login);

        login = findViewById(R.id.login);
        back = findViewById(R.id.back);
        companyCode = findViewById(R.id.companyCode);
        password = findViewById(R.id.password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyCode.getText().toString().isEmpty()){
                    companyCode.setError("Company code ?");
                    companyCode.requestFocus();
                }else if (password.getText().toString().isEmpty()){
                    password.setError("Password ?");
                    password.requestFocus();
                }else if (companyCode.getText().toString().equals("12345")
                && password.getText().toString().equals("12345")){
                    startActivity(new Intent(PromoLoginActivity.this, PromoSaleActivity.class));
                }else {
                    Toast.makeText(PromoLoginActivity.this, "Enter correct credentials...?", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
