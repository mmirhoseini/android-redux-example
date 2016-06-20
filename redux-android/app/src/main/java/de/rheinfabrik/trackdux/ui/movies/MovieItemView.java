package de.rheinfabrik.trackdux.ui.movies;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.rheinfabrik.trackdux.R;
import de.rheinfabrik.trackdux.utils.DeviceUtils;
import de.rheinfabrik.trackdux.viewmodels.MovieItemViewModel;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.jakewharton.rxbinding.view.RxView.clicks;
import static com.trello.rxlifecycle.RxLifecycle.bindView;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class MovieItemView extends CardView {

    // Members

    @Bind(R.id.movieTitle)
    protected TextView mMovieTitle;

    private MovieItemViewModel mMovieItemViewModel;

    // Constructor

    public MovieItemView(Context pContext) {
        this(pContext, null);
    }

    public MovieItemView(Context pContext, AttributeSet pAttrs) {
        this(pContext, pAttrs, 0);
    }

    public MovieItemView(Context pContext, AttributeSet pAttrs, int pDefStyleAttr) {
        super(pContext, pAttrs, pDefStyleAttr);

        inflate(getContext(), R.layout.movie_item_view, this);

        mMovieItemViewModel = new MovieItemViewModel(getContext());

        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        setLayoutParams(layoutParams);

        setClickable(true);
        setRadius(DeviceUtils.pixelsToDensityPoints(getContext(), 10));

        ButterKnife.bind(this);
    }

    // Lifecycle

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        addBindings();
    }

    // Public Api

    public MovieItemViewModel getViewModel() {
        return mMovieItemViewModel;
    }

    // Private Api

    public void addBindings() {

        // Title
        mMovieItemViewModel.movieTitle()
                .compose(bindView(this))
                .observeOn(mainThread())
                .subscribe(mMovieTitle::setText);

        // Clicks
        clicks(this)
                .compose(bindView(this))
                .subscribe(mMovieItemViewModel.openDetailsCommand::call);
    }
}
