package com.bigpowerlifting.bigsoftware.bigpowerlifting.attemptselection;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;

public class AttemptSelectionActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private AppBarLayout abl;
    private CardView resultsCardView;
    private AttemptSelectionStart ass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attempt_selection);

        abl = findViewById(R.id.appbar);
        resultsCardView = findViewById(R.id.attempt_selection_results_cardview);

        abl.setVisibility(View.GONE);
        resultsCardView.setVisibility(View.GONE);

        ass=AttemptSelectionStart.newInstance("","");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, ass)
                .commit();

    }

    public void swapToAttemptResultsFragment(String squat, String bench, String deadlift){

        abl.setVisibility(View.VISIBLE);
        resultsCardView.setVisibility(View.VISIBLE);

        if(ass!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(ass)
                    .commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), squat, bench, deadlift);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attempt_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_PARAM = "param_arg";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         * "param" corresponds to the estimated max for a given lift.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String param) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putDouble(ARG_PARAM, Double.parseDouble(param));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_attempt_selection, container, false);
            double param = getArguments().getDouble(ARG_PARAM);
            double[] attempts = AttemptSelection.getAttempts(param);
            //TODO Bind attempts to textviews within each tab.
            //TODO Make Seekbar scale to attempts.

            double attempt1mid = (attempts[1]-attempts[0]) + attempts[0];
            double attempt2mid = (attempts[3]-attempts[2]) + attempts[2];
            double attempt3mid = (attempts[5]-attempts[4]) + attempts[4];

            //Seekbar scales from attempts[n] -> attemptmid -> attempts[n+1]


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String squat;
        private String bench;
        private String deadlift;
        public SectionsPagerAdapter(FragmentManager fm, String squat, String bench, String deadlift) {
            super(fm);
            this.squat=squat;
            this.bench = bench;
            this.deadlift = deadlift;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            String param = null;
            switch (position){
                case 0: //squat
                    param = squat;
                break;
                case 1: //bench
                    param = bench;
                break;
                case 2:
                    param = deadlift;
                    break;
            }

            return PlaceholderFragment.newInstance(position + 1, param);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
