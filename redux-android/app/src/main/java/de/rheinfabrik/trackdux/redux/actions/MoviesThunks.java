package de.rheinfabrik.trackdux.redux.actions;

import android.content.Context;

import java.util.ArrayList;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.network.TraktTVApi;
import de.rheinfabrik.trackdux.redux.actions.MoviesActions.ErrorWhileLoadingMoviesAction;
import de.rheinfabrik.trackdux.redux.states.AppState;
import de.rheinfabrik.trackdux.redux.states.PopularMoviesState;
import de.rheinfabrik.trackdux.utils.dependency.DependencyProvider;
import de.rheinfabrik.trackdux.utils.redux.core.Store;
import de.rheinfabrik.trackdux.utils.rx.RxObservableCache;
import rx.Observable;

import static de.rheinfabrik.trackdux.redux.actions.MoviesActions.DidFinishLoadingMoviesAction;
import static de.rheinfabrik.trackdux.redux.actions.MoviesActions.newLoadMoviesAction;
import static rx.schedulers.Schedulers.io;

public final class MoviesThunks {

    // Constants

    private static final String REQUEST_POPULAR_MOVIES_OBSERVABLE_ID = "load popular movies";

    // Public Api

    public static Store.Thunk<AppState> requestMoviesThunk(Context pContext, boolean pForceReload) {
        return (pStateFunction, pDispatchFunction) -> {

            // Get dependencies
            TraktTVApi api = DependencyProvider.provideTraktTVApi(pContext);
            RxObservableCache cache = DependencyProvider.provideRxObservableCache(pContext);

            // Do nothing if we already have movies or if an error occurred
            PopularMoviesState popularMoviesState = pStateFunction.getState().popularMoviesState;
            if (pForceReload == false && (popularMoviesState.popularMovies != null || popularMoviesState.error != null)) {
                return;
            }

            // Remove cached observable if need to reload
            if (pForceReload) {
                cache.remove(REQUEST_POPULAR_MOVIES_OBSERVABLE_ID);
            }

            // Decide whether we can use a cached observable or need to create a new one
            Observable<ArrayList<Movie>> observable = cache.get(REQUEST_POPULAR_MOVIES_OBSERVABLE_ID);
            if (observable == null) {
                Observable<ArrayList<Movie>> requestPopularMoviesObservable = api.getPopularMovies()
                        .subscribeOn(io());
                observable = cache.put(requestPopularMoviesObservable, REQUEST_POPULAR_MOVIES_OBSERVABLE_ID);
            }

            observable
                    .doOnSubscribe(() -> pDispatchFunction.dispatch(newLoadMoviesAction()))
                    .doOnError(lThrowable -> {
                        ErrorWhileLoadingMoviesAction action = new ErrorWhileLoadingMoviesAction();
                        action.error = lThrowable;
                        pDispatchFunction.dispatch(action);
                    })
                    .onErrorResumeNext(throwable -> Observable.empty())
                    .subscribe(lMovieDetails -> {
                        DidFinishLoadingMoviesAction action = new DidFinishLoadingMoviesAction();
                        action.movies = lMovieDetails;
                        pDispatchFunction.dispatch(action);
                    });
        };
    }
}
