package com.bigpowerlifting.bigsoftware.bigpowerlifting.unitconversion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnitConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnitConversionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public UnitConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnitConversionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnitConversionFragment newInstance(String param1, String param2) {
        UnitConversionFragment fragment = new UnitConversionFragment();
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
        View v =  inflater.inflate(R.layout.fragment_unit_conversion, container, false);

        //SeekBar sb = v.findViewById(R.id.unitCoversionSeekBar);
        final TextView kilosTV = v.findViewById(R.id.kilosTV);
        final TextView poundsTV = v.findViewById(R.id.poundsTV);

        final EditText kilosET = v.findViewById(R.id.unitConversionKilosET);
        final EditText poundsET = v.findViewById(R.id.unitConversionPoundsET);

        kilosET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(kilosET.hasFocus()) {
                    try {
                        double d = Double.parseDouble(charSequence.toString());
                        poundsET.setText(String.valueOf(UnitConversionModel.convertToPounds(d)));
                    }
                    catch (Exception e){
                        poundsET.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        poundsET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(poundsET.hasFocus()) {
                    try {
                        double d = Double.parseDouble(charSequence.toString());
                        kilosET.setText(String.valueOf(UnitConversionModel.convertToKilos(d)));
                    }
                    catch (Exception e){
                        kilosET.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }

}
