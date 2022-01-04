package com.loopin.ipos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;
import android.os.Bundle;

import com.loopin.ipos.R;

public class SaleBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_book);

        AndroidViewModel model = new AndroidViewModel(new Application());
    }
}
