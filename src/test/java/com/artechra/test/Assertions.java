package com.artechra.test;

import static org.junit.Assert.assertTrue;

class Assertions {

    public static void assertEqualsWithinTolerance(long expected, long value, long tolerance) {
        assertTrue(String.format("expected %d to be %d within tolerance %d", value, expected, tolerance),
                value >= expected - tolerance && value <= expected + tolerance) ;
    }

}
