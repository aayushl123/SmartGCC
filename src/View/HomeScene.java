package View;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;

public class HomeScene {
    private static Scene homeScene;
    private static Menu fileMenu;
    private static Menu editMenu;
    private static Menu helpMenu;
    private static MenuBar menuBar;
    private static ToolBar toolBar;
    private static HomeTextEditor homeTextEditor;

    public static void setHomeScene(){
        setMenuBar();
        setToolBar();
        homeTextEditor = new HomeTextEditor();
        homeTextEditor.setTextEditor();
        VBox vBox = new VBox(menuBar, toolBar, homeTextEditor.getTextEditor());
        homeScene = new Scene(vBox, 1264, 775);
    }

    public static void setMenuBar(){
        menuBar = new MenuBar();
        setMenus();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(editMenu);
        menuBar.getMenus().add(helpMenu);
    }

    public static void setMenus(){
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");
        helpMenu = new Menu("Help");

        MenuItem fileMenuItem1 = new MenuItem("New");
        MenuItem fileMenuItem2 = new MenuItem("Open");
        MenuItem fileMenuItem3 = new MenuItem("Save");
        MenuItem fileMenuItem4 = new MenuItem("Save as");

        fileMenu.getItems().add(fileMenuItem1);
        fileMenu.getItems().add(fileMenuItem2);
        fileMenu.getItems().add(fileMenuItem3);
        fileMenu.getItems().add(fileMenuItem4);

        MenuItem editMenuItem1 = new MenuItem("Cut");
        MenuItem editMenuItem2 = new MenuItem("Copy");
        MenuItem editMenuItem3 = new MenuItem("Undo");

        editMenu.getItems().add(editMenuItem1);
        editMenu.getItems().add(editMenuItem2);
        editMenu.getItems().add(editMenuItem3);

        MenuItem helpMenuItem1 = new MenuItem("View Documentation");

        helpMenu.getItems().add(helpMenuItem1);
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

            toolBar.getItems().add(compileButton);
            toolBar.getItems().add(linkButton);
            toolBar.getItems().add(executeButton);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Scene getHomeScene(){
        return homeScene;
    }
}
