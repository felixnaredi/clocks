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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class App extends Application {
    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) {
        var quit = new AtomicBoolean(false);
        stage.setOnCloseRequest((e) -> quit.set(true));

        var box = new HBox(8);
        box.setAlignment(Pos.CENTER);

        // Create the clocks and display them.
        Stream.of(
                ClockViewFactory.make(0, 0, 0.125, ZoneId.systemDefault()),
                ClockViewFactory.make(0, 0, 0.125, ZoneId.of("UTC")),
                new YearClockView(0, 0),
                new RandomClockView(0, 0))

                .forEach(c -> {

                    // Override the 'getWait' method for each clock so that the running threads of the clocks will be
                    // killed when the application is quited.
                    //
                    // Another way would be to make the clock threads daemon threads but this does not risk them not
                    // updating on time due to priority and also is more fun.
                    var k = new ClockView(144, 144) {
                        @Override
                        public double getWait() {
                            return quit.get() ? -1 : c.getWait();
                        }

                        @Override
                        public double getTime() {
                            return c.getTime();
                        }
                    };

                    var label = new Label(c.toString());
                    label.setFont(Font.font("Helvetica", 18));
                    var view = new VBox(3.5, label, k);
                    view.setAlignment(Pos.CENTER);

                    box.getChildren().add(view);
                    k.display();
                });

        stage.setScene(new Scene(box, box.getChildren().size() * 150 + 20, 176));
        stage.show();

    }
}
