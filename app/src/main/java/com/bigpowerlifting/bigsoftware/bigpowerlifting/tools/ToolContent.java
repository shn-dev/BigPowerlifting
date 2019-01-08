package com.bigpowerlifting.bigsoftware.bigpowerlifting.tools;

import android.app.Activity;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.RankingsActivity;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.attemptselection.AttemptSelectionActivity;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort.MaxEffortActivity;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.maxeffort.MaxEffortStartFragment;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.unitconversion.UnitConversionActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ToolContent {

    public enum ToolPositionConstantsEnum{
        MAX_EFFORT,
        KILO_POUNDS_CONVERSION,
        MEET_ATTEMPT_SELECTION,
        RANKINGS
    }


    /**
     * An array of sample (dummy) items.
     */
    public static final List<Tool> ITEMS = new ArrayList<Tool>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Tool> ITEM_MAP = new HashMap<String, Tool>();

    private static final int COUNT = ToolPositionConstantsEnum.values().length;

    static {
        // Add some sample items.

        for (int i = 1; i <= COUNT; i++) {
            addItem(createTool(i));
        }
    }

    private static void addItem(Tool item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Tool createTool(int position) {

        //Tool's constructor: Tool(String id, String title, String subtitle, String content, int imageName) {...}

        //Need to add 1 to enum ordinal because its zero indexed and recyclerview position is not

        if(position==ToolPositionConstantsEnum.MAX_EFFORT.ordinal()+1){
            return new Tool(
                    String.valueOf(position),
                    "Max Effort Calculator",
                    "A great tool for getting big!",
                    "Wow this tool is great. So amazing, its borderline ridiculous",
                    "tool1.jpg", MaxEffortActivity.class);
        }
        else if(position==ToolPositionConstantsEnum.KILO_POUNDS_CONVERSION.ordinal()+1){
            return new Tool(
                    String.valueOf(position),
                    "Kilo-Pounds Conversion Chart",
                    "Why do calculus?",
                    "A simple table to quickly convert kilos to pounds. Useful for Americans. Probably useless" +
                            "to anyone else.",
                    "tool2.jpeg", UnitConversionActivity.class);
        }
        else if(position==ToolPositionConstantsEnum.MEET_ATTEMPT_SELECTION.ordinal()+1){
            return new Tool(
                    String.valueOf(position),
                    "Attempt Selection Generator",
                    "Based off of lots of research!",
                    "Don't worry about picking your attempts; this should give you a great approximation " +
                            "of what you should be picking for powerlifting competition attempts.",
                    "tool3.jpeg", AttemptSelectionActivity.class);
        }
        else if(position==ToolPositionConstantsEnum.RANKINGS.ordinal()+1){
            return new Tool(
                    String.valueOf(position),
                    "Meet and Lifter Info",
                    "Browse rankings and search for lifters.",
                    "Currently only USPA and USAPL lifter search is supported. Unfortunately, powerlifting " +
                            "has a ton of federations which makes it very high maintenance to have every federation out there supported!",
                    "tool4.jpg", RankingsActivity.class);
        }
        else{
            //Create a "default" tool. Delete this during production app. There should not be default tools in the
            //published version; this is just useful for tests and debugging.
            return new Tool(String.valueOf(position), "Item " + position, "Subtitle", makeDetails(position),
                    "tool3.jpeg", null);
        }
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Tool {
        public final String id;
        public final String title;
        public final String subtitle;
        public final String content;
        public final String imageName;
        public final Class<? extends Activity> activity;

        public Tool(String id, String title, String subtitle, String content, String imageName, Class<? extends Activity> activity) {
            this.id = id;
            this.content = content;
            this.title = title;
            this.subtitle = subtitle;
            this.imageName = imageName;
            this.activity = activity;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
