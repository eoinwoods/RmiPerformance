package com.artechra.test;

import com.artechra.timing.TimerGroup;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

class TimerSummaryIsEqual extends BaseMatcher<TimerGroup.Summary> {

    private final TimerGroup.Summary summary;

    TimerSummaryIsEqual(TimerGroup.Summary summary) {
        this.summary = summary;
    }

    public boolean matches(Object o) {
        TimerGroup.Summary s = (TimerGroup.Summary) o;
        return this.summary.avg   == s.avg &&
               this.summary.count == s.count &&
               this.summary.total == s.total &&
               this.summary.max   == s.max &&
               this.summary.min   == s.min ;
    }

    public void describeTo(Description desc) {
        desc.appendText(this.summary.toString()) ;
    }
}
