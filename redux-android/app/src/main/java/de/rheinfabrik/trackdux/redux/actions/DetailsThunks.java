package de.rheinfabrik.trackdux.redux.actions;

import android.content.Context;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.models.MovieDetails;
import de.rheinfabrik.trackdux.network.TraktTVApi;
import de.rheinfabrik.trackdux.redux.actions.DetailsActions.DidFinishLoadingMovieDetailsAction;
import de.rheinfabrik.trackdux.redux.actions.DetailsActions.ErrorWhileLoadingMovieDetailsAction;
import de.rheinfabrik.trackdux.redux.states.AppState;
import de.rheinfabrik.trackdux.redux.states.MovieDetailsState;
import de.rheinfabrik.trackdux.utils.dependency.DependencyProvider;
import de.rheinfabrik.trackdux.utils.redux.core.Store;
import de.rheinfabrik.trackdux.utils.rx.RxObservableCache;
import rx.Observable;

import static de.rheinfabrik.trackdux.redux.actions.DetailsActions.newLoadMovieDetailsAction;
import static rx.schedulers.Schedulers.io;

public final class DetailsThunks {

    // Constants

    private static final String REQUEST_MOVIE_DETAILS_OBSERVABLE_ID = "load movie details";

    // Public Api

    public static Store.Thunk<AppState> requestMoviesThunk(Context pContext) {
        return (pStateFunction, pDispatchFunction) -> {

            // Get dependencies
            TraktTVApi api = DependencyProvider.provideTraktTVApi(pContext);
            RxObservableCache observableCache = DependencyProvider.provideRxObservableCache(pContext);

            // Do nothing if we already have movies or if an error occurred
            MovieDetailsState state = pStateFunction.getState().movieDetailsState;
            if (state.movieDetails != null || state.error != null) {
                return;
            }

            // Grab Movie
            Movie movie = pStateFunction.getState().movieDetailsState.movie;
            if (movie == null || movie.identifiers == null) {
                return;
            }

            // Decide whether we can use a cached observable or need to create a new one
            String key = REQUEST_MOVIE_DETAILS_OBSERVABLE_ID + "trakt";
            Observable<MovieDetails> observable = observableCache.get(key);
            if (observable == null) {
                Observable<MovieDetails> requestMovieDetails = api.getMovieDetails(movie.identifiers.get("trakt"))
                        .subscribeOn(io());
                observable = observableCache.put(requestMovieDetails, key);
            }

            // Dispatch the correct actions
            observable
                    .doOnSubscribe(() -> pDispatchFunction.dispatch(newLoadMovieDetailsAction()))
                    .doOnTerminate(() -> observableCache.remove(key))
                    .doOnError(lThrowable -> {
                        ErrorWhileLoadingMovieDetailsAction action = new ErrorWhileLoadingMovieDetailsAction();
                        action.error = lThrowable;
                        pDispatchFunction.dispatch(action);
                    })
                    .onErrorResumeNext(throwable -> Observable.empty())
                    .subscribe(lMovieDetails -> {
                        DidFinishLoadingMovieDetailsAction action = new DidFinishLoadingMovieDetailsAction();
                        action.movieDetails = lMovieDetails;
                        pDispatchFunction.dispatch(action);
                    });
        };
    }
}
