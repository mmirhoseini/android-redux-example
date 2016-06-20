package de.rheinfabrik.trackdux.viewmodels;

import android.content.Context;

import com.jakewharton.rxrelay.PublishRelay;

import de.rheinfabrik.trackdux.redux.states.MovieDetailsState;
import de.rheinfabrik.trackdux.utils.rx.RxViewModel;
import rx.Observable;

import static de.rheinfabrik.trackdux.redux.actions.DetailsThunks.requestMoviesThunk;
import static de.rheinfabrik.trackdux.utils.dependency.ReduxProvider.provideReduxStore;

public final class MovieDetailsViewModel extends RxViewModel {

    // Observer

    public Observable<String> title() {
        return movieDetailsState()
                .filter(lMovieDetailsState -> lMovieDetailsState.movie != null)
                .map(lMovieDetailsState -> lMovieDetailsState.movie.title);
    }

    public Observable<String> tagline() {
        return movieDetailsState()
                .filter(lMovieDetailsState -> lMovieDetailsState.movieDetails != null)
                .map(lMovieDetailsState -> lMovieDetailsState.movieDetails.tagline);
    }

    public Observable<String> description() {
        return movieDetailsState()
                .filter(lMovieDetailsState -> lMovieDetailsState.movieDetails != null)
                .map(lMovieDetailsState -> lMovieDetailsState.movieDetails.description);
    }

    public Observable<Boolean> isLoading() {
        return provideReduxStore(mContext)
                .state()
                .map(lAppState -> lAppState.movieDetailsState.isLoadingDetails);
    }

    // Commands

    public final PublishRelay<Void> loadDetailsCommand = PublishRelay.create();

    // Constructor

    public MovieDetailsViewModel(Context pContext) {
        super(pContext);

        // Load details
        loadDetailsCommand.withLatestFrom(provideReduxStore(pContext).state(), (x, lAppState) -> lAppState.movieDetailsState.movie)
                .map(x -> requestMoviesThunk(pContext))
                .subscribe(action -> provideReduxStore(pContext).dispatch(action));
    }

    // Private Api

    public Observable<MovieDetailsState> movieDetailsState() {
        return provideReduxStore(mContext)
                .state()
                .map(lAppState -> lAppState.movieDetailsState);
    }
}
