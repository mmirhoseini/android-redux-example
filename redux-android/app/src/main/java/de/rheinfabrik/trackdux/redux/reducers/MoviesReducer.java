package de.rheinfabrik.trackdux.redux.reducers;

import de.rheinfabrik.trackdux.redux.actions.DetailsActions.ErrorWhileLoadingMovieDetailsAction;
import de.rheinfabrik.trackdux.redux.actions.MoviesActions.DidFinishLoadingMoviesAction;
import de.rheinfabrik.trackdux.redux.states.AppState;
import de.rheinfabrik.trackdux.utils.redux.core.Reducer;

import static de.rheinfabrik.trackdux.redux.actions.MoviesActions.DID_FINISH_LOADING_MOVIES;
import static de.rheinfabrik.trackdux.redux.actions.MoviesActions.ERROR_WHILE_LOADING_MOVIES;
import static de.rheinfabrik.trackdux.redux.actions.MoviesActions.LOAD_MOVIES;

public final class MoviesReducer {

    // Public Api

    public static Reducer<AppState> REDUCER = (lState, lAction) -> {
        switch (lAction.getType()) {
            case LOAD_MOVIES: {
                lState.popularMoviesState.isLoadingMovies = true;
                lState.popularMoviesState.popularMovies = null;

                return lState;
            }

            case DID_FINISH_LOADING_MOVIES: {
                DidFinishLoadingMoviesAction action = (DidFinishLoadingMoviesAction) lAction;
                lState.popularMoviesState.isLoadingMovies = false;
                lState.popularMoviesState.popularMovies = action.movies;

                return lState;
            }

            case ERROR_WHILE_LOADING_MOVIES: {
                ErrorWhileLoadingMovieDetailsAction action = (ErrorWhileLoadingMovieDetailsAction) lAction;
                lState.popularMoviesState.error = action.error;
                lState.popularMoviesState.isLoadingMovies = false;
                lState.popularMoviesState.popularMovies = null;

                return lState;
            }
        }

        return lState;
    };
}
