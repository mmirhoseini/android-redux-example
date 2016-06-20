package de.rheinfabrik.trackdux.redux.states;

import java.io.Serializable;

public final class AppState implements Serializable {

    // Sub-States

    public PopularMoviesState popularMoviesState = new PopularMoviesState();

    public MovieDetailsState movieDetailsState = new MovieDetailsState();
}
