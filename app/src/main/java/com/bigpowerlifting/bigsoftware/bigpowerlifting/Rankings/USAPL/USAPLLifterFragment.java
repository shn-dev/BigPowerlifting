package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.Federation;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.RankingsActivity;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Networker;
import com.devspark.progressfragment.ProgressFragment;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class USAPLLifterFragment extends ProgressFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String LIFTER_NAME = "lifter-name";
    private int mColumnCount = 1;
    private String mLifterName = "N/A";
    private final String TAG = "Lifter-Fragment";

    private USAPL mUSAPL;
    private View mContentView;
    private Networker mNetworker;
    private MyUSAPLLifterRecyclerViewAdapter mAdapter;
    private RankingsActivity mActivity;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public USAPLLifterFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static USAPLLifterFragment newInstance(int columnCount, String name) {
        USAPLLifterFragment fragment = new USAPLLifterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(LIFTER_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = ((RankingsActivity)getActivity());
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mNetworker = mActivity.getNetworker();
            mUSAPL = mActivity.getUSAPLInstance();
            mLifterName = getArguments().getString(LIFTER_NAME);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(mContentView);
        Log.d(TAG, "onActivityCreated: Trying to get USAPL lifter competitions");
        obtainData();
    }

    //Obtains the hashmap of names/IDs (should already be available as a local file)
    //Once obtained, get lifter info.

    private void obtainData(){
        setContentShown(false);
        if(mUSAPL != null){
            mUSAPL.getAutoFillNames(namesAndIDs -> {
                if (namesAndIDs.containsKey(mLifterName)) {
                    String lifterId = namesAndIDs.get(mLifterName);
                    mUSAPL.getLifterInfo(lifterId, info -> {
                        //Add lifter data to view. Adapter is notified of data change inside of bindItems(...)
                        mAdapter.bindItems(info);
                        //Show the view
                        setContentShown(true);
                    });
                }
            }, getContext());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mNetworker != null){
            mNetworker.cancelAllRequests();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        mContentView = inflater.inflate(R.layout.fragment_usapllifter_list, container, false);
        RecyclerView rv = mContentView.findViewById(R.id.list);
        TextView nameTV = mContentView.findViewById(R.id.USAPLLifterName);
        nameTV.setText(mLifterName);
        // Set the adapter
        if (rv != null) {
            Context context = rv.getContext();
            if (mColumnCount <= 1) {
                rv.setLayoutManager(new LinearLayoutManager(context));
            } else {
                rv.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyUSAPLLifterRecyclerViewAdapter(null, mListener);
            rv.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
        void onListFragmentInteraction(Federation.Competition item);
    }
}
