package de.rheinfabrik.trackdux.utils;

import android.content.Context;
import android.content.Intent;

import de.rheinfabrik.trackdux.ui.details.MovieDetailsActivity;

public final class IntentFactory {

    // Constructor

    private IntentFactory() {
        throw new IllegalStateException("No instantiation possible!");
    }

    // Public Api

    public static Intent newMovieDetailsIntent(Context pContext) {
        return new Intent(pContext, MovieDetailsActivity.class);
    }

}
