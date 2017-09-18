package com.android.arjun.redditfinder;

import org.junit.Test;

import static com.android.arjun.redditfinder.Generics.getNotNullValue;
import static org.assertj.core.api.Assertions.assertThat;

public class GenericsTest {
    private final String actualNullValue = null;
    private final String fallbackNotNullValue = "fallback";

    @Test
    public void shouldReturnActualValue() {
        final String actualNotNullValue = "actual";
        assertThat(getNotNullValue(actualNotNullValue, fallbackNotNullValue)).isEqualTo("actual");
    }

    @Test
    public void shouldReturnFallbackValueWhenActualValueIsNull() {
        assertThat(getNotNullValue(actualNullValue, fallbackNotNullValue)).isEqualTo("fallback");
    }
}
