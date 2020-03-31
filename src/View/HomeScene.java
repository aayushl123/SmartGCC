package View;

import Backend.TermTester;
import Driver.Main;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HomeScene {
    private static Scene homeScene;
    private static MenuBarClass menuBarClass;
    private static MenuBar menuBar;
    private static ToolBar toolBar;
    private static HomeTextEditor homeTextEditor;
    private static File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static void setHomeScene(){
        setToolBar();
        homeTextEditor = new HomeTextEditor();
        homeTextEditor.setTextEditor();
        menuBarClass = new MenuBarClass(homeTextEditor);
        menuBar = menuBarClass.getMenuBar();
        VBox vBox = new VBox(menuBar, toolBar, homeTextEditor.getTextEditor());
        homeScene = new Scene(vBox, 1264, 775);
    }

    public static void setToolBar(){
        try {
            toolBar = new ToolBar();
            FileInputStream input = new FileInputStream("src/Resources/CompileButtonIcon.png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            Button compileButton = new Button("Compile", imageView);
            input = new FileInputStream("src/Resources/LinkIcon.png");
            image = new Image(input);
            imageView = new ImageView(image);
            Button linkButton = new Button("Linking", imageView);
            input = new FileInputStream("src/Resources/ExecuteIcon.png");
            image = new Image(input);
            imageView = new ImageView(image);
            Button executeButton = new Button("Execute", imageView);

            setToolBarButtonAction(compileButton);
            setToolBarButtonAction(executeButton);

            toolBar.getItems().add(compileButton);
            toolBar.getItems().add(linkButton);
            toolBar.getItems().add(executeButton);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setToolBarButtonAction(Button button){
        if(button.getText().equals("Compile")) {
            button.setOnAction(event -> {
                TermTester.ToolBarActions(1, file.getName());
            });
        }else if(button.getText().equals("Execute")){
            button.setOnAction(event -> {
                TermTester.ToolBarActions(4, file.getName());
            });
        }
    }

    public static Scene getHomeScene(){
        return homeScene;
    }
}
