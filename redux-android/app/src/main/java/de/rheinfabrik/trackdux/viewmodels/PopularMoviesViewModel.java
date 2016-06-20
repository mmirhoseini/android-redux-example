package de.rheinfabrik.trackdux.viewmodels;

import android.content.Context;
import android.content.Intent;

import com.jakewharton.rxrelay.PublishRelay;

import java.util.ArrayList;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.redux.states.PopularMoviesState;
import de.rheinfabrik.trackdux.utils.rx.RxViewModel;
import rx.Observable;

import static de.rheinfabrik.trackdux.redux.actions.MoviesThunks.requestMoviesThunk;
import static de.rheinfabrik.trackdux.utils.IntentFactory.newMovieDetailsIntent;
import static de.rheinfabrik.trackdux.utils.dependency.ReduxProvider.provideReduxStore;

public final class PopularMoviesViewModel extends RxViewModel {

    // Observables

    public Observable<Intent> openMovieDetails() {
        return provideReduxStore(mContext).state()
                .map(lAppState -> lAppState.movieDetailsState.movie)
                .distinctUntilChanged()
                .filter(lMovie -> lMovie != null)
                .map(x -> newMovieDetailsIntent(mContext));
    }

    public Observable<Boolean> isLoadingMovies() {
        return popularMoviesState()
                .map(lPopularMoviesState -> lPopularMoviesState.isLoadingMovies);
    }

    public Observable<String> errorMessage() {
        return popularMoviesState()
                .map(lPopularMoviesState -> lPopularMoviesState.error)
                .filter(lThrowable -> lThrowable != null)
                .map(Throwable::getLocalizedMessage);
    }

    public Observable<ArrayList<Movie>> popularMovies() {
        return provideReduxStore(mContext).state()
                .map(lAppState -> lAppState.popularMoviesState.popularMovies);
    }

    // Commands

    public final PublishRelay<Void> loadPopularMoviesCommand = PublishRelay.create();
    public final PublishRelay<Void> reloadPopularMoviesCommand = PublishRelay.create();

    // Constructor

    public PopularMoviesViewModel(Context pContext) {
        super(pContext);

        loadPopularMoviesCommand.asObservable()
                .map(x -> requestMoviesThunk(pContext, false))
                .subscribe(lThunk -> provideReduxStore(pContext).dispatch(lThunk));

        reloadPopularMoviesCommand.asObservable()
                .map(x -> requestMoviesThunk(pContext, true))
                .subscribe(lThunk -> provideReduxStore(pContext).dispatch(lThunk));
    }

    // Private Api

    private Observable<PopularMoviesState> popularMoviesState() {
        return provideReduxStore(mContext).state()
                .map(lAppState -> lAppState.popularMoviesState);
    }
}
