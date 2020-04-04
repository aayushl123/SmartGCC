package View;

import Backend.UserClass;
import Driver.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;

public class WelcomeScene {
    private static Scene welcomeScene;
    private static ImageView imageView;

    public static void setWelcomeScene(){
        Label playerLabel = new Label("Please select the type of user and press OK:");
        playerLabel.setFont(new Font("Arial", 24));
        ToggleGroup radioGroup = new ToggleGroup();

        Button submitButton = new Button("OK");
        RadioButton radioButton1 = new RadioButton("1. Novice user");
        RadioButton radioButton2 = new RadioButton("2. Typical User");
        RadioButton radioButton3 = new RadioButton("3. Expert User");

        submitButton.setStyle("-fx-font-size:20px;");
        radioButton1.setStyle("-fx-font-size:20px;");
        radioButton2.setStyle("-fx-font-size:20px;");
        radioButton3.setStyle("-fx-font-size:20px;");
        radioButton1.setToggleGroup(radioGroup);
        radioButton2.setToggleGroup(radioGroup);
        radioButton3.setToggleGroup(radioGroup);
        submitButton.setOnAction(event -> {
            RadioButton selectedButton = (RadioButton)radioGroup.getSelectedToggle();
            String user;
            String[] buttonText = selectedButton.getText().split(" ");
            if(buttonText[1].charAt(0) == 'N')
                user = "Novice";
            else if (buttonText[1].charAt(0) == 'T')
                user = "Typical";
            else
                user = "Expert";
            UserClass.setUserType(user);
            Main.changeScene();
        });

        setImageView();
        VBox vbox = new VBox(20, imageView, playerLabel,radioButton1, radioButton2, radioButton3, submitButton);
        vbox.setAlignment(Pos.CENTER);
        welcomeScene = new Scene(vbox, 1264, 775);
    }

    public static Scene getWelcomeScene(){
        return welcomeScene;
    }

    public static ImageView setImageView(){
        try {
            Image image = new Image(new FileInputStream("src/Resources/WelcomeScreenImage.png"));
            imageView = new ImageView(image);
            imageView.setX(50);
            imageView.setY(25);
            imageView.setPreserveRatio(true);
        }catch(Exception e){
            e.printStackTrace();
        }
        return imageView;
    }
}
