package de.rheinfabrik.trackdux.utils.redux.utils;

import org.nustaq.serialization.FSTConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

import de.rheinfabrik.trackdux.utils.redux.core.Action;
import de.rheinfabrik.trackdux.utils.redux.core.Reducer;
import de.rheinfabrik.trackdux.utils.redux.core.Store;

public class StateCache<TState extends Serializable> {

    // Members

    private final FSTConfiguration mConfiguration = FSTConfiguration.createAndroidDefaultConfiguration();
    private final String mFilePath;

    // Constructor

    public StateCache(String pFilePath) {
        super();

        mFilePath = pFilePath;
    }

    // Public Api

    @SuppressWarnings("unchecked")
    public static <TState extends Serializable> Reducer<TState> newReducer() {
        return (lState, lAction) -> {
            switch (lAction.getType()) {
                case SetStateAction.TYPE: {
                    return (TState) ((SetStateAction) lAction).state;
                }
            }

            return lState;
        };
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void resetToInitialState(Store<TState> pStore) {
        resetToState(pStore, pStore.getInitialState());

        new File(mFilePath).delete();
    }

    public void resetToCachedState(Store<TState> pStore) {
        TState cachedState = readState();
        if (cachedState == null) {
            cachedState = pStore.getInitialState();
        }

        resetToState(pStore, cachedState);
    }

    public void cacheCurrentState(Store<TState> pStore) {

        // Write
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(mFilePath);
            mConfiguration.encodeToStream(fileOutputStream, pStore.getState());
            fileOutputStream.close();
        } catch (Exception exception) {
            // EMPTY
        }
    }

    // Private Api

    private void resetToState(Store<TState> pStore, TState pState) {
        SetStateAction<Serializable> setStateAction = new SetStateAction<>();
        setStateAction.state = pState;
        pStore.dispatch(setStateAction);
    }

    @SuppressWarnings("unchecked")
    private TState readState() {
        TState state = null;

        // Read
        try {
            FileInputStream fileInputStream = new FileInputStream(mFilePath);
            state = (TState) mConfiguration.decodeFromStream(fileInputStream);
            fileInputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            // EMPTY!
        }

        return state;
    }

    private static class SetStateAction<TState extends Serializable> implements Action {

        // Constants

        private static final String TYPE = "SetStateAction";

        // Properties

        public TState state;

        // Action

        @Override
        public String getType() {
            return TYPE;
        }
    }
}
