package com.bigpowerlifting.bigsoftware.bigpowerlifting.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by shanesepac on 6/17/18.
 */

public class Math {

    public static double roundValue(double val) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(df.format(val));
    }

}
