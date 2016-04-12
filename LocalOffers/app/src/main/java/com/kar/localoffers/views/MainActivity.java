package com.kar.localoffers.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kar.localoffers.R;
import com.kar.localoffers.controllers.OffersController;

public class MainActivity extends AppCompatActivity {

    OffersController mOffersController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mOffersController = new OffersController(this);
        mOffersController.initFragments();
    }
}
