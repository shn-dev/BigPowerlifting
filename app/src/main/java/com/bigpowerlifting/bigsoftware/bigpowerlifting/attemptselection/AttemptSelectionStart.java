package com.bigpowerlifting.bigsoftware.bigpowerlifting.attemptselection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttemptSelectionStart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttemptSelectionStart extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Attempt Selection Start";

    private AttemptSelectionActivity activity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AttemptSelectionStart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttemptSelectionStart.
     */
    // TODO: Rename and change types and number of parameters
    public static AttemptSelectionStart newInstance(String param1, String param2) {
        AttemptSelectionStart fragment = new AttemptSelectionStart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AttemptSelectionActivity)getActivity();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private class Validation{
        private String bench;
        private String squat;
        private String deadlift;
        private boolean validated;

        public Validation(){};
        public Validation(String bench, String squat, String deadlift) {
            this.bench = bench;
            this.squat = squat;
            this.deadlift = deadlift;
            if(!bench.isEmpty() && !squat.isEmpty() && !deadlift.isEmpty()) {
                this.validated = true;
            }
        }

        public String getBench() {
            return bench;
        }

        public void setBench(String bench) {
            this.bench = bench;
        }

        public String getSquat() {
            return squat;
        }

        public void setSquat(String squat) {
            this.squat = squat;
        }

        public String getDeadlift() {
            return deadlift;
        }

        public void setDeadlift(String deadlift) {
            this.deadlift = deadlift;
        }

        public boolean isValidated() {
            return validated;
        }

        public void setValidated(boolean validated) {
            this.validated = validated;
        }
    }
    private Validation isValidated(View v){

        return new Validation(((EditText)v.findViewById(R.id.attempt_select_benchmax_et)).getText().toString(),
                ((EditText)v.findViewById(R.id.attempt_select_squatmax_et)).getText().toString(),
                ((EditText)v.findViewById(R.id.attempt_select_deadliftmax_et)).getText().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_attempt_selection_start, container, false);

        v.findViewById(R.id.attempt_selection_btn).setOnClickListener(l->{
            if(activity != null){
                Validation val = isValidated(v);
                Log.d(TAG, "onCreateView: " + val.getBench());
                if(val.isValidated())
                    activity.swapToAttemptResultsFragment(val.squat, val.bench, val.deadlift);
                else
                    Toast.makeText(activity, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
