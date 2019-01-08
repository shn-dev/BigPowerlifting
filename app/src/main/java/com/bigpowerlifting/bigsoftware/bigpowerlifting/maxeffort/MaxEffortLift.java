package com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanesepac on 4/14/18.
 */

public class MaxEffortLift {

    public String getFormulaType() {
        return formulaType;
    }

    public double getEstimatedMax() {
        return estimatedMax;
    }

    private final String formulaType;
    private final double estimatedMax;


    public MaxEffortLift(String formulaType, double estimatedMax){
        this.formulaType = formulaType;
        this.estimatedMax = estimatedMax;
    }
    public static MaxEffortLift of(String formulaType, double estimatedMax){
        return new MaxEffortLift(formulaType, estimatedMax);
    }



    public static List<MaxEffortLift> getMaxEffortList(double weightLifted, int reps, double RPE){

        /*
            RPE versus reps
            Epley,
            Brzycki,
            McGlothin,
            Lombardi,
            Mayhew,
            OConner,
            Wathan,
            average
         */

        List<MaxEffortLift> list = new ArrayList<>();

        list.add(MaxEffortLift.of("RPE v. Reps",
                MaxEffort.EstimatedMaxFormulas.RPEVersusRepsMax(weightLifted,reps, RPE)));
        list.add(MaxEffortLift.of(MaxEffort.EstimatedMaxFormulas.MaxMethods.Epley.name(),
                MaxEffort.EstimatedMaxFormulas.Epley(weightLifted, reps)));
        list.add(MaxEffortLift.of(MaxEffort.EstimatedMaxFormulas.MaxMethods.Brzycki.name(),
                MaxEffort.EstimatedMaxFormulas.Brzycki(weightLifted, reps)));
        list.add(MaxEffortLift.of(MaxEffort.EstimatedMaxFormulas.MaxMethods.McGlothin.name(),
                MaxEffort.EstimatedMaxFormulas.McGlothin(weightLifted, reps)));
        list.add(MaxEffortLift.of(MaxEffort.EstimatedMaxFormulas.MaxMethods.Lombardi.name(),
                MaxEffort.EstimatedMaxFormulas.Lombardi(weightLifted, reps)));
        list.add(MaxEffortLift.of(MaxEffort.EstimatedMaxFormulas.MaxMethods.Mayhew.name(),
                MaxEffort.EstimatedMaxFormulas.OConner(weightLifted, reps)));
        list.add(MaxEffortLift.of(MaxEffort.EstimatedMaxFormulas.MaxMethods.Wathan.name(),
                MaxEffort.EstimatedMaxFormulas.Wathan(weightLifted, reps)));
        list.add(MaxEffortLift.of("Average",
                MaxEffort.EstimatedMaxFormulas.AverageMax(weightLifted, reps)));

        return list;
    }

}
