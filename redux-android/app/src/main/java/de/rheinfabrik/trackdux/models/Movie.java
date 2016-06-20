package de.rheinfabrik.trackdux.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public final class Movie implements Serializable {

    // Properties

    @SerializedName("title")
    public final String title = null;

    @SerializedName("ids")
    public final Map<String, String> identifiers = null;

    // Equals

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return !(title != null ? !title.equals(movie.title) : movie.title != null) && !(identifiers != null ? !identifiers.equals(movie.identifiers) : movie.identifiers != null);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (identifiers != null ? identifiers.hashCode() : 0);

        return result;
    }
}
