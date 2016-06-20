package de.rheinfabrik.trackdux.viewmodels;

import android.content.Context;

import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.PublishRelay;

import de.rheinfabrik.trackdux.models.Movie;
import de.rheinfabrik.trackdux.redux.actions.DetailsActions.GoToMovieDetailsAction;
import de.rheinfabrik.trackdux.utils.rx.RxViewModel;
import rx.Observable;

import static de.rheinfabrik.trackdux.utils.dependency.ReduxProvider.provideReduxStore;

public final class MovieItemViewModel extends RxViewModel {

    // Observables

    public Observable<String> movieTitle() {
        return movieCommand.asObservable().map(movie -> movie.title);
    }

    // Commands

    public final BehaviorRelay<Movie> movieCommand = BehaviorRelay.create();
    public final PublishRelay<Void> openDetailsCommand = PublishRelay.create();

    // Constructor

    public MovieItemViewModel(Context pContext) {
        super(pContext);

        // Details
        openDetailsCommand
                .withLatestFrom(movieCommand, (x, lMovie) -> {
                    GoToMovieDetailsAction action = new GoToMovieDetailsAction();
                    action.movie = lMovie;
                    return action;
                }).subscribe(action -> provideReduxStore(pContext).dispatch(action));
    }
}
