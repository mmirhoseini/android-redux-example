package de.rheinfabrik.trackdux.utils;

import android.content.Context;

public class DeviceUtils {

    // Class members

    private static float sDensity = 0.0f;


    // Constructor

    private DeviceUtils() {
        throw new IllegalStateException("No instantiation possible!");
    }

    // Public Api

    public static int pixelsToDensityPoints(Context pContext, float pPixels) {
        if (sDensity == 0.0f) {
            sDensity = pContext.getResources().getDisplayMetrics().density;
        }

        if (sDensity == 0.0f) {
            return 0;
        }

        return Math.round(pPixels / sDensity);
    }
}
