package chalmers.dat055.clocks;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    private static Canvas drawDial(double width, double height) {
        var canvas = new Canvas(width, height);
        var g = canvas.getGraphicsContext2D();
        g.scale(width / 2, height / 2);

        //
        // Draw dial and case.
        //
        g.setFill(Color.BEIGE);
        g.setStroke(Color.BLACK);
        g.setLineWidth(2.5e-2);
        g.fillOval(0, 0, 2, 2);
        g.strokeOval(1.25e-2, 1.25e-2, 1.975, 1.975);

        //
        // Draw markers
        //
        g.translate(1, 1);
        for (int i = 0; i < 12; ++i) {
            g.rect(-0.01, 0.89, 0.02, 0.070);
            g.rotate(360.0/12.0);
        }
        g.setFill(Color.BLACK);
        g.fill();

        return canvas;
    }

    private Canvas mHandsCanvas;

    public ClockView(double width, double height) {
        mHandsCanvas = new Canvas(width, height);
        getChildren().setAll(drawDial(width, height), mHandsCanvas);
    }

    private static void drawHand(GraphicsContext g, double t, double w, double h) {
        g.save();
        g.rotate(t * 360.0);
        g.translate(-w / 2, 0);
        g.scale(w, h);
        g.fillRect(0, 0, 1, 1);
        g.restore();
    }

    public void display() {
        var width = mHandsCanvas.getWidth();
        var height = mHandsCanvas.getHeight();
        var g = mHandsCanvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.scale(width / 2, height / 2);
        g.translate(1, 1);
        g.rotate(180);

        new Thread(() -> {
            while (true) {
                var t = getTime();

                Platform.runLater(() -> {
                    // This clear is a funny side effect from the current state of the transform.
                    g.clearRect(-1, -1, 2, 2);

                    drawHand(g, (t % 60.0) / 60.0, 0.025, 0.85);
                    drawHand(g, (t % 3600.0) / 3600.0, 0.05, 0.8);
                    drawHand(g, (t % 43200.0) / 43200.0, 0.075, 0.55);
                });

                var w = getWait();
                if (w < 0) { return; }

                try {
                    Thread.sleep((long)(w * 1000.0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
