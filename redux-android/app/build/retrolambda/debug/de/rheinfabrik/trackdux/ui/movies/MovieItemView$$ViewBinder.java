// Generated code from Butter Knife. Do not modify!
package de.rheinfabrik.trackdux.ui.movies;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MovieItemView$$ViewBinder<T extends de.rheinfabrik.trackdux.ui.movies.MovieItemView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492950, "field 'mMovieTitle'");
    target.mMovieTitle = finder.castView(view, 2131492950, "field 'mMovieTitle'");
  }

  @Override public void unbind(T target) {
    target.mMovieTitle = null;
  }
}
