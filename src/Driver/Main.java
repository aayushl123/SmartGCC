package Driver;

import View.WelcomeScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        WelcomeScene.setWelcomeScene();
        Parent root = FXMLLoader.load(getClass().getResource("../View/sample.fxml"));
        primaryStage.setTitle("SmartGCC");
//        primaryStage.setScene(new Scene(root, 1264, 775));
        primaryStage.setScene(WelcomeScene.getWelcomeScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
