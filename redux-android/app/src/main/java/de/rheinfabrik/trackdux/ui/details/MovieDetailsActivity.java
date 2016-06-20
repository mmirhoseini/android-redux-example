package de.rheinfabrik.trackdux.ui.details;

import android.os.Bundle;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import de.rheinfabrik.trackdux.R;
import de.rheinfabrik.trackdux.utils.dependency.ReduxProvider;
import de.rheinfabrik.trackdux.viewmodels.MovieDetailsViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static butterknife.ButterKnife.findById;
import static com.trello.rxlifecycle.RxLifecycle.bindActivity;
import static de.rheinfabrik.trackdux.redux.actions.DetailsActions.newGoBackFromMovieDetailsAction;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class MovieDetailsActivity extends RxAppCompatActivity {

    // Members

    private MovieDetailsViewModel mViewModel;

    // Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);

        mViewModel = new MovieDetailsViewModel(this);

        addBindings();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mViewModel.loadDetailsCommand.call(null);
    }

    @Override
    public void onBackPressed() {
        ReduxProvider.provideReduxStore(this).dispatch(newGoBackFromMovieDetailsAction());

        super.onBackPressed();
    }

    // Private Api

    private void addBindings() {

        // Title
        mViewModel.title()
                .compose(bindActivity(lifecycle()))
                .observeOn(mainThread())
                .subscribe(this::setTitle);

        // Tagline
        mViewModel.tagline()
                .compose(bindActivity(lifecycle()))
                .observeOn(mainThread())
                .subscribe(((TextView) findById(this, R.id.tagline))::setText);

        // Overview
        mViewModel.description()
                .compose(bindActivity(lifecycle()))
                .observeOn(mainThread())
                .subscribe(((TextView) findById(this, R.id.description))::setText);

        // Loading
        mViewModel.isLoading()
                .compose(bindActivity(lifecycle()))
                .map(isLoading -> isLoading ? VISIBLE : GONE)
                .observeOn(mainThread())
                .subscribe(findById(this, R.id.loadingIndicator)::setVisibility);
    }
}
