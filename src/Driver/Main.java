package Driver;

import View.HomeScene;
import View.WelcomeScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 *Main Class for the project
 */
public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        WelcomeScene.setWelcomeScene();
        Parent root = FXMLLoader.load(getClass().getResource("../View/sample.fxml"));
        primaryStage.setTitle("SmartGCC");
//        primaryStage.setScene(new Scene(root, 1264, 775));
        primaryStage.setScene(WelcomeScene.getWelcomeScene());
        primaryStage.show();

    }

    public static void changeScene(){
        HomeScene.setHomeScene();
        stage.setScene(HomeScene.getHomeScene());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage(){
        return stage;
    }
}
