package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USPA;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.Federation;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Networker;

import java.util.List;

/**
 * Created by shanesepac on 6/7/18.
 */

public class USPA extends Federation {


    public USPA(Networker n) {
        super(n);
    }

    @Override
    public List<Competition> getLifterInfo(String userID, LifterInfoObtained infoObtained) {
        return null;
    }

    @Override
    public List<Ranking> getRankings(RankingsObtained rankingsObtained, String address) {
        return null;
    }

}
