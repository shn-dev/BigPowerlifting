package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.Federation;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL.USAPLLifterFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyUSAPLLifterRecyclerViewAdapter extends RecyclerView.Adapter<MyUSAPLLifterRecyclerViewAdapter.ViewHolder> {

    private static int EXPANDED_POSITION = -1;
    private static int PREVIOUS_EXPANDED_POSITION = -1;
    private List<Federation.Competition> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyUSAPLLifterRecyclerViewAdapter(List<Federation.Competition> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    //This method is called to update the RecyclerView's items, such as when a networking request is complete. The adapter
    //is then also notified that the data set has changed.
    public void bindItems(List<Federation.Competition> items){
        mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_usapllifter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Federation.Competition c = mValues.get(position);
        holder.mDate.setText(c.getDate());
        holder.mMeetName.setText(c.getName());
        holder.mPlacing.setText(c.getRanking());
        holder.mDivision.setText(c.getDivision());
        holder.mWeightclass.setText(c.getWeightclass());
        holder.mBodyweight.setText(c.getBodyweight());
        adjustColorForLifts(holder.mSquat1, c.getSquat()[0]);
        adjustColorForLifts(holder.mSquat2, c.getSquat()[1]);
        adjustColorForLifts(holder.mSquat3, c.getSquat()[2]);
        adjustColorForLifts(holder.mBench1, c.getBench()[0]);
        adjustColorForLifts(holder.mBench2, c.getBench()[1]);
        adjustColorForLifts(holder.mBench3, c.getBench()[2]);
        adjustColorForLifts(holder.mDeadlift1, c.getDeadlift()[0]);
        adjustColorForLifts(holder.mDeadlift2, c.getDeadlift()[1]);
        adjustColorForLifts(holder.mDeadlift3, c.getDeadlift()[2]);

        holder.mTotal.setText(c.getTotal());
        holder.mWIlks.setText(c.getPoints());


        //Code below makes the card review when the card's arrow is clicked, similar to the Main Activity's tool fragment
        final boolean isExpanded = position== EXPANDED_POSITION;
        holder.mExpandedLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        int arrow = isExpanded ? R.drawable.ic_keyboard_arrow_up_black_24dp : R.drawable.ic_keyboard_arrow_down_black_24dp;
        holder.mArrow.setImageResource(arrow);


        if (isExpanded)
            PREVIOUS_EXPANDED_POSITION = position;

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {

                EXPANDED_POSITION = isExpanded ? -1:position;
                notifyItemChanged(PREVIOUS_EXPANDED_POSITION);
                notifyItemChanged(position);

                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    private void adjustColorForLifts(TextView v, String text){
        v.setText(text);
        int textColor = text.contains("-") ? R.color.failure : R.color.success;
        v.setTextColor(v.getRootView().getContext().getResources().getColor(textColor));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout mExpandedLayout;
        public final ImageView mArrow;
        public final View mView;
        public final TextView mDate;
        public final TextView mMeetName;
        public final TextView mPlacing;
        public final TextView mDivision;
        public final TextView mWeightclass;
        public final TextView mBodyweight;
        public final TextView mSquat1;
        public final TextView mSquat2;
        public final TextView mSquat3;
        public final TextView mBench1;
        public final TextView mBench2;
        public final TextView mBench3;
        public final TextView mDeadlift1;
        public final TextView mDeadlift2;
        public final TextView mDeadlift3;
        public final TextView mTotal;
        public final TextView mWIlks;
        public Federation.Competition mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mExpandedLayout = (LinearLayout)view.findViewById(R.id.cardHiddenLayout);
            mArrow = (ImageView)view.findViewById(R.id.cardArrow);
            mDate = (TextView)view.findViewById(R.id.USAPLLifter_date);
            mMeetName = (TextView)view.findViewById(R.id.USAPLLifter_meetname);
            mPlacing = (TextView)view.findViewById(R.id.USAPLLifter_placing);
            mDivision = (TextView)view.findViewById(R.id.USAPLLifter_division);
            mWeightclass = (TextView)view.findViewById(R.id.USAPLLifter_weightclass);
            mBodyweight = (TextView)view.findViewById(R.id.USAPLLifter_bodyweight);
            mSquat1 = (TextView)view.findViewById(R.id.USAPLLifter_squat1);
            mSquat2 = (TextView)view.findViewById(R.id.USAPLLifter_squat2);
            mSquat3 = (TextView)view.findViewById(R.id.USAPLLifter_squat3);
            mDeadlift1 = (TextView)view.findViewById(R.id.USAPLLifter_deadlift1);
            mDeadlift2 = (TextView)view.findViewById(R.id.USAPLLifter_deadlift2);
            mDeadlift3 = (TextView)view.findViewById(R.id.USAPLLifter_deadlift3);
            mBench1 = (TextView)view.findViewById(R.id.USAPLLifter_bench1);
            mBench2 = (TextView)view.findViewById(R.id.USAPLLifter_bench2);
            mBench3 = (TextView)view.findViewById(R.id.USAPLLifter_bench3);
            mTotal = (TextView)view.findViewById(R.id.USAPLLifter_total);
            mWIlks = (TextView)view.findViewById(R.id.USAPLLifter_wilks);


        }
    }
}
