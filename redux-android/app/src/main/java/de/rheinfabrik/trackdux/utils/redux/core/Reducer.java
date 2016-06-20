package de.rheinfabrik.trackdux.utils.redux.core;

import java.io.Serializable;

public interface Reducer<TState extends Serializable> {

    // Reducer

    TState reduce(TState pState, Action pAction);

    // Utils

    class Utils {

        // Public Api

        @SafeVarargs
        public static <TState extends Serializable> Reducer<TState> combine(Reducer<TState>... pReducer) {
            return (lState, lAction) -> {
                for (Reducer<TState> reducer : pReducer) {
                    TState newState = reducer.reduce(lState, lAction);

                    if (newState != lState) {
                        return newState;
                    }
                }

                return lState;
            };
        }
    }
}
