package de.rheinfabrik.trackdux.utils.dependency;

import android.content.Context;

import java.io.Serializable;

import de.rheinfabrik.trackdux.redux.reducers.MovieDetailsReducer;
import de.rheinfabrik.trackdux.redux.reducers.MoviesReducer;
import de.rheinfabrik.trackdux.redux.states.AppState;
import de.rheinfabrik.trackdux.utils.redux.core.Reducer;
import de.rheinfabrik.trackdux.utils.redux.core.Store;
import de.rheinfabrik.trackdux.utils.redux.utils.StateCache;
import de.rheinfabrik.trackdux.utils.redux.utils.StateLogger;

import static de.rheinfabrik.trackdux.utils.dependency.DependencyService.getService;
import static de.rheinfabrik.trackdux.utils.redux.core.Reducer.Utils.combine;

public final class ReduxProvider {

    // Constants

    private static final String REDUX_STATE_CACHE_IDENTIFIER = "ReduxStateCache";
    private static final String REDUX_STORE_CACHE_IDENTIFIER = "ReduxStore";

    // Public Api

    public static Store<AppState> provideReduxStore(Context pContext) {
        return getService(pContext).getDependency(REDUX_STORE_CACHE_IDENTIFIER);
    }

    public static StateCache<AppState> provideReduxStateCache(Context pContext) {
        return getService(pContext).getDependency(REDUX_STATE_CACHE_IDENTIFIER);
    }

    public static void injectDependencies(Context pContext) {
        DependencyService dependencyService = getService(pContext);

        StateCache<AppState> cache = new StateCache<>(pContext.getCacheDir().getPath() + "/" + "Cache.redux");
        Reducer<AppState> reducer = combine(StateCache.newReducer(), MovieDetailsReducer.REDUCER, MoviesReducer.REDUCER);
        Store<AppState> store = Store.createStore(reducer, new AppState());

        StateLogger.attachToStore(store);

        dependencyService.addDependency(store, REDUX_STORE_CACHE_IDENTIFIER);
        dependencyService.addDependency(cache, REDUX_STATE_CACHE_IDENTIFIER);
    }
}
