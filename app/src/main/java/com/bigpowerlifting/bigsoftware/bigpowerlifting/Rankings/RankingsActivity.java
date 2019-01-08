package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.R;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL.USAPL;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL.USAPLLifterFragment;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL.USAPLSelectionFragment;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USPA.USPA;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Networker;

public class RankingsActivity extends AppCompatActivity
        implements FederationSelectionFragment.OnFragmentInteractionListener,
        RankingsResultFragment.OnListFragmentInteractionListener,
        USAPLLifterFragment.OnListFragmentInteractionListener
{
    private static USAPL mUSAPL;
    private static USPA mUSPA;
    //Need to destroy mNetworker on context destruction to prevent static memory leak
    private static Networker mNetworker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rankingsContainer, FederationSelectionFragment.newInstance("",""))
                .commit();
    }

    public USAPL getUSAPLInstance(){
        mUSAPL = mUSAPL == null ? new USAPL(mNetworker) : mUSAPL;
        return mUSAPL;
    }

    public USPA getUSPAInstance(){
        mUSPA = mUSPA == null ? new USPA(mNetworker) : mUSPA;
        return mUSPA;
    }

    @Override
    public void onFragmentInteraction(Federation.Federations fed) {
        //TODO: Implement what happens on federation selection
        if(fed== Federation.Federations.USAPL) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rankingsContainer, USAPLSelectionFragment.newInstance(fed.name(), ""))
                    .commit();
        }
        else if(fed == Federation.Federations.USPA){
            Toast.makeText(this, "USPA Support Coming Soon!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListFragmentInteraction(Federation.Ranking item, Federation.Federations fed) {
        //TODO: Implement RankingsResultFragment listener
        //Need to go to lifter search using name in item object to find id.
        if(fed == Federation.Federations.USAPL){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rankingsContainer, USAPLLifterFragment.newInstance(1, item.getName()))
                    .commit();
        }
        else if (fed == Federation.Federations.USPA){
            //TODO: Code what happens when a ranking is selected under USPA
        }
    }

    public Networker getNetworker(){
        mNetworker = mNetworker == null ? new Networker(this): mNetworker;
        return mNetworker;
    }


    @Override
    public void onListFragmentInteraction(Federation.Competition item) {
        //TODO: Implement interface when USAPL lifter item is interacted with
    }
}
