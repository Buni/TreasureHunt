package com.samhattangady.treasurehunt;

import android.net.Uri;

/**
 * Class to hold all details of a given hunt.
 * Will grow over time as the app develops.
 */

public class Hunt {

    private int huntId;
    private String huntName;
    private String imageLink;

    public Hunt() {

    }

    public Hunt(int id, String name, String image) {
        this.huntId = id;
        this.huntName = name;
        this.imageLink = image;
    }

    public int getHuntId() { return huntId; }
    public void setHuntId(int id) { this.huntId = id; }

    public String getHuntName() { return huntName; }
    public void setHuntName(String name) { this.huntName = name; }

    public String getImageLink() { return imageLink; }
    public void setImageLink(String link) { this.imageLink = link; }
}
