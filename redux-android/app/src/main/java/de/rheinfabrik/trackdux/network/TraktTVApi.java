package de.rheinfabrik.trackdux.network;

import java.util.ArrayList;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.models.MovieDetails;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface TraktTVApi {

    // Public Api

    @GET("/movies/popular?page=1&limit=2000")
    Observable<ArrayList<Movie>> getPopularMovies();

    @GET("/movies/{id}?extended=full")
    Observable<MovieDetails> getMovieDetails(@Path("id") String identifier);

}
