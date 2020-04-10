package View;


import Backend.TermTester;
import Driver.Main;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import java.io.File;

/**
 * Class to display the home screen to the user.
 * @author sagarbhatia, karansharma
 */
public class HomeScene {
    private static Scene homeScene;
    private static MenuBarClass menuBarClass;
    private static ToolBarClass toolBarClass;
    private static MenuBar menuBar;
    private static ToolBar toolBar;
    private static HomeTextEditor homeTextEditor;
    private static ConsoleOutput consoleOutput;
    private static File localFile,originalFile;

    /**
     * Method to get the file object
     * @return originalFile object of the original file
     */
    public static File getFile() {
        return originalFile;
    }

    /**
     * Method to set the local file object and original file object
     * @param localFile name of the file in resources
     * @param originalFile name of the file in the original location.
     */
    public void setFile(File localFile,File originalFile) {
        HomeScene.localFile = localFile;
        HomeScene.originalFile=originalFile;
    }

    /**
     * Method to intialize the object in the home scene.
     */
    public static void setHomeScene(){
        homeTextEditor = new HomeTextEditor();
        toolBarClass = new ToolBarClass(homeTextEditor);
        toolBar = toolBarClass.getToolBar();
        homeTextEditor.setTextEditor();
        initializeConsoleOutput();
        toolBarClass = new ToolBarClass(homeTextEditor);
        toolBar = toolBarClass.getToolBar();
        menuBarClass = new MenuBarClass(homeTextEditor);
        menuBar = menuBarClass.getMenuBar();
        VBox vBox = new VBox(menuBar, toolBar, homeTextEditor.getTextEditor(),consoleOutput.getConsoleOutput());
        homeScene = new Scene(vBox, 1264, 775);


        homeScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {


            final KeyCombination keyCombSave = new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_DOWN);

            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.F1) {
                    ToolBarClass.onOptionSelected(1);
                    ke.consume();
                } else  if (ke.getCode() == KeyCode.F2) {
                    ToolBarClass.onOptionSelected(2);
                    ke.consume();
                } else  if (ke.getCode() == KeyCode.F3) {
                   // ToolBarClass.onOptionSelected(3);
                    ke.consume();
                } else  if (ke.getCode() == KeyCode.F4) {
                    ToolBarClass.onOptionSelected(4);
                    ke.consume();
                }

                if (keyCombSave.match(ke)) {
                    ToolBarClass.checkIfFileExists();
                }
            }
        });

    }

    /**
     * Method to initialize the console output window.
     */
    private static void initializeConsoleOutput() {
        consoleOutput = new ConsoleOutput();
        consoleOutput.setTextEditor();
    }

    /**
     * Method to get the object of the home screen.
     * @return homeScene object
     */
    public static Scene getHomeScene(){
        return homeScene;
    }
}
