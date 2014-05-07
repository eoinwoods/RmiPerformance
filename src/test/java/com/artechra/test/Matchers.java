package com.artechra.test;

import com.artechra.timing.TimerGroup;
import org.hamcrest.Matcher;

class Matchers extends org.hamcrest.Matchers {
    static Matcher<? super TimerGroup.Summary> equalTo(TimerGroup.Summary summary) {
        return new TimerSummaryIsEqual(summary);
    }
}
