package com.bigpowerlifting.bigsoftware.bigpowerlifting.unitconversion;

import static com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Math.roundValue;

/**
 * Created by shanesepac on 6/17/18.
 */

public class UnitConversionModel {

    private static final double KILO_POUNDS_CONVERSION = 2.2046;

    static double convertToPounds(double kilos){
        return roundValue(kilos*KILO_POUNDS_CONVERSION);
    }

    static double convertToKilos(double pounds){
        return roundValue(pounds/KILO_POUNDS_CONVERSION);
    }

}
