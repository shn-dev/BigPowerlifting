package com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

import java.util.List;

public class MyMaxEffortResultRecyclerViewAdapter extends RecyclerView.Adapter<MyMaxEffortResultRecyclerViewAdapter.ViewHolder> {

    private final List<MaxEffortLift> mValues;

    public MyMaxEffortResultRecyclerViewAdapter(List<MaxEffortLift> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_maxeffortresult, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).getFormulaType());
        holder.mContentView.setText(String.valueOf(mValues.get(position).getEstimatedMax()) + "lbs");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.maxeffort_methodname_tv);
            mContentView = (TextView) view.findViewById(R.id.maxeffort_methodvalue_tv);
        }
    }
}
