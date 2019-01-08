package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FederationSelectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FederationSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FederationSelectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FederationSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FederationSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FederationSelectionFragment newInstance(String param1, String param2) {
        FederationSelectionFragment fragment = new FederationSelectionFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_federation_selection2, container, false);
        ImageView USAPL = v.findViewById(R.id.rankingUSAPLLogo);
        ImageView USPA = v.findViewById(R.id.rankingUSPALogo);

        //Set the images on the ImageViews
        AssetManager am = getContext().getResources().getAssets();
        try {
            InputStream is = am.open("USAPL+Logo.png");
            USAPL.setImageBitmap(BitmapFactory.decodeStream(is));

            is = am.open("USPA-Logo.png");
            USPA.setImageBitmap(BitmapFactory.decodeStream(is));

        } catch (IOException e) {
            e.printStackTrace();
        }

        USAPL.setOnClickListener(c -> {
            onCardViewPressed(Federation.Federations.USAPL);
        });
        USPA.setOnClickListener(c -> {
            onCardViewPressed(Federation.Federations.USPA);
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onCardViewPressed(Federation.Federations fed) {
        if (mListener != null) {
            mListener.onFragmentInteraction(fed);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Federation.Federations fed);
    }
}
