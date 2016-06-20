package de.rheinfabrik.trackdux.redux.actions;

import java.util.ArrayList;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.utils.redux.core.Action;

public final class MoviesActions {

    // Loading

    public final static String LOAD_MOVIES = "LoadMovies";
    public final static String ERROR_WHILE_LOADING_MOVIES = "ErrorWhileLoadingMovies";
    public final static String DID_FINISH_LOADING_MOVIES = "DidFinishLoadingMovies";

    public static Action newLoadMoviesAction() {
        return () -> LOAD_MOVIES;
    }

    public static class ErrorWhileLoadingMoviesAction implements Action {

        // Properties

        public Throwable error;

        // Action

        @Override
        public String getType() {
            return ERROR_WHILE_LOADING_MOVIES;
        }
    }

    public static class DidFinishLoadingMoviesAction implements Action {

        // Properties

        public ArrayList<Movie> movies;

        // Action

        @Override
        public String getType() {
            return DID_FINISH_LOADING_MOVIES;
        }
    }
}
