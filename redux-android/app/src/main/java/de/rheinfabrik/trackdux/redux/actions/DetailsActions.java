package de.rheinfabrik.trackdux.redux.actions;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.models.MovieDetails;
import de.rheinfabrik.trackdux.utils.redux.core.Action;

public final class DetailsActions {

    // Navigation

    public final static String GO_TO_MOVIE_DETAILS = "GoToMovieDetails";
    public final static String GO_BACK_FROM_MOVIE_DETAILS = "GoBackFromMovieDetails";

    public static class GoToMovieDetailsAction implements Action {

        // Properties

        public Movie movie;

        // Action

        @Override
        public String getType() {
            return GO_TO_MOVIE_DETAILS;
        }
    }

    public static Action newGoBackFromMovieDetailsAction() {
        return () -> GO_BACK_FROM_MOVIE_DETAILS;
    }

    // Loading

    public final static String LOAD_MOVIE_DETAILS = "LoadMovieDetails";
    public final static String ERROR_WHILE_LOADING_MOVIE_DETAILS = "ErrorWhileLoadingMovieDetails";
    public final static String DID_FINISH_LOADING_MOVIE_DETAILS = "DidFinishLoadingMovieDetails";

    public static Action newLoadMovieDetailsAction() {
        return () -> LOAD_MOVIE_DETAILS;
    }

    public static class ErrorWhileLoadingMovieDetailsAction implements Action {

        // Properties

        public Throwable error;

        // Action

        @Override
        public String getType() {
            return ERROR_WHILE_LOADING_MOVIE_DETAILS;
        }
    }

    public static class DidFinishLoadingMovieDetailsAction implements Action {

        // Properties

        public MovieDetails movieDetails;

        // Action

        @Override
        public String getType() {
            return DID_FINISH_LOADING_MOVIE_DETAILS;
        }
    }
}
