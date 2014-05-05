package com.artechra.test;

import com.artechra.timing.Timer;
import com.artechra.timing.TimerGroup;
import org.junit.Test;

import static com.artechra.test.Assertions.assertEqualsWithinTolerance;
import static com.artechra.test.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class TimerGroupTests {

    private static class FixedTimer extends Timer {
        private final long durationNanos ;
        public FixedTimer(long durationNanos) {
            this.durationNanos = durationNanos ;
        }
        @Override
        public long durationNanos() {
            return this.durationNanos ;
        }

    }

    private static class FixedTimerGroup extends TimerGroup {
        @Override
        public Timer createTimer() {
            long groupSize = this.getSummaryStats().count ;
            long msecs = groupSize + 10 ;
            return new FixedTimer(msecs * 1000 * 1000) ;
        }
    }

    @Test
    public void timerGroupWithRealTimerMustReturnCorrectSummary() throws InterruptedException {
        TimerGroup tg = new TimerGroup() ;
        tg.startTimer("one") ;
        Thread.sleep(100) ;
        tg.stopTimer("one");
        TimerGroup.Summary s = tg.getSummaryStats() ;
        assertEqualsWithinTolerance(100, s.total, 10);
        assertEquals(1, s.count) ;
    }

    @Test
    public void singleValueTimerMustReturnCorrectSummary() throws Exception {
        TimerGroup tg = new FixedTimerGroup() ;
        tg.startTimer("one");
        tg.stopTimer("one") ;
        TimerGroup.Summary s = tg.getSummaryStats() ;
        assertThat(s, is(equalTo(new TimerGroup.Summary(10l, 1l, 10l, 10l, 10l, "")))) ;
    }

    @Test
    public void threeTimersMustReturnCorrectSummary() throws Exception {
        TimerGroup tg = new FixedTimerGroup() ;
        tg.startTimer("one");
        tg.stopTimer("one") ;
        tg.startTimer("two");
        tg.stopTimer("two") ;
        tg.startTimer("three");
        tg.stopTimer("three") ;
        TimerGroup.Summary s = tg.getSummaryStats() ;
        assertThat(s, is(equalTo(new TimerGroup.Summary(33l, 3l, 10l, 12l, 11l, "")))) ;
    }

    @Test(expected = IllegalArgumentException.class)
    public void stoppingNonexistentTimerMustCauseAnError() {
        TimerGroup tg = new TimerGroup() ;
        tg.stopTimer("no-such-timer");
    }
}
