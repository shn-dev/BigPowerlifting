package com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaxEffortStartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaxEffortStartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MaxEffortStartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaxEffortStartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MaxEffortStartFragment newInstance(String param1, String param2) {
        MaxEffortStartFragment fragment = new MaxEffortStartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_max_effort_start, container, false);
        final MaxEffortActivity activity = (MaxEffortActivity)getActivity();
        final EditText RPE_ET = v.findViewById(R.id.max_effort_RPE_et);
        final EditText Reps_ET = v.findViewById(R.id.max_effort_reps_et);
        final EditText Weight_ET = v.findViewById(R.id.max_effort_weight_et);
        final TextView calcTV = v.findViewById(R.id.max_effort_go);

        calcTV.setOnClickListener(l-> {
            if(TextUtils.isEmpty(RPE_ET.getText()) ||
                    TextUtils.isEmpty(Reps_ET.getText()) ||
                    TextUtils.isEmpty(Weight_ET.getText())){
                Toast.makeText(activity, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
            }
            else{
                activity.startResultFragment(Weight_ET.getText().toString(),
                        Reps_ET.getText().toString(),
                        RPE_ET.getText().toString());
            }
        });

        return v;
    }

}

