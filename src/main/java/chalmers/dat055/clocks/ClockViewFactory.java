package chalmers.dat055.clocks;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This is a factory class for {@code ClockView} objects.
 */
public class ClockViewFactory {

    /**
     * Makes a clock that will display the given time of a certain time zone.
     *
     * @param width View width.
     * @param height View height.
     * @param wait Update interval.
     * @param timeZone Time zone for the clock.
     * @return A clock view.
     */
    public static ClockView make(double width, double height, double wait, ZoneId timeZone) {
        return new ClockView(width, height) {

            @Override
            public double getWait() {
                return wait;
            }

            @Override
            public double getTime() {
                var now = ZonedDateTime.now(timeZone);
                return (double)((now.getHour() * 3600 + now.getMinute() * 60 + now.getSecond())
                        * 1000000000L + (long)now.getNano()) / 10e8;
            }

            @Override
            public String toString() {
                return timeZone.toString();
            }
        };
    }
}
