package com.bigpowerlifting.bigsoftware.bigpowerlifting;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.ToolFragment.OnListFragmentInteractionListener;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.tools.ToolContent.Tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Tool} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyToolRecyclerViewAdapter extends RecyclerView.Adapter<MyToolRecyclerViewAdapter.ViewHolder> {

    private final List<Tool> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final RecyclerView mRecyclerView;
    private static int EXPANDED_POSITION = -1;
    private static int PREVIOUS_EXPANDED_POSITION = -1;

    public MyToolRecyclerViewAdapter(List<Tool> items, OnListFragmentInteractionListener listener, RecyclerView rv) {
        mValues = items;
        mListener = listener;
        mRecyclerView = rv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tool, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(holder.mItem.content);
        holder.mTitle.setText(holder.mItem.title);
        holder.mSubTitle.setText(holder.mItem.subtitle);

        //Get image out of assets and load it into the cardview
        AssetManager am = holder.itemView.getContext().getResources().getAssets();
        try {
            InputStream is = am.open(holder.mItem.imageName);
            holder.mImageView.setImageBitmap(BitmapFactory.decodeStream(is));
            // use the input stream as you want
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        holder.mOpenTool.setOnClickListener(c -> {
            Intent i = new Intent(holder.itemView.getContext(), holder.mItem.activity);
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Tool mItem;
        public final TextView mTitle;
        public final TextView mSubTitle;
        public final TextView mContentView;
        public final ImageView mImageView;
        public final LinearLayout mExpandedLayout;
        public final ImageView mArrow;
        public final TextView mOpenTool;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView)view.findViewById(R.id.cardContent);
            mTitle = (TextView)view.findViewById(R.id.cardTitle);
            mSubTitle = (TextView)view.findViewById(R.id.cardSubtitle);
            mImageView = (ImageView)view.findViewById(R.id.tool_ImageView);
            mExpandedLayout = (LinearLayout)view.findViewById(R.id.cardHiddenLayout);
            mArrow = (ImageView)view.findViewById(R.id.cardArrow);
            mOpenTool = (TextView)view.findViewById(R.id.cardOpenTool);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
