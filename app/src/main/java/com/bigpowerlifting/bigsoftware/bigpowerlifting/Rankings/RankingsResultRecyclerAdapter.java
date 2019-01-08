package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.RankingsResultFragment.OnListFragmentInteractionListener;

import java.util.List;

public class RankingsResultRecyclerAdapter extends RecyclerView.Adapter<RankingsResultRecyclerAdapter.ViewHolder> {

    private List<Federation.Ranking> mValues;
    private final OnListFragmentInteractionListener mListener;

    public RankingsResultRecyclerAdapter(List<Federation.Ranking> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void bindData(List<Federation.Ranking> list){
        mValues = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_rankings_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Federation.Ranking r = mValues.get(position);
        holder.mItem = r;
        holder.mPlacing.setText(r.getRanking());
        holder.mName.setText(r.getName());
        holder.mTotal.setText(r.getWeight()); //weight refers to weight lifted, not bodyweight, in this context
        holder.mWilks.setText(r.getPoints());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, Federation.Federations.USAPL);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlacing;
        public final TextView mName;
        public final TextView mTotal;
        public final TextView mWilks;

        public Federation.Ranking mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlacing = (TextView)view.findViewById(R.id.RankingsPlacing);
            mName = (TextView)view.findViewById(R.id.RankingsName);
            mTotal = (TextView)view.findViewById(R.id.RankingsTotal);
            mWilks = (TextView)view.findViewById(R.id.RankingsWilks);
        }

    }
}
