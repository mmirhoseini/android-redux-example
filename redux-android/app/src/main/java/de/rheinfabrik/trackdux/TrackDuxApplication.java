package de.rheinfabrik.trackdux;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import de.rheinfabrik.trackdux.redux.states.AppState;
import de.rheinfabrik.trackdux.utils.dependency.DependencyProvider;
import de.rheinfabrik.trackdux.utils.dependency.DependencyService;
import de.rheinfabrik.trackdux.utils.dependency.ReduxProvider;
import de.rheinfabrik.trackdux.utils.redux.core.Store;
import de.rheinfabrik.trackdux.utils.redux.utils.StateCache;

public final class TrackDuxApplication extends Application implements Application.ActivityLifecycleCallbacks {

    // Members

    private final DependencyService mDependencyService = new DependencyService();
    private boolean mFirstActivityCreatedCall = true;

    // Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        DependencyProvider.injectDependencies(this);
        ReduxProvider.injectDependencies(this);

        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (DependencyService.matchesService(name)) {
            return mDependencyService;
        }

        return super.getSystemService(name);
    }

    // Application.ActivityLifecycleCallbacks

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        // Only Handle the first call
        if (mFirstActivityCreatedCall == false) {
            return;
        }

        mFirstActivityCreatedCall = false;

        StateCache<AppState> cache = ReduxProvider.provideReduxStateCache(this);
        Store<AppState> store = ReduxProvider.provideReduxStore(this);

        // If this is the first call of onActivityCreated() and the instance state is null
        // we can assume that this is a fresh app start.
        if (savedInstanceState == null) {
            cache.resetToInitialState(store);
        } else if (savedInstanceState != null) {
            cache.resetToCachedState(store);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        // EMPTY!
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // EMPTY!
    }

    @Override
    public void onActivityPaused(Activity activity) {

        // Cache state
        StateCache<AppState> cache = ReduxProvider.provideReduxStateCache(this);
        Store<AppState> store = ReduxProvider.provideReduxStore(this);
        cache.cacheCurrentState(store);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // EMPTY!
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // EMPTY!
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // EMPTY!
    }
}
