package de.rheinfabrik.trackdux.utils.rx;

import android.content.Context;

import com.jakewharton.rxrelay.PublishRelay;

import rx.Observable;

public abstract class RxViewModel {

    // Observables

    public Observable<Boolean> isActive() {
        return isActiveCommand.asObservable();
    }

    // Commands

    public final PublishRelay<Boolean> isActiveCommand = PublishRelay.create();

    // Members

    protected final Context mContext;

    // Constructor

    public RxViewModel(Context pContext) {
        super();

        mContext = pContext;
    }
}
