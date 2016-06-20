package de.rheinfabrik.trackdux.redux.states;

import java.io.Serializable;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.models.MovieDetails;

public final class MovieDetailsState implements Serializable {

    // Properties

    public Movie movie = null;

    public MovieDetails movieDetails = null;

    public boolean isLoadingDetails = false;

    public Throwable error = null;
}
