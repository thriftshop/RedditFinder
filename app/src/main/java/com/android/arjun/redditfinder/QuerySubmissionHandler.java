package com.android.arjun.redditfinder;

import android.support.annotation.NonNull;

import java.util.List;

public interface QuerySubmissionHandler<T> {
    /**
     * Gets all the resultant T of a particular query using the given parameters.
     * @param query String that need to be searched
     * @param limit	Maximum amount of resultant T that can be returned
     * @return a list of resultant T.
     * @throws IllegalArgumentException when the limit is less than zero.
     */
    @NonNull List<T> submit(@NonNull String query, @NonNull Integer limit) throws IllegalArgumentException;
}