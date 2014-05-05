package com.artechra.timing;

public class Timer {

    private long start = 0;
    private long end = 0 ;

    public void start() {
        this.start = System.nanoTime() ;
    }

    public void stop() {
        checkStarted();
        this.end = System.nanoTime() ;
    }

    public long durationNanos() {
        checkStarted();
        checkEnded();
        return this.end - this.start;
    }

    public long durationMillis() {
        return durationNanos() / 1000 / 1000 ;
    }

    private void checkStarted() {
        if (this.start == 0) {
            throw new IllegalStateException("Timer has not been started") ;
        }
    }
    private void checkEnded() {
        if (this.end == 0) {
            throw new IllegalStateException("Timer has not been ended") ;
        }
    }

}
