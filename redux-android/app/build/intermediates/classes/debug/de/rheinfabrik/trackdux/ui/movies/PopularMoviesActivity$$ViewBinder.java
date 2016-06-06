// Generated code from Butter Knife. Do not modify!
package de.rheinfabrik.trackdux.ui.movies;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PopularMoviesActivity$$ViewBinder<T extends de.rheinfabrik.trackdux.ui.movies.PopularMoviesActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492949, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131492949, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131492948, "field 'mRefreshLayout'");
    target.mRefreshLayout = finder.castView(view, 2131492948, "field 'mRefreshLayout'");
  }

  @Override public void unbind(T target) {
    target.mRecyclerView = null;
    target.mRefreshLayout = null;
  }
}
