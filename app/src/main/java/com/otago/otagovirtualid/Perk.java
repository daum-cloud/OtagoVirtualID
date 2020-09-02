package com.otago.otagovirtualid;

import java.util.ArrayList;

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
            perks.add(new Perk("Discount " + ++lastContactId, i <= numPerks / 2));
        }

        return perks;
    }

}
