package chalmers.dat055.clocks;

import java.util.Random;

/**
 * {@code ClockView} that starts on a random time and progresses the time with a random pulsating movement.
 */
public class RandomClockView extends ClockView {

    double mTime;
    int mCount;
    double mStep;
    Random mRandom;

    RandomClockView() {
        mRandom = new Random();
        mTime = mRandom.nextInt(43200);
        mStep = 0;
        mCount = 0;
    }

    @Override
    public double getWait() {
        return 0.025;
    }

    @Override
    public double getTime() {
        mCount %= 60;
        if (mCount++ == 0) {
            mStep += (mRandom.nextDouble() - 0.5);
        }
        mStep *= 0.97;
        mTime += mStep;
        return mTime;
    }

    @Override
    public String toString() {
        return "Random";
    }
}
