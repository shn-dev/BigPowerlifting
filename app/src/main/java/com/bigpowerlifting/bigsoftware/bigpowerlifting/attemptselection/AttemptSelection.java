package com.bigpowerlifting.bigsoftware.bigpowerlifting.attemptselection;

/**
 * Created by shanesepac on 4/24/18.
 */

public class AttemptSelection {

    private static final double kiloPoundConversionFactor = 2.20462;

    public static class Constants{
        public static final int SQUAT1 = 1001;
        public static final int SQUAT1PLUS = 1002;
        public static final int SQUAT2 = 1003;
        public static final int SQUAT2PLUS = 1004;
        public static final int SQUAT3 = 1005;
        public static final int SQUAT3PLUS = 1006;
        public static final int BENCH1 = 1006;
        public static final int BENCH1PLUS = 1007;
        public static final int BENCH2 = 1008;
        public static final int BENCH2PLUS = 1009;
        public static final int BENCH3 = 1010;
        public static final int BENCH3PLUS = 1011;
        public static final int DEADLIFT1 = 1012;
        public static final int DEADLIFT1PLUS = 1013;
        public static final int DEADLIFT2 = 1014;
        public static final int DEADLIFT2PLUS = 1015;
        public static final int DEADLIFT3 = 1016;
        public static final int DEADLIFT3PLUS = 1017;
    }

    //Sourced from https://marylandpowerlifting.com/2009/05/11/a-powerlifters-guide-to-attempt-selection/

    /**
     *
     * @param estimatedMax The estimated max of the lift attempt array to generate.
     * @return Returns an array with first attempt [0], first+ attempt[1], second attempt[2] etc...
     * X+ attempt is
     */
    public static double[] getAttempts(double estimatedMax){

        //First attempt 90-92%
        //Second attempt 95-97%
        //Third attempt 100%

        final double firstAttemptPercent = 0.90;
        final double firstPlusAttemptPercent = 0.92;
        final double secondAttemptPercent = 0.95;
        final double secondPlusAttemptPercent = 0.97;
        //final double thirdAttemptPercent = 1d;

        double[] arr = new double[6];

        arr[0] = kilosToLift(estimatedMax*firstAttemptPercent);
        arr[1] = kilosToLift(estimatedMax*firstPlusAttemptPercent);
        arr[2] = kilosToLift(estimatedMax*secondAttemptPercent);
        arr[3] = kilosToLift(estimatedMax*secondPlusAttemptPercent);
        arr[4] = kilosToLift(estimatedMax);
        arr[5] = arr[4] + 2.50;

        return arr;
    }


    /**
     * Converts the attempt to the nearest 2.5kg increment.
     * @param pounds The number of pounds to be converted to the nearest 2.5kg increment.
     * @return The number in kilos to lift, rounded to the nearest 2.5kg.
     */
    private static double kilosToLift(double pounds){

        double kilosFromPounds = pounds / kiloPoundConversionFactor;
        //5*(Math.round(f/5));
        double poundsRounded = Math.floor(kilosFromPounds/ 2.5) * 2.5; //rounded to nearest 2.5kg, not 2 decimal places.

        return round(poundsRounded ,2);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String getFormattedAttemptString(double kiloVal){
        return String.valueOf(kiloVal) + "kg (" + String.valueOf(round(kiloVal*kiloPoundConversionFactor, 2)) + "lbs)";
    }
}
