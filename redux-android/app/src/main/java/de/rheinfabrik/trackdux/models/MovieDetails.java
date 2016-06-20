package de.rheinfabrik.trackdux.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class MovieDetails implements Serializable {

    // Properties

    @SerializedName("tagline")
    public final String tagline = null;

    @SerializedName("overview")
    public final String description = null;
}
