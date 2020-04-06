package View;


import Backend.TermTester;
import Driver.Main;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import java.io.File;

public class HomeScene {
    private static Scene homeScene;
    private static MenuBarClass menuBarClass;
    private static ToolBarClass toolBarClass;
    private static MenuBar menuBar;
    private static ToolBar toolBar;
    private static HomeTextEditor homeTextEditor;
    private static ConsoleOutput consoleOutput;
    private static File localFile,originalFile;

    public static File getFile() {
        return originalFile;
    }

    public void setFile(File localFile,File originalFile) {
        HomeScene.localFile = localFile;
        HomeScene.originalFile=originalFile;
    }

    public static void setHomeScene(){
        homeTextEditor = new HomeTextEditor();
        homeTextEditor.setTextEditor();
        initializeConsoleOutput();
        toolBarClass = new ToolBarClass(homeTextEditor);
        toolBar = toolBarClass.getToolBar();
        menuBarClass = new MenuBarClass(homeTextEditor);
        menuBar = menuBarClass.getMenuBar();
        VBox vBox = new VBox(menuBar, toolBar, homeTextEditor.getTextEditor(),consoleOutput.getConsoleOutput());
        homeScene = new Scene(vBox, 1264, 775);


        homeScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.F12) {
                    System.out.println("Key Pressed: " + ke.getCode());
                    ke.consume(); // <-- stops passing the event to next node
                }
                System.out.println(ke.getCode());
            }
        });
    }


    private static void initializeConsoleOutput() {
        consoleOutput = new ConsoleOutput();
        consoleOutput.setTextEditor();
    }

    public static Scene getHomeScene(){
        return homeScene;
    }
}
