package com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaxEffortResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaxEffortResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_WEIGHTLIFTED = "WEIGHTLIFTED";
    private static final String ARG_NUMREPS = "NUMREPS";
    private static final String ARG_RPE = "RPE";
    private static int mColumnCount = 1;

    private static final String ARG_COLUMN_COUNT = "column-count";

    // TODO: Rename and change types of parameters
    private String mWeightLifted;
    private String mNumReps;
    private String mRPE;


    public MaxEffortResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MaxEffortResultFragment newInstance(String weightlifted, String numreps, String rpe) {
        MaxEffortResultFragment fragment = new MaxEffortResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, mColumnCount);
        args.putString(ARG_WEIGHTLIFTED, weightlifted);
        args.putString(ARG_NUMREPS, numreps);
        args.putString(ARG_RPE, rpe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mWeightLifted = getArguments().getString(ARG_WEIGHTLIFTED);
            mNumReps = getArguments().getString(ARG_NUMREPS);
            mRPE = getArguments().getString(ARG_RPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<MaxEffortLift> liftResults = MaxEffortLift.getMaxEffortList(
                Double.parseDouble(mWeightLifted),
                Integer.parseInt(mNumReps),
                Double.parseDouble(mRPE)
        );

        View view = inflater.inflate(R.layout.fragment_maxeffortresult_list, container, false);
        RecyclerView rv = view.findViewById(R.id.list);

        // Set the adapter
        if (rv != null) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                rv.setLayoutManager(new LinearLayoutManager(context));
            } else {
                rv.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            rv.setAdapter(new MyMaxEffortResultRecyclerViewAdapter(liftResults));
        }
        return view;
    }

}
