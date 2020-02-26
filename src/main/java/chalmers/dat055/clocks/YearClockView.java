package chalmers.dat055.clocks;

import java.time.ZonedDateTime;

/**
 * The date of the year represented with a 12 hour cycle.
 */
public class YearClockView extends ClockView {

    public YearClockView(double width, double height) {
        super(width, height);
    }

    @Override
    public double getWait() {
        return 1;
    }

    @Override
    public double getTime() {
        var now = ZonedDateTime.now();

        // Seconds since new years eve.
        var t = (double) (now.getDayOfYear() * 60 * 60 * 24 +
                now.getHour() * 60 * 60 +
                now.getMinute() * 60 +
                now.getSecond());

        // Seconds of this year.
        var year = now.getYear();
        var total = year % 1000 == 0 || (year % 4 == 0 && year % 100 != 0) ? 31622400 : 31536000;

        // Remainder expressed in a 12-hour cycle.
        return (t / total) * 43200.0;
    }

    @Override
    public String toString() {
        return "Year";
    }
}
