package de.rheinfabrik.trackdux.redux.states;

import java.io.Serializable;
import java.util.ArrayList;

import de.rheinfabrik.trackdux.models.Movie;

public final class PopularMoviesState implements Serializable {

    // Properties

    public ArrayList<Movie> popularMovies = null;

    public boolean isLoadingMovies = false;

    public Throwable error = null;
}
