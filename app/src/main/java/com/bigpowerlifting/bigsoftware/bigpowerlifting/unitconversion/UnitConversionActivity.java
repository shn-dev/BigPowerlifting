package com.bigpowerlifting.bigsoftware.bigpowerlifting.unitconversion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

public class UnitConversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_conversion);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.unitConversionContainer, UnitConversionFragment.newInstance("", ""))
                .commit();
    }
}
