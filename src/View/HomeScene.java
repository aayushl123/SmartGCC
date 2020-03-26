package View;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class HomeScene {
    private static Scene homeScene;
    private static Menu fileMenu;
    private static Menu editMenu;
    private static Menu helpMenu;
    private static MenuBar menuBar;

    public static void setHomeScene(){
        setMenuBar();
        VBox vBox = new VBox(menuBar);
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



    public static Scene getHomeScene(){
        return homeScene;
    }
}
