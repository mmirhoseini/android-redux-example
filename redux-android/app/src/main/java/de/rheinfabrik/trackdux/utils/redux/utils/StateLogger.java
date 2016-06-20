package de.rheinfabrik.trackdux.utils.redux.utils;

import android.util.Log;

import com.google.gson.Gson;

import de.rheinfabrik.trackdux.utils.redux.core.Store;
import rx.Subscription;

public final class StateLogger {

    // Public Api

    public static Subscription attachToStore(Store<?> pStore) {
        return pStore.state().subscribe(lState -> {
            String stateJson = new Gson().toJson(lState);
            String stateName = lState.getClass().getSimpleName();

            Log.d(StateLogger.class.getSimpleName(), "[" + stateName + "]" + " changed to: " + stateJson);
        });
    }
}
