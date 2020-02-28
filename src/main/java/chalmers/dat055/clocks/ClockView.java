package chalmers.dat055.clocks;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * A view of a standard 12-hour cycle wall clock with three hands, a short thick one representing hours, a tall one
 * representing minutes and a tall and thin one representing seconds.
 */
public abstract class ClockView extends Group {

    /**
     * After checking the time and settings its cursors accordingly the clock will wait for a given amount of time
     * before it repeats the process.
     *
     * <p> Values less than zero will halt the clock forever.
     *
     * @return Duration in seconds.
     */
    public abstract double getWait();

    /**
     * The state of the clocks hands are determined by the time. Note that the design of the the clock is such that
     * the state modulates every 12-hours.
     *
     * @return Floating precision positive or negative UNIX time, i.e seconds since 1970-01-01 00:00 GMT.
     */
    public abstract double getTime();

    private Canvas mHandsCanvas;
    private Canvas mDialCanvas;

    public ClockView() {
        mHandsCanvas = new Canvas();
        mDialCanvas = new Canvas();
        getChildren().setAll(mDialCanvas, mHandsCanvas);
    }

    private static void drawHand(GraphicsContext g, double t, double w, double h) {
        g.save();
        g.rotate(t * 360.0);
        g.scale(w, h);
        g.translate(-0.5, 0);
        g.fillRect(0, 0, 1, 1);
        g.restore();
    }

    public void display() {

        Platform.runLater(() -> {
            drawDial();
            drawHands();
        });

        new Thread(() -> {
            while (true) {
                Platform.runLater(this::drawHands);

                var w = getWait();
                if (w < 0) {
                    return;
                }

                try {
                    Thread.sleep((long)(w * 1000.0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Sets the size of the clock view.
     *
     * <p>NOTE! This method must be called before {@code display}.
     * @param width New width.
     * @param height New height.
     */
    public void setSize(double width, double height) {
        for (Canvas c : Arrays.asList(mDialCanvas, mHandsCanvas)) {
            c.setWidth(width);
            c.setHeight(height);
        }
    }

    private void drawDial() {
        var g = mDialCanvas.getGraphicsContext2D();
        g.save();

        g.scale(mDialCanvas.getWidth() / 2, mDialCanvas.getHeight() / 2);
        g.clearRect(0, 0, 2, 2);

        // Draw dial and case.
        g.setFill(Color.BEIGE);
        g.setStroke(Color.BLACK);
        g.setLineWidth(2.5e-2);
        g.fillOval(0, 0, 2, 2);
        g.strokeOval(1.25e-2, 1.25e-2, 1.975, 1.975);

        // Draw markers
        g.translate(1, 1);
        for (int i = 0; i < 12; ++i) {
            g.rect(-0.01, 0.89, 0.02, 0.070);
            g.rotate(360.0/12.0);
        }
        g.setFill(Color.BLACK);
        g.fill();

        g.restore();
    }

    private void drawHands() {
        var t = getTime();
        var width = mHandsCanvas.getWidth();
        var height = mHandsCanvas.getHeight();

        var g = mHandsCanvas.getGraphicsContext2D();
        g.save();

        g.setFill(Color.BLACK);
        g.scale(width / 2, height / 2);
        g.clearRect(0, 0, 2, 2);
        g.translate(1, 1);
        g.rotate(180);

        // Draw the hands
        drawHand(g, (t % 60.0) / 60.0, 0.025, 0.85);
        drawHand(g, (t % 3600.0) / 3600.0, 0.05, 0.8);
        drawHand(g, (t % 43200.0) / 43200.0, 0.075, 0.55);

        // Draw the dot in the middle above the hands.
        g.setLineWidth(0.3);
        g.setStroke(Color.BLACK);
        g.setFill(Color.color(0.8, 0.2, 0.1));
        g.scale(0.04, 0.04);
        g.fillOval(-1, -1, 2, 2);
        g.strokeOval(-1, -1, 2, 2);

        g.restore();
    }
}
