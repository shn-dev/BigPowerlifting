package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;
import com.devspark.progressfragment.ProgressFragment;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RankingsResultFragment extends ProgressFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_RANKINGS_ADDRESS = "rankings-address";
    private static final String ARG_FEDERATION = "federation";
    private static final String ARG_DIVISION = "division";
    private static final String ARG_WEIGHT_CLASS = "weightclass";
    private static final String ARG_STATE = "state";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mRankingsAddress = "";
    private String mFederation = "";
    private String mDivision = "";
    private String mWeightclass = "";
    private String mState = "";
    private RankingsResultFragment.OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RankingsResultFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(mContentView);
        obtainData();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((RankingsActivity)getActivity()).getNetworker().cancelAllRequests();
    }

    private void obtainData() {
        // Show indeterminate progress
        setContentShown(false);
        setContentView(mContentView);

        if(mFederation != null && mRankingsAddress != null && mRecyclerAdapter != null){
            if(mFederation.equals(Federation.Federations.USAPL.name())){
                ((RankingsActivity)getActivity()).getUSAPLInstance().getRankings(
                        list->{
                            mRecyclerAdapter.bindData(list);
                            setContentShown(true);
                        },
                        mRankingsAddress
                );
            }
            else if(mFederation.equals(Federation.Federations.USPA.name())){
                //TODO: Get USPA rankings

            }
        }
    }

    @SuppressWarnings("unused")
    public static RankingsResultFragment newInstance(String rankingsAddress, String federation,
                                                     String division, String weightclass, String state) {
        RankingsResultFragment fragment = new RankingsResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        args.putString(ARG_RANKINGS_ADDRESS, rankingsAddress);
        args.putString(ARG_FEDERATION, federation);
        args.putString(ARG_DIVISION, division);
        args.putString(ARG_WEIGHT_CLASS, weightclass);
        args.putString(ARG_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mRankingsAddress = getArguments().getString(ARG_RANKINGS_ADDRESS);
            mFederation = getArguments().getString(ARG_FEDERATION);
            mDivision = getArguments().getString(ARG_DIVISION);
            mWeightclass = getArguments().getString(ARG_WEIGHT_CLASS);
            mState = getArguments().getString(ARG_STATE);
        }
    }

    View mContentView = null;
    RankingsResultRecyclerAdapter mRecyclerAdapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        mContentView = inflater.inflate(R.layout.fragment_rankings_result_list, container, false);

        if(mState != null && mDivision != null && mWeightclass != null){
            ((TextView)mContentView.findViewById(R.id.RankingsState)).setText("State: " + mState);
            ((TextView)mContentView.findViewById(R.id.RankingsDivison)).setText("Divison: " + mDivision+"; ");
            ((TextView)mContentView.findViewById(R.id.RankingsWeightClass)).setText("Weightclass: " + mWeightclass + "; ");
        }

        RecyclerView rv = mContentView.findViewById(R.id.list);
        // Set the adapter
        if (rv != null) {
            Context context = mContentView.getContext();
            if (mColumnCount <= 1) {
                rv.setLayoutManager(new LinearLayoutManager(context));
            } else {
                rv.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mRecyclerAdapter = new RankingsResultRecyclerAdapter(null, mListener);
            rv.setAdapter(mRecyclerAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (RankingsResultFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Federation.Ranking item, Federation.Federations fed);
    }
}
