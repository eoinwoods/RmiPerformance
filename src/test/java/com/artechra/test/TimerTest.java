package com.artechra.test;

import com.artechra.timing.Timer;
import org.junit.Test;

import static com.artechra.test.Assertions.assertEqualsWithinTolerance;

public class TimerTest {

    private final static long MILLI_TO_NANO = 1000 * 1000 ;

    @Test
    public void testTimerReturnsCredibleNanosecondPeriodLength() throws InterruptedException {
        Timer t = new Timer() ;
        t.start();
        Thread.sleep(100) ;
        t.stop();
        long duration = t.durationNanos();
        assertEqualsWithinTolerance(100 * MILLI_TO_NANO, duration, 2 * MILLI_TO_NANO) ;
    }

    @Test(expected = IllegalStateException.class)
    public void aNonStartedTimerMustGenerateAnErrorIfStopped() {
        Timer t = new Timer() ;
        t.stop() ;
    }

    @Test(expected = IllegalStateException.class)
    public void testingARunningTimerMustGenerateAnException() {
        Timer t = new Timer() ;
        t.start() ;
        t.durationNanos() ;
    }

    @Test
    public void factoryMustReturnAFunctionalRunningTimer() throws InterruptedException {
        Timer t = Timer.newRunningTimer() ;
        Thread.sleep(10);
        t.stop() ;
        assertEqualsWithinTolerance(10 * MILLI_TO_NANO, t.durationNanos(), 2 * MILLI_TO_NANO) ;
    }
}
