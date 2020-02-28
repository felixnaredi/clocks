package chalmers.dat055.clocks;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class App extends Application {

    private final static double CLOCK_WIDTH = 200.0;
    private final static double CLOCK_HEIGHT = 200.0;

    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) {
        var quit = new AtomicBoolean(false);
        stage.setOnCloseRequest((e) -> quit.set(true));

        var box = new HBox(CLOCK_WIDTH * 0.1);
        box.setAlignment(Pos.CENTER);

        // Create the clocks and display them.
        for (ClockView c : Arrays.asList(
                ClockViewFactory.make(0.125, ZoneId.systemDefault()),
                ClockViewFactory.make(0.125, ZoneId.of("UTC")),
                new YearClockView(),
                new RandomClockView())) {
            // Override the 'getWait' method for each clock so that the running threads of the clocks will be
            // killed when the application quits.
            //
            // Another way would be to make the threads of each clock to daemon threads but that would risk them
            // not updating on time due to becoming low priority threads.
            var k = new ClockView() {
                @Override
                public double getWait() {
                    return quit.get() ? -1 : c.getWait();
                }

                @Override
                public double getTime() {
                    return c.getTime();
                }
            };
            k.setSize(CLOCK_WIDTH, CLOCK_HEIGHT);

            var label = new Label(c.toString());
            label.setFont(Font.font("Helvetica", 18));
            var view = new VBox(3.5, label, k);
            view.setAlignment(Pos.CENTER);

            box.getChildren().add(view);
            k.display();
        }

        stage.setScene(new Scene(
                box,
                box.getChildren().size() * CLOCK_WIDTH * 1.1 + 20,
                CLOCK_HEIGHT * 2.5));

        stage.show();
    }
}
