package com.artechra.timing;

import java.util.HashMap;

public class TimerGroup {

    private static final String UNIT_INDICATOR = "us" ;

    private final HashMap<String, Timer> timers = new HashMap<>() ;

    public static class Summary {
        public final long total ;
        public final long count ;
        public final long min ;
        public final long max ;
        public final long avg ;
        public final String unitIndicator ;
        public Summary(long total, long count, long min, long max, long avg, String unitIndicator) {
            this.total = total ;
            this.count = count ;
            this.min = min ;
            this.max = max ;
            this.avg = avg ;
            this.unitIndicator = unitIndicator ;
        }
        public String toString() {
            return String.format("Summary[total=%d%s count=%d min=%d%s max=%d%s avg=%d%s]",
                    this.total, this.unitIndicator,
                    this.count,
                    this.min, this.unitIndicator,
                    this.max, this.unitIndicator,
                    this.avg, this.unitIndicator) ;
        }
    }

    public void startTimer(String name) {
        Timer t = createTimer() ;
        t.start();
        timers.put(name, t);
    }

    public void stopTimer(String name) {
        Timer t = timers.get(name) ;
        if (t == null) {
            throw new IllegalArgumentException(String.format("No such timer '%s'", name)) ;
        }
        t.stop();
    }

    public Summary getSummaryStats() {
        long min = Long.MAX_VALUE ;
        long max = 0 ;
        long total = 0 ;
        long count =  0;
        for (Timer t : timers.values()) {
            long duration = t.durationMillis() ;
            if (duration < min) {
                min = duration ;
            }
            if (duration > max) {
                max = duration ;
            }
            total += duration ;
            count++ ;
        }
        long avg = (count == 0 ? 0 : total/count) ;
        return new Summary(total, count, min, max, avg, UNIT_INDICATOR) ;
    }

    protected Timer createTimer() {
        return new Timer() ;
    }
}
