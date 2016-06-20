package de.rheinfabrik.trackdux.utils.dependency;

import android.content.Context;

import de.rheinfabrik.trackdux.network.TraktTVApi;
import de.rheinfabrik.trackdux.network.TraktTVApiFactory;
import de.rheinfabrik.trackdux.utils.rx.RxObservableCache;

import static de.rheinfabrik.trackdux.utils.dependency.DependencyService.getService;

public final class DependencyProvider {

    // Constants

    private static final String TRAKT_TV_API_DEPENDENCY_IDENTIFIER = "TraktTVApi";
    private static final String RX_OBSERVABLE_CACHE_DEPENDENCY_IDENTIFIER = "RxObservableCache";

    // Constructor

    private DependencyProvider() {
        throw new IllegalStateException("No instantiation possible!");
    }

    // Public Api

    public static TraktTVApi provideTraktTVApi(Context pContext) {
        return getService(pContext).getDependency(TRAKT_TV_API_DEPENDENCY_IDENTIFIER);
    }

    public static RxObservableCache provideRxObservableCache(Context pContext) {
        return getService(pContext).getDependency(RX_OBSERVABLE_CACHE_DEPENDENCY_IDENTIFIER);
    }

    public static void injectDependencies(Context pContext) {
        DependencyService dependencyService = getService(pContext);
        dependencyService.addDependencyLoader(RxObservableCache::new, RX_OBSERVABLE_CACHE_DEPENDENCY_IDENTIFIER);
        dependencyService.addDependencyLoader(TraktTVApiFactory::newApi, TRAKT_TV_API_DEPENDENCY_IDENTIFIER);
    }
}
