package de.rheinfabrik.trackdux.utils.redux.core;

import java.io.Serializable;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class Store<TState extends Serializable> {

    // Interfaces

    public interface Thunk<TState> {

        // Interfaces

        interface DispatchFunction {
            void dispatch(Action pAction);
        }

        interface StateFunction<TState> {
            TState getState();
        }

        // Public Api

        void call(StateFunction<TState> pStateFunction, DispatchFunction pDispatchFunction);
    }

    // Factory

    public static <TState extends Serializable> Store<TState> createStore(Reducer<TState> pReducer, TState pInitialState) {
        return new Store<>(pReducer, pInitialState);
    }

    // Members

    private final Reducer<TState> mReducer;
    private final BehaviorSubject<TState> mStateSubject = BehaviorSubject.create();
    private final TState mInitialState;

    // Constructor

    private Store(Reducer<TState> pReducer, TState pInitialState) {
        super();

        mInitialState = pInitialState;
        mReducer = pReducer;
        mStateSubject.onNext(mInitialState);
    }

    // Public Api

    public void dispatch(Action pAction) {
        TState newState = mReducer.reduce(getState(), pAction);
        mStateSubject.onNext(newState);
    }

    public void dispatch(Thunk<TState> pThunk) {
        pThunk.call(this::getState, this::dispatch);
    }

    public TState getState() {
        return mStateSubject.getValue();
    }

    public TState getInitialState() {
        return mInitialState;
    }

    public Observable<TState> state() {
        return mStateSubject;
    }
}
