package com.otago.otagovirtualid;

import java.util.ArrayList;

/**Perks Class;
 *storing temporary values in an array
 *for temporary demonstration. Actual
 *real-time data to be implemented via
 *Firebase.
 */

public class Perk {

    private String pName;
    private boolean pActive;

    public Perk(String name, boolean active) {
        pName = name;
        pActive = active;
    }

    public String getName() {
        return pName;
    }

    public boolean isOnline() {
        return pActive;
    }

    private static int lastContactId = 0;

    public static ArrayList<Perk> createPerkList(int numPerks) {
        ArrayList<Perk> perks = new ArrayList<Perk>();

        for (int i = 1; i <= numPerks; i++) {
            perks.add(new Perk("*Name of retailer* " + ++lastContactId + "\n" + "*Type of discount... *", i <= numPerks / 2));
        }

        return perks ;
    }

}
