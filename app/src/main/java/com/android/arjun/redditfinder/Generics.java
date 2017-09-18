package com.android.arjun.redditfinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Generics {
    public static <T> T getNotNullValue(@Nullable T actual, @NonNull T fallback) {
        if (actual != null) return actual;
        else return fallback;
    }

    private Generics() { }
}
