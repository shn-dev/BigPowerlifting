package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Networker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by shanesepac on 6/3/18.
 */

public abstract class Federation {

    protected  Networker networker;
    public Federation(Networker n){
        networker = n;
    }

    @FunctionalInterface
    public interface LifterInfoObtained{
        void lifterInfoObtained(List<Competition> info);
    }

    @FunctionalInterface
    public interface RankingsObtained{
        void rankingsObtained(List<Ranking> rankings);
    }

    public static String[] getItemList(LinkedHashMap<String, ?> map){
        List<String> list = new ArrayList<>();
        list.addAll(map.keySet());
        String[] arr = new String[list.size()];
        list.toArray(arr);
        return arr;
    }

    public abstract List<Competition> getLifterInfo(String userID, LifterInfoObtained infoObtained);
    public abstract List<Ranking> getRankings(RankingsObtained rankingsObtained, String address);

    public class Competition{
        private String date;
        private String name;
        private String weightclass;

        public String getWeightclass() {
            return weightclass;
        }

        public void setWeightclass(String weightclass) {
            this.weightclass = weightclass;
        }

        public Competition(String date, String name, String ranking, String division, String weightclass, String bodyweight,
                           String[] squat, String[] bench, String[] deadlift, String total, String points) {
            this.date = date;
            this.name = name;
            this.ranking = ranking;
            this.division = division;
            this.bodyweight = bodyweight;
            this.squat = squat;
            this.bench = bench;
            this.weightclass = weightclass;
            this.deadlift = deadlift;
            this.total = total;
            this.points = points;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getBodyweight() {
            return bodyweight;
        }

        public void setBodyweight(String bodyweight) {
            this.bodyweight = bodyweight;
        }

        public String[] getSquat() {
            return squat;
        }

        public void setSquat(String[] squat) {
            this.squat = squat;
        }

        public String[] getBench() {
            return bench;
        }

        public void setBench(String[] bench) {
            this.bench = bench;
        }

        public String[] getDeadlift() {
            return deadlift;
        }

        public void setDeadlift(String[] deadlift) {
            this.deadlift = deadlift;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        private String ranking;
        private String division;
        private String bodyweight;
        private String[] squat;
        private String[] bench;
        private String[] deadlift;
        private String total;
        private String points;
    }

    public class Ranking{
        private String ranking;
        private String name;
        private String date;
        private String points;
        private String weight;

        public Ranking(String ranking, String name, String date, String points, String weight) {
            this.ranking = ranking;
            this.name = name;
            this.date = date;
            this.points = points;
            this.weight = weight;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
    }

    public enum Federations{
        USAPL, USPA
    }
}
