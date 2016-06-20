package de.rheinfabrik.trackdux.utils.dependency;

import android.content.Context;

import java.util.HashMap;

public final class DependencyService {

    // Constants

    private static final String SERVICE_NAME = "de.rheinfabrik.DependencyService";

    // Interfaces

    public interface DependencyLoader<TDependency> {
        TDependency loadDependency();
    }

    // Members

    private final HashMap<String, DependencyLoader<?>> mDependencyLoaders = new HashMap<>();

    // Public static Api

    @SuppressWarnings({"ResourceType", "WrongConstant"})
    public static DependencyService getService(Context pContext) {
        Object service = pContext.getSystemService(SERVICE_NAME);
        if (service == null) {
            service = pContext.getApplicationContext().getSystemService(SERVICE_NAME);
        }

        return (DependencyService) service;
    }

    public static boolean matchesService(String pName) {
        return SERVICE_NAME.equals(pName);
    }

    // Public Api

    @SuppressWarnings("unchecked")
    public <TDependency> TDependency getDependency(String pIdentifier) {
        if (mDependencyLoaders.containsKey(pIdentifier) == false) {
            throw new IllegalStateException("Dependency for " + pIdentifier + " not set!");
        }

        return (TDependency) mDependencyLoaders.get(pIdentifier).loadDependency();
    }

    public <TDependency> void addDependency(TDependency pDependency, String pIdentifier) {
        addDependencyLoader(() -> pDependency, pIdentifier);
    }

    public <TDependency> void addDependencyLoader(DependencyLoader<TDependency> pDependencyLoader, String pIdentifier) {
        mDependencyLoaders.put(pIdentifier, pDependencyLoader);
    }
}
