package org.crosa.android.popularmovies.model;

public enum PosterSize {
    W_92("w92"),
    W_154("w154"),
    W_185("w185"),
    W_342("w342"),
    W_500("w500"),
    W_780("w780"),
    ORIGINAL("original");

    private final String value;
    PosterSize(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
