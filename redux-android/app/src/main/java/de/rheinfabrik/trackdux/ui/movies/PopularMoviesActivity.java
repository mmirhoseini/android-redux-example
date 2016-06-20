package de.rheinfabrik.trackdux.ui.movies;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.rheinfabrik.trackdux.R;
import de.rheinfabrik.trackdux.adapter.AdapterFactory;
import de.rheinfabrik.trackdux.viewmodels.PopularMoviesViewModel;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout.refreshes;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class PopularMoviesActivity extends RxAppCompatActivity {

    // Members

    private PopularMoviesViewModel mViewModel;

    @Bind(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.refreshLayout)
    protected SwipeRefreshLayout mRefreshLayout;

    // Lifecycle

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        setContentView(R.layout.activity_popular_movies);

        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));

        mViewModel = new PopularMoviesViewModel(getApplicationContext());

        addBindings();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mViewModel.loadPopularMoviesCommand.call(null);
    }

    // Private Api

    private void addBindings() {

        // Refresh layout
        refreshes(mRefreshLayout)
                .compose(bindToLifecycle())
                .subscribe(x -> mViewModel.reloadPopularMoviesCommand.call(null));

        // Details
        mViewModel.openMovieDetails()
                .compose(bindToLifecycle())
                .observeOn(mainThread())
                .subscribe(this::startActivity);

        // Movies
        mViewModel.popularMovies()
                .distinctUntilChanged()
                .map(AdapterFactory::newMovieRecyclerViewAdapter)
                .compose(bindToLifecycle())
                .observeOn(mainThread())
                .subscribe(mRecyclerView::setAdapter);

        // Loading state
        mViewModel.isLoadingMovies()
                .compose(bindToLifecycle())
                .observeOn(mainThread())
                .subscribe(lIsLoading -> {
                    mRefreshLayout.setRefreshing(lIsLoading);
                    mRecyclerView.setVisibility(lIsLoading ? INVISIBLE : VISIBLE);
                });

        // Error
        mViewModel.errorMessage()
                .compose(bindToLifecycle())
                .observeOn(mainThread())
                .subscribe(lErrorMessage -> Toast.makeText(this, lErrorMessage, LENGTH_SHORT).show());
    }
}
