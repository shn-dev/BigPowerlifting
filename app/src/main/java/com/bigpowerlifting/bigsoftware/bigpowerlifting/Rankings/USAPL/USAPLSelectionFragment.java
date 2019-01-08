package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.Federation;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.RankingsActivity;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.RankingsResultFragment;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Networker;
import com.devspark.progressfragment.ProgressFragment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link USAPLSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class USAPLSelectionFragment extends ProgressFragment {



    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "USAPL_RANKINGS_FRAG";
    private String mParam1;
    private String mParam2;
    private LinkedHashMap<String, String> lifterData = null;
    private Networker mNetworker;
    private RankingsActivity mActivity;
    private USAPL mUSAPL;
    private View mContentView;

    public USAPLSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment USAPLSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static USAPLSelectionFragment newInstance(String param1, String param2) {
        USAPLSelectionFragment fragment = new USAPLSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = ((RankingsActivity)getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mNetworker = mActivity.getNetworker();
        mUSAPL = mActivity.getUSAPLInstance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(mContentView);
        loadingStuff();
    }

    private void loadingStuff(){
        final AutoCompleteTextView autoCompleteTextView = mContentView.findViewById(R.id.USAPLLifterEntryET);
        final ProgressBar namesLoadingPB = mContentView.findViewById(R.id.usaplRankingsNameLoadingPB);
        setContentShown(false);
        if(mUSAPL != null) {
            mUSAPL.getAutoFillNames(new USAPL.USAPLNamesListObtained() {
                @Override
                public void namesObtained(LinkedHashMap<String, String> namesAndIDs) {
                    lifterData = namesAndIDs;
                    if(lifterData != null && getContext() != null) { //Need to check if user changed fragment/activity
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, new ArrayList<>(lifterData.keySet()));
                        autoCompleteTextView.setAdapter(adapter);
                        namesLoadingPB.setVisibility(View.GONE);
                        setContentShown(true);
                    }
                }
            }, getContext());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View loadingView = inflater.inflate(R.layout.fragment_progress, container, false);
        mContentView = inflater.inflate(R.layout.fragment_usapl_selection, container, false);

        final AutoCompleteTextView autoCompleteTextView = mContentView.findViewById(R.id.USAPLLifterEntryET);
        final ProgressBar namesLoadingPB = mContentView.findViewById(R.id.usaplRankingsNameLoadingPB);

        Spinner sexSpinner = mContentView.findViewById(R.id.usaplSexSpinner);
        Spinner divisionSpinner = mContentView.findViewById(R.id.usaplDivisonSpinner);
        Spinner exerciseSpinner = mContentView.findViewById(R.id.usaplExerciseSpinner);
        Spinner weightclassSpinner = mContentView.findViewById(R.id.usaplWeightClassSpinner);
        Spinner stateSpinner = mContentView.findViewById(R.id.usaplStateSpinner);
        Spinner yearSpinner = mContentView.findViewById(R.id.usaplYearSpinner);
        Spinner orderbySpinner = mContentView.findViewById(R.id.usaplOrderBySpinner);

        setAdapter(USAPL.getSex(), sexSpinner, null);
        setAdapter(USAPL.getDivision(), divisionSpinner, null);
        setAdapter(USAPL.getExercise(), exerciseSpinner, null);
        setAdapter(USAPL.getWeightclass(), weightclassSpinner, null);
        setAdapter(USAPL.getState(), stateSpinner, null);
        setAdapter(USAPL.getYear(), yearSpinner, null);
        setAdapter(USAPL.getOrderby(), orderbySpinner, null);

        TextView rankingSearchBtn = mContentView.findViewById(R.id.usaplRankingSearchBtn);
        rankingSearchBtn.setOnClickListener(c -> {

            //Format: .../rankings-default?s=m&c=-1&w=&e=-1&st=&y=&o=p
            //s=sex
            //c=division
            //w=weightclass
            //e=exercise
            //st=state
            //y=year
            //o=orderby (points/wilks)
            StringBuilder sb = new StringBuilder(USAPL.Endpoints.USAPLRankingsEndpoint);
            sb.append("s=");
            sb.append(USAPL.getSex().get(sexSpinner.getSelectedItem()));
            sb.append("&c=");
            sb.append(USAPL.getDivision().get(divisionSpinner.getSelectedItem()));
            sb.append("&w=");
            sb.append(USAPL.getWeightclass().get(weightclassSpinner.getSelectedItem()));
            sb.append("&e=");
            sb.append(USAPL.getExercise().get(exerciseSpinner.getSelectedItem()));
            sb.append("&st=");
            //If all is selection, a blank string value is required for the "st" parameter in the address' URI.
            //This if statement accomodates that necessity
            if(!((Integer)USAPL.getState().get(stateSpinner.getSelectedItem()) == null)){
                sb.append(USAPL.getState().get(stateSpinner.getSelectedItem()));
            }
            sb.append("&y=");
            sb.append(USAPL.getYear().get(yearSpinner.getSelectedItem()));
            sb.append("&o=");
            sb.append(USAPL.getOrderby().get(orderbySpinner.getSelectedItem()));

            goToRankingsFragment(sb.toString(), (String)divisionSpinner.getSelectedItem(),
                    (String)weightclassSpinner.getSelectedItem(),
                    (String)stateSpinner.getSelectedItem());
        });

        TextView lifterSearchBtn = mContentView.findViewById(R.id.USAPLLifterSearchBtn);
        lifterSearchBtn.setOnClickListener(c -> {
            if(lifterData == null){
                namesLoadingPB.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Still loading lifter data from server. The first time loading this may take up to 15 seconds.",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Log.d(TAG, "onCreateView: " + lifterData.toString());
                if(!lifterData.containsKey(autoCompleteTextView.getText().toString())){
                    Toast.makeText(getContext(), "Lifter not found. Please check spelling.", Toast.LENGTH_SHORT).show();
                }
                else{
                    //usapl.getLifterInfo(lifterData.get(autoCompleteTextView.getText().toString()));
                    goToLifterFragment(autoCompleteTextView.getText().toString());
                }
            }
        });

        return loadingView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mNetworker != null){
            mNetworker.cancelAllRequests();
        }
    }


    //TODO: Add the following fragment transitions to the back stack.

    private void goToRankingsFragment(String address, String division, String weightclass, String state){
        ((AppCompatActivity)getContext()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rankingsContainer, RankingsResultFragment.newInstance(
                        address,
                        Federation.Federations.USAPL.name(),
                        division,
                        weightclass,
                        state))
                .commit();
    }

    private void goToLifterFragment(String name){
        ((AppCompatActivity)getContext()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rankingsContainer, USAPLLifterFragment.newInstance(1, name))
                .commit();
    }

    private void setAdapter(LinkedHashMap<String, ?> map, Spinner s, Comparator<String> c){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                USAPL.getItemList(map));
        s.setAdapter(arrayAdapter);
    }
}
