import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

       Parent root = FXMLLoader.load(getClass().getResource("views/LogingForm.fxml"));
       primaryStage.setTitle("Bethara Auto Service (v-1.0.0)");
       primaryStage.initStyle(StageStyle.DECORATED);
       primaryStage.setScene(new Scene(root));
       primaryStage.show();

       new FadeIn(root).play();

    }
}
