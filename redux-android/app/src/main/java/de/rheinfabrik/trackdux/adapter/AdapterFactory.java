package de.rheinfabrik.trackdux.adapter;

import android.content.Context;

import java.util.List;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.ui.movies.MovieItemView;
import de.rheinfabrik.trackdux.utils.RecyclerViewArrayAdapter;

public class AdapterFactory {

    // Constructor

    private AdapterFactory() {
        throw new IllegalStateException("No instantiation possible!");
    }

    // Public Api

    public static RecyclerViewArrayAdapter<Movie, MovieItemView> newMovieRecyclerViewAdapter(List<Movie> pMovies) {
        return new RecyclerViewArrayAdapter<Movie, MovieItemView>(pMovies) {

            @Override
            public MovieItemView onCreateView(Context pContext) {
                return new MovieItemView(pContext);
            }

            @Override
            public void onBindData(MovieItemView pView, Movie pData) {
                pView.getViewModel().movieCommand.call(pData);
            }
        };
    }

}
