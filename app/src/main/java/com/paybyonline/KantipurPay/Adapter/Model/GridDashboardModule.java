package com.paybyonline.KantipurPay.Adapter.Model;

import android.graphics.Bitmap;

/**
 * Created by Sameer on 4/17/2017.
 */

public class GridDashboardModule {
    public String label;
    public Bitmap icons;

    public GridDashboardModule(String label, Bitmap icons) {
        this.label = label;
        this.icons = icons;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Bitmap getIcons() {
        return icons;
    }

    public void setIcons(Bitmap icons) {
        this.icons = icons;
    }
}
