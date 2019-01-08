package com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

public class MaxEffortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_effort);

        startDataGatherFragment();
    }

    public void startDataGatherFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.maxEffortContainer, MaxEffortStartFragment.newInstance("",""))
                .commit();
    }

    public void startResultFragment(String weightlifted, String numreps, String rpe){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.maxEffortContainer, MaxEffortResultFragment.newInstance(weightlifted,numreps, rpe))
                .commit();

    }
}
