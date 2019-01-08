package com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import static com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Math.roundValue;

/**
 * Created by ssepa on 7/31/2017.
 */

public class MaxEffort {

    public static class EstimatedMaxFormulas {


        /**
         * Anything below RPE6.5 gets reset to RPE6.5. Any submitted RPE that is
         * not in a 0.5 increment is rounded to the closest 0.5. Based off of Mike T's
         * RPE versus rep chart.
         * @param w Weight lifted.
         * @param reps Number of reps.
         * @param RPE Perceived exertion.
         * @return
         */
        public static double RPEVersusRepsMax(double w, int reps, double RPE) {

            double[][] RPEChart = {
                    //1     2     3     4     5     6     7    8     9     10   //Reps
                    //0     1     2     3     4     5     6    7     8     9    //Index
                    {1.00, 0.96, 0.92, 0.89, 0.86, 0.84, 0.81, 0.79, 0.76, 0.74}, //RPE 10 : Index 0
                    {0.98, 0.94, 0.91, 0.88, 0.85, 0.82, 0.80, 0.77, 0.75, 0.72},
                    {0.96, 0.92, 0.89, 0.86, 0.84, 0.81, 0.79, 0.76, 0.74, 0.71},
                    {0.94, 0.91, 0.88, 0.85, 0.82, 0.80, 0.77, 0.75, 0.72, 0.69}, //...
                    {0.92, 0.89, 0.86, 0.84, 0.81, 0.79, 0.76, 0.74, 0.71, 0.68}, //...
                    {0.91, 0.88, 0.85, 0.82, 0.80, 0.77, 0.75, 0.72, 0.69, 0.67},
                    {0.89, 0.86, 0.84, 0.81, 0.79, 0.76, 0.74, 0.71, 0.68, 0.65},
                    {0.88, 0.85, 0.82, 0.80, 0.77, 0.75, 0.72, 0.69, 0.67, 0.64 //RPE 6.5 // Index 7
                    }
            };


            if (RPE < 6.5) {
                RPE = 6.5;
            }

            //round the RPE to the nearest 0.5
            double roundedRPE = Math.round(RPE * 2.0) / 2.0;
            int RPEIndex = 0;


            for (double i = 0; i < 8; i++) {
                if (Double.compare(10.0 - (i * 0.5), roundedRPE) == 0) {
                    RPEIndex = (int) i;
                }
            }

            double coeff = RPEChart[RPEIndex][reps - 1];

            return roundValue(w / coeff);
        }

        public static double Epley(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double epley = w * (1 + reps / 30.0);
            return roundValue(epley);
        }

        public static double Brzycki(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double brzycki = w / (1.0278 - (0.0278 * reps));
            return roundValue(brzycki);
        }

        public static double McGlothin(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double mcglothin = (100 * w) / (101.3 - (2.67123 * reps));
            return roundValue(mcglothin);
        }

        public static double Lombardi(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double lombardi = Math.pow(reps, 0.10) * w;
            return roundValue(lombardi);
        }

        public static double Mayhew(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double mayhew = (100 * w) / (52.2 + 41.9 * Math.exp(-0.055 * reps));
            return roundValue(mayhew);
        }

        public static double OConner(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double oconner = w * (1 + reps / 40.0);
            return roundValue(oconner);
        }

        public static double Wathan(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double wathan = (100 * w) / (48.8 + 53.8 * Math.exp(-0.075 * reps));
            return roundValue(wathan);
        }

        /**
         * Gets the average of all seven single rep max lift formulas.
         *
         * @param w    The weight lifted.
         * @param reps The number of reps the weight was lifted.
         * @return The average of all seven single rep max lift formulas.
         */
        public static double AverageMax(double w, int reps) {

            if (reps == 1) {
                return w;
            }

            double average = ((Epley(w, reps) + Brzycki(w, reps) + McGlothin(w, reps) + Lombardi(w, reps)
                    + Mayhew(w, reps) + OConner(w, reps) + Wathan(w, reps)) / 7.0);

            return roundValue(average);
        }

        public enum MaxMethods {
            Epley,
            Brzycki,
            McGlothin,
            Lombardi,
            Mayhew,
            OConner,
            Wathan
        }
    }

    /**
     * Get an individual's Wilks score.
     * @param weightLiftedKg Combined total of squat, bench press and deadlift, in kilograms.
     * @param isMale True if the lifter is male, false if female.
     * @param bodyweightKg The bodyweight, in kilograms, of the lifter.
     * @return The Wilks score of the lifter.
     */
    public static double getWilks(double weightLiftedKg, boolean isMale, double bodyweightKg) {

        double a, b, c, d, e, f;
        if (isMale) {
            a = -216.0475144;
            b = 16.2606339;
            c = -0.002388645;
            d = -0.00113732;
            e = 7.01863E-06;
            f = -1.291E-08;
        } else {
            a = 594.31747775582;
            b = -27.23842536447;
            c = 0.82112226871;
            d = -0.00930733913;
            e = 4.731582E-05;
            f = -9.054E-08;
        }

        double coeff = 500 /
                (a + (b * bodyweightKg) + (c * Math.pow(bodyweightKg, 2)) + (d * Math.pow(bodyweightKg, 3))
                        + (e * Math.pow(bodyweightKg, 4)) + (f * Math.pow(bodyweightKg, 5)));

        return roundValue(coeff * weightLiftedKg);
    }
}
