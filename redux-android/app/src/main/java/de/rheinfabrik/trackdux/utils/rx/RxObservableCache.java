package de.rheinfabrik.trackdux.utils.rx;

import java.util.HashMap;

import rx.Observable;

public final class RxObservableCache {

    // Members

    private final HashMap<String, Observable<?>> mObservables = new HashMap<>();

    // Public Api

    public <TInput> Observable<TInput> put(Observable<TInput> pObservable, String pIdentifier) {
        if (contains(pIdentifier)) {
            remove(pIdentifier);
        }

        Observable<TInput> cachedObservable = pObservable.cache();

        mObservables.put(pIdentifier, cachedObservable);

        return cachedObservable;
    }

    public boolean contains(String pIdentifier) {
        return mObservables.containsKey(pIdentifier);
    }

    public void remove(String pIdentifier) {
        mObservables.remove(pIdentifier);
    }

    @SuppressWarnings("unchecked")
    public <TInput> Observable<TInput> get(String pIdentifier) {
        return (Observable<TInput>) mObservables.get(pIdentifier);
    }
}
