package View;

import Backend.TermTester;
import Driver.Main;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.FileInputStream;

public class HomeScene {
    private static Scene homeScene;
    private static MenuBarClass menuBarClass;
    private static ToolBarClass toolBarClass;
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
        toolBarClass = new ToolBarClass();
        toolBar = toolBarClass.getToolBar();
        homeTextEditor = new HomeTextEditor();
        homeTextEditor.setTextEditor();
        menuBarClass = new MenuBarClass(homeTextEditor);
        menuBar = menuBarClass.getMenuBar();
        VBox vBox = new VBox(menuBar, toolBar, homeTextEditor.getTextEditor());
        homeScene = new Scene(vBox, 1264, 775);
    }

    public static Scene getHomeScene(){
        return homeScene;
    }
}
