package de.rheinfabrik.trackdux.redux.reducers;

import de.rheinfabrik.trackdux.redux.actions.DetailsActions.DidFinishLoadingMovieDetailsAction;
import de.rheinfabrik.trackdux.redux.actions.DetailsActions.ErrorWhileLoadingMovieDetailsAction;
import de.rheinfabrik.trackdux.redux.actions.DetailsActions.GoToMovieDetailsAction;
import de.rheinfabrik.trackdux.redux.states.AppState;
import de.rheinfabrik.trackdux.utils.redux.core.Reducer;

import static de.rheinfabrik.trackdux.redux.actions.DetailsActions.DID_FINISH_LOADING_MOVIE_DETAILS;
import static de.rheinfabrik.trackdux.redux.actions.DetailsActions.ERROR_WHILE_LOADING_MOVIE_DETAILS;
import static de.rheinfabrik.trackdux.redux.actions.DetailsActions.GO_BACK_FROM_MOVIE_DETAILS;
import static de.rheinfabrik.trackdux.redux.actions.DetailsActions.GO_TO_MOVIE_DETAILS;
import static de.rheinfabrik.trackdux.redux.actions.DetailsActions.LOAD_MOVIE_DETAILS;
import static de.rheinfabrik.trackdux.utils.redux.core.Reducer.Utils.combine;

public final class MovieDetailsReducer {

    // Private Api

    private static Reducer<AppState> NAVIGATION_REDUCER = (lState, lAction) -> {
        switch (lAction.getType()) {
            case GO_TO_MOVIE_DETAILS: {
                GoToMovieDetailsAction action = (GoToMovieDetailsAction) lAction;
                lState.movieDetailsState.movie = action.movie;

                return lState;
            }

            case GO_BACK_FROM_MOVIE_DETAILS: {
                lState.movieDetailsState.movie = null;
                lState.movieDetailsState.movieDetails = null;
                lState.movieDetailsState.error = null;

                return lState;
            }
        }

        return lState;
    };

    private static Reducer<AppState> LOAD_MOVIE_DETAILS_REDUCER = (lState, lAction) -> {
        switch (lAction.getType()) {
            case LOAD_MOVIE_DETAILS: {
                lState.movieDetailsState.isLoadingDetails = true;
                lState.movieDetailsState.movieDetails = null;

                return lState;
            }

            case DID_FINISH_LOADING_MOVIE_DETAILS: {
                DidFinishLoadingMovieDetailsAction action = (DidFinishLoadingMovieDetailsAction) lAction;
                lState.movieDetailsState.isLoadingDetails = false;
                lState.movieDetailsState.movieDetails = action.movieDetails;

                return lState;
            }

            case ERROR_WHILE_LOADING_MOVIE_DETAILS: {
                ErrorWhileLoadingMovieDetailsAction action = (ErrorWhileLoadingMovieDetailsAction) lAction;
                lState.movieDetailsState.error = action.error;
                lState.movieDetailsState.isLoadingDetails = false;
                lState.movieDetailsState.movieDetails = null;

                return lState;
            }
        }

        return lState;
    };

    // Public Api

    public static Reducer<AppState> REDUCER = combine(NAVIGATION_REDUCER, LOAD_MOVIE_DETAILS_REDUCER);
}
